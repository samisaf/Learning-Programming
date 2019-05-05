;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname acid=base) (read-case-sensitive #t) (teachpacks ((lib "convert.ss" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "convert.ss" "teachpack" "htdp")))))
(define (acidemia? pH) (< pH 7.35))
(define (alkalemia? pH) (> pH 7.45))
(define (neutral? pH) (not (or (acidemia? pH) (alkalemia? pH))))

(define (status pH)
  (cond ((acidemia? pH) "Acidemia")
        ((alkalemia? pH) "Alkalemia")
        (else "Neutral")))
