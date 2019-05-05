(define (sqroot x) 
  (define (sqrt-iter guess)
    (cond ((good-enough? guess) guess)
          (else (sqrt-iter (improve guess)))))
  
  (define (good-enough? guess)
    (< (abs(- x (square guess))) (/ x 10000)))
  
  (define (square y) (* y y))
  
  (define (improve guess)
    (average guess (/ x guess)))
  
  (define (average z y) (/ (+ z y) 2))
  
  (sqrt-iter x 1.0))

