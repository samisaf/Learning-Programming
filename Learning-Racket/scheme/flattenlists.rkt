(define a 1)

(define (flatten l)
  (set! a (+ a 1))
  (cond ((null? l) l)
        ((list? (car l)) (append (flatten(car l)) (flatten(cdr l))))
        (else (cons (car l) (flatten (cdr l))))))

(define l1 '(1 (2 3) (4) (5 (6(7))) (8 9)))

(define l2 '(1 (2 3) (4)() (5 (6(7))) (8 9)))
  