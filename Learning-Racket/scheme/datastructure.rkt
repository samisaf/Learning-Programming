(define (combine a b)
  (define (select n)
    (cond ((= 1 n) a)
          ((= 2 n) b)))
  select)

(define (first pair)
  (pair 1))

(define (second pair)
  (pair 2)) 