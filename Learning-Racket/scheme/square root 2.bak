(define (sqroot x) (+ (sqrt-iter x 1) 0.0))

(define (sqrt-iter x guess)
  (cond ((good-enough? x guess) guess)
        (else (sqrt-iter x (improve guess x)))))

(define (good-enough? x guess)
  (< (abs(- x (square guess))) 0.00001))

(define (square x) (* x x))

(define (improve guess x)
  (average guess (/ x guess)))

(define (average x y) (/ (+ x y) 2))

