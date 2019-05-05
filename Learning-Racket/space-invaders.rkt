#lang racket

(require 2htdp/universe)
(require 2htdp/image)

;; Space Invaders
;; =============================================================================
;; The tank moves right and left at the bottom of the screen when you press the arrow keys.
;; If you press the left arrow key, it will continue to move left at a constant speed until you press the right arrow key.
;; The tank should fire missiles straight up from its current position when you press the space bar.
;; The invaders should appear randomly along the top of the screen and move at a 45 degree angle.
;; When they hit a wall they will bounce off and continue at a 45 degree angle in the other direction.
;; When an invader reaches the bottom of the screen, the game is over.

;; Domain Analysis
;; =============================================================================
;; We need to have the data structures to represent the following:
;; - Tank: The player ship or tank. This will be a compound data structure that should capture the tank's x coordinate as well as direction.
;; - Invader: An enemy ship. This will be a compound data structure that should capture the ship's coordinate (x and y) as well as direction.
;; - ListOfInvaders: This will be a list of the invader ships on the screen.
;; - Missile: A fired missile. This will be a compound data structure that capture the missile's cooordinates (x and y)
;; - ListOfMissiles: This is a list of the missiles that have been fired, are on the screen, and and have not hit their targets.
;; - Game: This will be a compound data structure that includes a Tank, ListOfInvaders, and ListOfMissiles.
;; We need to have functions to acheive the following.
;; - Game -> Game: an engine that has an internal clock, and can capture key events from the player. We will use the big-bang function.

;; Constants:
;; =============================================================================
;; Screen Constants
(define WIDTH  300)
(define HEIGHT 500)
(define MTS (rectangle WIDTH HEIGHT "solid" "transparent"))

;; Tank Constants
(define TANK (above (rectangle 5 10 "solid" "black") (rectangle 20 10 "solid" "black")))
(define TANK-HEIGHT (image-height TANK))
(define TANK-HEIGHT/2 (/ TANK-HEIGHT 2))
(define TANK-SPEED 2)
(define HIT-RANGE 10)

;; Invader Constants
(define INVADER (overlay/xy (ellipse 10 15 "outline" "blue") -5 6 (ellipse 20 10 "solid"   "blue")))
(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED 1.5)
(define INVADE-RATE 100)
(define INVADER-WIDTH 20)

;; Missile Constants
(define MISSILE (ellipse 5 15 "solid" "red"))
(define MISSILE-SPEED 10)

;; Data Definitions:
;; =============================================================================
(define-struct game (invaders missiles tank tick))
;; Game is (make-game  (listof Invader) (listof Missile) tank ticks)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles, tank position, and ticks

(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1

(define-struct invader (x y dir))
;; Invader is (make-invader Number Number Integer[-1, 1])
;; interp. the invader is at (x, y) in screen coordinates
;; dir is the direction of the invader (+1 is right, and -1 is left)

(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates

;; Function Definitions:
;; =============================================================================
;; Game -> Game
(define (main game)
  (big-bang game                       ; Game
            (on-tick   update-all)     ; Game -> Game
            (to-draw   render)         ; Game -> Image
            (stop-when game-over?)     ; Game -> Boolean
            (on-key    handle-key)))   ; Game KeyEvent -> Game

;; Game -> Game
(define (update-all g)
  (make-game (update-invaders g) (update-missiles g) (advance-tank g) (add1 (game-tick g))))

;; Game -> Invaders
(define (update-invaders g)
  (let ((invaders (game-invaders g)) (missiles (game-missiles g)) (tick (game-tick g)))
    ;; Invader ListOfMissiles -> Boolean
    (define (intact-invader? i)
      (let ((any-collision? (lambda (m) (collide? i m))))
        (not (ormap any-collision? missiles))))
    ;; Invader -> Invader
    (define (advance-invader i)
      (let ((x (invader-x i)) (y (invader-y i)) (dir (invader-dir i)))
        ; when invader hits right edge it starts moving left
        (cond [(> x WIDTH) (make-invader (- x INVADER-X-SPEED) (+ y INVADER-Y-SPEED) (- INVADER-X-SPEED))]
              ; when invader hits left edge it start moving right
              [(< x     0) (make-invader (+ x INVADER-X-SPEED) (+ y INVADER-Y-SPEED) INVADER-X-SPEED)]
              ; otherwise advance invader in 45 degree angle
              [else (make-invader (+ x dir) (+ y INVADER-Y-SPEED) dir)])))
    ;; Invaders Missiles -> Invaders
    (define (advance-invaders invaders)
      (let ((intact-invaders (filter intact-invader? invaders)))  ; discard the invader if it is hit by a missile
        (map advance-invader intact-invaders)))
    ;; add a random invader on a regular interval
    (if (= 0 (modulo tick INVADE-RATE))
        (cons (random-invader) (advance-invaders invaders))
        (advance-invaders invaders))))

;; Game -> Missiles
(define (update-missiles g)
  (let ((invaders (game-invaders g)) (missiles (game-missiles g)) (tick (game-tick g)))
    ;; Missile ListOfInvaders -> Boolean
    (define (intact-missile? m)
      (let ((any-collision? (lambda (i) (collide? i m))))
        (not (ormap any-collision? invaders))))
    ;; Missile -> Boolean
    (define (in-screen? m)
      (< (missile-y m) HEIGHT))
    ;; Missile -> Missile
    (define (advance-missile m)
      (make-missile (missile-x m) (- (missile-y m) MISSILE-SPEED)))
    ;; Main Function
    (let ((intact-missiles (filter intact-missile? missiles)))                ; Discard missiles that hit a target
      (let ((in-screen-intact-missiles (filter in-screen? intact-missiles)))  ; Discard missiles outside the screen
        (map advance-missile in-screen-intact-missiles)))))                   ; Advance missiles

;; Tank -> Tank
(define (advance-tank g)
  (let ((t (game-tank g)))
  (cond [(and (> (tank-x t) WIDTH) (= (tank-dir t) +1)) t]  ; can't go beyond right edge of screen
        [(and (< (tank-x t)     0) (= (tank-dir t) -1)) t]  ; can't go beyond left edge of screen
        [else (make-tank (+ (* (tank-dir t) TANK-SPEED) (tank-x t)) (tank-dir t))])))

;; Invader Missile -> Boolean
(define (collide? i m)
  (and
   (< (abs (- (invader-x i) (missile-x m))) INVADER-WIDTH)
   (< (abs (- (invader-y i) (missile-y m))) INVADER-WIDTH)))

;; Generates a ranom invader
(define (random-invader)
  (if (= 0 (random 2))
      (make-invader (random WIDTH) 0 INVADER-X-SPEED)
      (make-invader (random WIDTH) 0 (- INVADER-X-SPEED))))

; Game KeyEvent -> Game
(define (handle-key g e)
  (let ((invaders (game-invaders g)) (missiles (game-missiles g)) (tank (game-tank g)) (tick (game-tick g)))
    (cond [(key=? e "right") (make-game invaders missiles (tank-right tank) tick)]
          [(key=? e  "left") (make-game invaders missiles (tank-left  tank) tick)]
          [(key=? e     " ") (make-game invaders (add-missile-to g) tank tick)]
          [(key=? e     "a") (make-game (cons (random-invader) invaders) missiles tank tick)]
          [else g])))

; Game -> ListOfMissiles
(define (add-missile-to g)
  (cons (make-missile (tank-x (game-tank g)) (- HEIGHT TANK-HEIGHT)) (game-missiles g)))

;; Tank -> Tank
(define (tank-right t) (make-tank (tank-x t) +1))

;; Tank -> Tank
(define (tank-left t) (make-tank (tank-x t) -1))

; Game -> Boolean
(define (game-over? g) (any-invader-at-bottom? (game-invaders g)))

;; ListOfInvaders -> Boolean
(define (any-invader-at-bottom? loi)
  (let ((at-bottom? (lambda (i) (>= (invader-y i) HEIGHT))))
    (ormap at-bottom? loi)))

;; Game -> Image
(define (render g)
  (overlay (draw-invaders (game-invaders g)) (draw-missiles (game-missiles g)) (draw-tank (game-tank g))))

;; ListOfInvader -> Image
(define (draw-invaders loi) (foldl overlay empty-image (map draw-invader loi)))

;; ListOfMissiles -> Image
(define (draw-missiles lom) (foldl overlay empty-image (map draw-missile lom)))

;; Invader -> Image
(define (draw-invader i) (place-image INVADER (invader-x i) (invader-y i) MTS))

;; Missile -> Image
(define (draw-missile m) (place-image MISSILE (missile-x m) (missile-y m) MTS))

;; Tank -> Image
(define (draw-tank t) (place-image TANK (tank-x t) (- HEIGHT TANK-HEIGHT/2) MTS))

;; Run Game:
;; =============================================================================
(define T (make-tank (/ WIDTH 2) 1))   ; center going right
(define G (make-game empty empty T 0))
(main G)
