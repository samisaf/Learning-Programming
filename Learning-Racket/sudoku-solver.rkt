#lang racket

;; Domain Analysis
;; Board: Represents a sudoku board. This is a list of 81 cells.
;; Cell: Represents a sudoku cell. Its value is 0 if the cell is unfilled, or 1-9 when filled with these numbers.
;; Unit: A sudko board has 9 row units, 9 column units, and 9 box units. Each unit is a list of 9 cell positions (ranging from 0 to 91)
;; The board positions are as follows:

;; 00 01 02 | 03 04 05 | 06 07 08
;; 09 10 11 | 12 13 14 | 15 16 17
;; 18 19 20 | 21 22 23 | 24 25 26
;; ---------+----------+---------
;; 27 28 29 | 30 31 32 | 33 34 35
;; 36 37 38 | 39 40 41 | 42 43 44
;; 45 46 47 | 48 49 50 | 51 52 53
;; ---------+----------+---------
;; 54 55 56 | 57 58 59 | 60 61 62
;; 63 64 65 | 66 67 68 | 69 70 71
;; 72 73 74 | 75 76 77 | 78 79 80

(require rackunit)

(define ROW-UNITS (list
                   (list 00 01 02 03 04 05 06 07 08)
                   (list 09 10 11 12 13 14 15 16 17)
                   (list 18 19 20 21 22 23 24 25 26)
                   (list 27 28 29 30 31 32 33 34 35)
                   (list 36 37 38 39 40 41 42 43 44)
                   (list 45 46 47 48 49 50 51 52 53)
                   (list 54 55 56 57 58 59 60 61 62)
                   (list 63 64 65 66 67 68 69 70 71)
                   (list 72 73 74 75 76 77 78 79 80)))

(define COL-UNITS (list
                   (list 00 09 18 27 36 45 54 63 72)
                   (list 01 10 19 28 37 46 55 64 73)
                   (list 02 11 20 29 38 47 56 65 74)
                   (list 03 12 21 30 39 48 57 66 75)
                   (list 04 13 22 31 40 49 58 67 76)
                   (list 05 14 23 32 41 50 59 68 77)
                   (list 06 15 24 33 42 51 60 69 78)
                   (list 07 16 25 34 43 52 61 70 79)
                   (list 08 17 26 35 44 53 62 71 80)))

(define BOX-UNITS (list
                   (list 00 01 02 09 10 11 18 19 20)
                   (list 03 04 05 12 13 14 21 22 23)
                   (list 06 07 08 15 16 17 24 25 26)
                   (list 27 28 29 36 37 38 45 46 47)
                   (list 30 31 32 39 40 41 48 49 50)
                   (list 33 34 35 42 43 44 51 52 53)
                   (list 54 55 56 63 64 65 72 73 74)
                   (list 57 58 59 66 67 68 75 76 77)
                   (list 60 61 62 69 70 71 78 79 80)))

;; Position -> Row 
(define (row p) (quotient p 9))

;; Position -> Column
(define (col p) (remainder p 9))

;; Position -> Box
(define (box p)
  (let ((r (row p)) (c (col p)))
    (+ (* (quotient r 3) 3) (quotient c 3))))

;; Board -> Boolean | Board
;; Solves a sukoku board
(define (solve-board b)
  (if (solved? b) b                      ; if the board is full, then the board is solved
      (solve-boards (next-boards b))))   ; otherwise generate children board, and then solve them

;; listof boards -> Boolean | Board
(define (solve-boards lob)
  (if (empty? lob) false                       ; if the listof boards is empty then there is no solution
      (let ((try (solve-board (first lob))))   ; otherwise try to solve the first board
        (if (not (false? try))                 ; if there is a solution then
            try                                ; return that solution
            (solve-boards (rest lob))))))      ; otherwise solve the rest of the boards

;; Board -> Boolean
;; Returns true if the board is solved, ie, the board is full, and none of its cells are empty
(define (solved? board)
  (not (ormap false? board))) ; if any cell is empty(false) then the board is not solved

;; Board -> listof Boards
;; Returns the children board of the given board
(define (next-boards b)
  (keep-valid (fill-1-9 (find-blank b) b)))

;; listof Board -> listof Boards
;; keep valid board according to sudoku rules. No duplicates in any rows, columns, or boxes
(define (keep-valid lob)
  (filter valid-board? lob))

;; Board -> Boolean
;; A valid board is one where there are no duplicates in any rows, columns, or boxes
(define (valid-board? b)
  (let ((units (append (rows b) (cols b) (boxes b))))
    (andmap valid-unit? units)))

;; listof Values -> Boolean
(define (valid-unit? u)
  (no-duplicates? (remove-blanks u)))

;; listof Values -> listof Values
;; Removes blank (false) cells
(define (remove-blanks lov)
  (if (empty? lov)
      lov
      (if (number? (first lov))
          (cons (first lov) (remove-blanks (rest lov)))
          (remove-blanks (rest lov)))))

;; listof values -> Boolean
;; Returns true if there are no duplicates in the list
(define (no-duplicates? lov)
  (if (false? (check-duplicates lov)) true false))
              
;; listof Position Board -> Value
;; Takes a list of positions and returns the corrosponding values in the board
(define (values-of lop board)
  (map (lambda (p) (list-ref board p)) lop))

;; Board -> listof listof values
(define (rows board)
  (map (lambda (lop) (values-of lop board)) ROW-UNITS))

;; Board -> listof listof values
(define (cols board)
  (map (lambda (lop) (values-of lop board)) COL-UNITS))

;; Board -> listof listof values
(define (boxes board)
  (map (lambda (lop) (values-of lop board)) BOX-UNITS))

;; Position Board -> listof Board
;; Takes a position and board, and fills the first empty position with 1 through 9. Produces a list of board accordingly
(define (fill-1-9 pos board)
  (let ((vals (list 1 2 3 4 5 6 7 8 9)) (partial (lambda (n) (fill-with-n n pos board))))
    (map partial vals)))
                                        
;; Number Position Board -> Board
;; Takes a number, a position and board, and fills the first empty position with that number
(define (fill-with-n n pos board)
  (if (= pos 0)
      (cons n (rest board))
      (cons (first board) (fill-with-n n (sub1 pos) (rest board)))))

;; Board -> Position
;; Takes a board, and returns the position of the first empty cell
(define (find-blank board)
  (if (empty? board)
      (error "board is full")
      (if (false? (first board))
          0
          (+ 1 (find-blank (rest board))))))

;; Tests
;; ===================================================================
(define B false) ;B stands for blank


(define BD1 
  (list B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B))

(define BD2 
  (list 1 2 3 4 5 6 7 8 9 
        B B B B B B B B B 
        B B B B B B B B B 
        B B B B B B B B B 
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B))

(define BD3 
  (list 1 B B B B B B B B
        2 B B B B B B B B
        3 B B B B B B B B
        4 B B B B B B B B
        5 B B B B B B B B
        6 B B B B B B B B
        7 B B B B B B B B
        8 B B B B B B B B
        9 B B B B B B B B))

(define BD4                ;easy
  (list 2 7 4 B 9 1 B B 5
        1 B B 5 B B B 9 B
        6 B B B B 3 2 8 B
        B B 1 9 B B B B 8
        B B 5 1 B B 6 B B
        7 B B B 8 B B B 3
        4 B 2 B B B B B 9
        B B B B B B B 7 B
        8 B B 3 4 9 B B B))

(define BD4s               ;solution to 4
  (list 2 7 4 8 9 1 3 6 5
        1 3 8 5 2 6 4 9 7
        6 5 9 4 7 3 2 8 1
        3 2 1 9 6 4 7 5 8
        9 8 5 1 3 7 6 4 2
        7 4 6 2 8 5 9 1 3
        4 6 2 7 5 8 1 3 9
        5 9 3 6 1 2 8 7 4
        8 1 7 3 4 9 5 2 6))

(define BD5                ;hard
  (list 5 B B B B 4 B 7 B
        B 1 B B 5 B 6 B B
        B B 4 9 B B B B B
        B 9 B B B 7 5 B B
        1 8 B 2 B B B B B 
        B B B B B 6 B B B 
        B B 3 B B B B B 8
        B 6 B B 8 B B B 9
        B B 8 B 7 B B 3 1))

(define BD5s               ;solution to 5
  (list 5 3 9 1 6 4 8 7 2
        8 1 2 7 5 3 6 9 4
        6 7 4 9 2 8 3 1 5
        2 9 6 4 1 7 5 8 3
        1 8 7 2 3 5 9 4 6
        3 4 5 8 9 6 1 2 7
        9 2 3 5 4 1 7 6 8
        7 6 1 3 8 2 4 5 9
        4 5 8 6 7 9 2 3 1))

(define BD6                ;hardest ever? (Dr Arto Inkala)
  (list B B 5 3 B B B B B 
        8 B B B B B B 2 B
        B 7 B B 1 B 5 B B 
        4 B B B B 5 3 B B
        B 1 B B 7 B B B 6
        B B 3 2 B B B 8 B
        B 6 B 5 B B B B 9
        B B 4 B B B B 3 B
        B B B B B 9 7 B B))

(define BD7                 ; no solution 
  (list 1 2 3 4 5 6 7 8 B 
        B B B B B B B B 2 
        B B B B B B B B 3 
        B B B B B B B B 4 
        B B B B B B B B 5
        B B B B B B B B 6
        B B B B B B B B 7
        B B B B B B B B 8
        B B B B B B B B 9))

(check-eq? (row  0) 0)
(check-eq? (row 10) 1)
(check-eq? (row 80) 8)

(check-eq? (col  0) 0)
(check-eq? (col 10) 1)
(check-eq? (col 80) 8)

(check-eq? (box  0) 0)
(check-eq? (box 10) 0)
(check-eq? (box 42) 5)
(check-eq? (box 80) 8)

(check-equal? (solved? BD4) false)
(check-equal? (solved? BD4s) true)

(check-equal? (solve-board BD4) BD4s)
(check-equal? (solve-board BD5) BD5s)
(print BD6)
(print (solve-board BD6))