(define (try num guess)
  (if (good-enough? num guess)
      guess
      (try num (improve num guess))))

(define (good-enough? num guess)
  (< (abs (- num (* guess guess))) 0.00001))

(define (improve num guess)
  (average guess (/ num guess)))

(define (average x y)
  (/ (+ x y ) 2))

(define (squaro x)
  (try x 1.0))

(squaro 4)
(squaro 100)
