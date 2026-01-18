\input

6001mac

\psetheader{Spring Semester, 1992-93}{Problem Set 2}
\medskip
\begin{flushleft}
Issued:  February 9, 1992 \\
\smallskip
Tutorial preparation for: Week of February 15 \\
\smallskip

Written solutions are due in Recitation: 
\begin{itemize}
\item Even Numbered Recitation Sections: Wednesday February 17; 
\item Odd Numbered Recitation Sections: Friday, February 19.
\end{itemize}

\smallskip


Reading: 
\begin{tightlist}

\item Text: Complete Chapter 1.

\end{tightlist}
\end{flushleft}

# 1. Tutorial Exercises

#### Exercise 2.1

Do exercise 1.23 of the text. You should make use of the `sum` procedure
defined on p. 47 of the notes.

#### Exercise 2.2

Do exercise 1.24 of the text.

#### Exercise 2.3

Determine the orders of growth for the `my-sine` and `recurrent-sine`
procedures in Problem Set 1. Consider both the growth of space and
number of operations and their dependence on the size of the argument
and the value of the global variable `epsilon`, using the number of
applications of the `my-sine` and `recurrent-sine` procedures to measure
the number of operations.

How would the orders of growth for the `my-sine` procedure be changed if
it had been defined as

\beginlisp

(define (my-sine x) (if (small-enuf? x) x (\* (my-sine (/ x 3)) (- 3 (\*
4 (my-sine (/ x 3)) (my-sine (/ x 3))))))) `\endlisp`{=latex}

####  Exercise 2.4 

Procedure `repeated` is defined as

\beginlisp

(define (repeated p n) (cond ((= n 0) (lambda (x) x)) ((= n 1) p) (else
(lambda (x) (p ((repeated p (-1+ n)) x)))))) `\endlisp `{=latex}

Determine the values of the expressions

\beginlisp

((repeated square 2) 5) `\endlisp`{=latex}

and

\beginlisp

((repeated square 5) 2). `\endlisp`{=latex}

In tutorial you may be asked how the interpreter evaluates expressions
similar to these.

# 2. Continued Fractions

Continued fractions play an important role in number theory and in
approximation theory. In this programming assignment, you will write
some short procedures that evaluate continued fractions. There is no
predefined code for you to load.

An infinite continued fraction is an expression of the form

$$f={{N_1} \over 
  {\displaystyle D_1 + {{\strut N_2} \over 
                {\displaystyle D_2 + \ddots }}}}$$

One way to approximate an irrational number is to expand as a continued
fraction, and truncate the expansion after a sufficient number of terms.
Such a truncation---a so-called *$k$-term finite continued
fraction*---has the form $${{N_1} \over {\displaystyle D_1+
        {\strut {N_2} \over {\displaystyle \ddots +
        {\strut {N_K} \over {\displaystyle D_K+0}}}}}}$$

For example, if the $N_i$ and the $D_i$ are all 1, it is not hard to
show that the infinite continued fraction expansion
$${1 \over {\displaystyle 1 +
        {\strut 1 \over {\displaystyle 1 +\ddots } }}}$$ converges to
$1/\phi\approx .618$ where $\phi$ is the *golden ratio*
$$\frac{1+ \sqrt{5}}{2}$$ The first few finite continued fraction
approximations (also called *convergents*) are:
$$1, \frac{1}{2}=.5, \frac{2}{3}\approx .667, \frac{3}{5}=.6,
\frac{5}{8}=.625, \cdots$$

#### Pre-Lab Exercise 2.1

Suppose that `n` and `d` are procedures of one argument (the term index)
that return the $n_i$ and $d_i$ of the terms of the continued fraction.

Define procedures `cont-frac-r` and ` cont-frac-i` such that evaluating
`(cont-frac-r n d k)` and ` (cont-frac-i n d k)` each compute the value
of the $k$-term finite continued fraction. The process that results from
applying ` cont-frac-r` should be recursive, and the process that
results from applying `cont-frac-i` should be iterative. Check your
procedures by approximating $1/\phi$ using

\beginlisp

(cont-frac (lambda (i) 1) (lambda (i) 1) k) `\endlisp`{=latex}

for successive values of `k`, where `cont-frac` is ` cont-frac-r` or
`cont-frac-i`. How large must you make `k` in order to get an
approximation that is accurate to 4 decimal places? Turn in a listing of
your procedures.

#### Lab Exercise 2.2

One of the first formulas for $\pi$ was given around 1658 by the English
mathematician Lord Brouncker:

$$4/\pi -1  =  1/(2 + 9/(2 + 25/ (2 + 49/ (2 + 81 /(2 + \cdots$$

This leads to the following procedure for approximating $\pi$:

\beginlisp

(define (estimate-pi k) (/ 4 (+ (brouncker k) 1))) `\endlisp`{=latex}

\beginlisp

(define (square x) (\* x x)) `\null`{=latex} (define (brouncker k)
(cont-frac (lambda (i) (square (- (\* 2 i) 1))) (lambda (i) 2) k))
`\endlisp`{=latex}

where `cont-frac` is one of your procedures `cont-frac-r` or
`cont-frac-i`. Use this method to generate approximations to $\pi$.
About how large must you take $k$ in order to get 2 decimal places
accuracy? (Don't try for more places unless you are very patient.)

## Computing inverse tangents

Continued fractions are frequently encountered when approximating
functions. In this case, the $N_i$ and $D_i$ can themselves be constants
or functions of one or more variables. For example, a continued fraction
representation of the inverse tangent function was published in 1770 by
the German mathematician J.H. Lambert:

$${\tan^{-1}x} ={x \over 
{\displaystyle 1+{\strut {\left( {1x} \right)^2} \over 
{\displaystyle 3+{\strut {\left( {2x} \right)^2} \over 
{\displaystyle 5+{\strut {\left( {3x} \right)^2} \over {7+\ddots }}}}}}}}$$

#### Pre-Lab Exercise 2.3

Define a procedure `(atan-cf k x)` that computes an approximation to the
inverse tangent function based on a $k$-term continued fraction
representation of $\tan^{-1} x$ given above. Provided you use the
correct arguments, it should be possible to define this procedure
directly in terms of the `cont-frac` procedure you have already defined.

#### Lab Exercise 2.4

Verify that your procedure works (and check its accuracy) by using it to
compute the tangent of a number of arguments, such as $0$, $1$, $3$,
$10$, $30$, $100$. Compare your results with those computed using
Scheme's `atan` procedure[^1].

How many terms are required to get reasonable accuracy? Turn in your
procedure definition together with some sample results.

The idea of a continued fraction can be generalized to include arbitrary
nested expressions such as (1) and (2) below:

$$%MathType!ZZhx46!633xZh9VgeZpaeVeH9Kppzqiq9taFeGa2-Y8WaXhsyprZBeecuFmWh
%bif9Y+se2t+8KbHa1pc8racy-lZddeFiH9enVrqiq9YaFeGu0lRu03
%Nu0l7lryp5ZtgeVeH92cFeGa2-Y8WaXhsyprZBeeFiH9RbFeGu0lRa
%1paaaaaaa8!2AB6!
\left( {N_1/\left( {D_1+N_2/\left( {D_2+\cdots +N_k/\left( {D_k+0}
\right)} \right)} \right)} \right)$$
$$%MathType!ZZhx46!633xZh9VgeZpaeVeH9Kppzqiq9taFeGu0VYhsyprZBeecuFmWhbif9
%ln-aq8se2t+8KbHa1pc8rasr-kFiH9enVrqiq9YaFeGu0VKu03Nu0V
%08daXlryp5ZtgeVeH92cFeGu0VYhsyprZBeeFiH9RbFeGu0VKa1pba
%aaaap!2B00!
\left( {N_1+D_1\times \left( {N_2+D_2\times \cdots \times \left(
{N_k+D_k\times 1} \right)} \right)} \right)$$ or in general
$$\left( T_1 {\cal O}_1 \left( T_2 {\cal O}_2 \cdots 
            \left( T_k {\cal O}_k R \right) \right) \right)$$ where the
$T_i$ are the terms of the expression, the ${\cal O}_i$ are the
arithmetic operators, and $R$ is the residual term (perhaps representing
the effects of terms beyond the $k$th in an approximation). For example,
in (2) the ${\cal O}_i$ are alternately the addition and the
multiplication operators, and $R=1$.

#### Pre-Lab Exercise 2.5

Define a procedure `nested-acc` such that evaluating
` (nested-acc op r term k)` computes the value of a nested expression.
Argument `op` is a procedure of one argument, that when applied to the
term index $i$ returns the $i$th arithmetic operation ${\cal O}_i$
(which is a procedure of two arguments). The `r` argument represents the
effect of the $R$ term. Turn in a listing of your procedure.

How would you use your `nested-acc` procedure to compute a $k$-term
approximation to the function
$$%MathType!ZZhx46!633xZh9VgeZtbecu-esrVSZtbecu-esrVSZtbecu-esrVSsrFFWhba
%Fea8raaa!0C5C!
f(x)=\sqrt {x+\sqrt {x+\sqrt {x+\cdots }}}$$

#### Lab Exercise 2.6

Verify that your `nested-acc` procedure works properly by computing an
approximation to $f(1)$, whose value is the golden ratio. How large must
you make `k` in order to get an approximation that is accurate to 4
decimal places? Turn in a listing of your procedures.

#### Lab Exercise 2.7

Verify that your `nested-acc` procedure works properly by using it to
compute the value of the Taylor series for the sine function discussed
in Problem Set 1.

In certain continued fractions all $N_i$ and $D_i$ terms are equal and
it is convenient to regard the fraction as being "built-up" from a base
by a repetitive operation. For example, given the base function $B$ and
the augmentors $N$ and $D$, one can build $N/(D+B)$. This can be
computed in a straightforward fashion by the procedure `build`

\beginlisp

(define (build n d b) (/ n (+ d b))) `\endlisp`{=latex}

The value of the expression that is represented by the two-term
continued fraction

$$%MathType!ZZhx46!633xZh9VgeZtdeVeH9KdXlryprsrVSZtdeVeH9KdXlryprsrVSVeH9
%Kaaaa8!1036!
{N \over {D+{N \over {D+B}}}}$$

can then be computed by evaluating `(build n d (build n d b))`. When
further composition is of interest, it is possible to use the `repeated`
procedure defined above.

####  Pre-Lab Exercise 2.8 

Write a procedure `repeated-build` of four arguments `k`, `n`, `d`, and
`b`, which returns a result that is equivalent to applying the `build`
procedure $k$ times. In particular `(repeated-build 2 n d b)` should
return the same result as `(build n d (build n d b))`. Your
implementation of ` repeated-build` should make use of the `repeated`
procedure given above (in a non-trivial way).

####  Lab Exercise 2.9 

Verify that your `repeated-build` procedure works properly by evaluating
the continued fraction for the reciprocal of the golden ratio:

$${1 \over {\displaystyle 1 +
        {\strut 1 \over {\displaystyle 1 + \ddots } }}}$$ converges to
$1/\phi\approx .618$ where $\phi$ is the *golden ratio*

Now consider the problem of computing the rational functions
$r_1(x)=(1+0x)/(1+x)$, $r_2(x)=(1+x)/(2+x)$, $r_3(x)=(2+x)/(3+2x)$,
$$%MathType!ZZhx46!633xZh9VgeVeH9Nmpzq8se2Bl8raZddeFiH94bGu03Z8dbXhsyFyZB
%eeFiH9RbFeGu0l7lryVfZtgeVeH92kf9slq9taFeWhsypEq8He2h28
%gbXhsy-Asr-kcuFmWhbif9Y+se2BX8KbXlryVTWhb8He2JhasrFpZp
%eecuFmqiq9Xif9R8He2l38gbXhsy-AsrFlcuFmWhbm-aq8se2Fiaaa
%Wd!3C40!
r_k\left( x \right)={{a_k+a_{k-1}x} \over {a_{k+1}+a_kx}}={1 \over
{1+r_{k-1}\left( x \right)}}$$ where $a_k$ is the $k$-th Fibonacci
number ($a_0=0$).

####  Lab Exercise 2.10 

Making use of your `repeated-build` procedure, define a procedure `r` of
one argument `k` that evaluates to a procedure of one argument `x` that
computes $r_k(x)$. For example, evaluating `((r 2) 0)` should return
$0.5$.

## Optional Problem

One difficulty with using continued fractions to evaluate functions is
that there is generally no way to determine in advance how many terms of
an infinite continued fraction are required to achieve a specified
degree of accuracy. The result obtained by computing the value of a
continued fraction by working backwards from the $k$-th to the first
term cannot generally be used in the computation of the $k+1$ term
continued fraction.

Remarkably, there is a general technique for computing the value of a
continued fraction that overcomes this problem. This result is based on
the recurrence formulas $$A_{-1}=1;B_{-1}=0;A_0=0;B_0=1$$
$$%MathType!ZZhx46!633xZh9VgeVeH9umpzq8se2Bk8rasrFpFiH9enVrq8He2Rg8raVeH9
%umpzq8se2BQu0lTa1pb8rasr-kFiH9onVrq8He2Rg8raVeH9umpzq8
%se2BQu0lTa1pc8raaa!212C!
A_j=D_jA_{j-1}+N_jA_{j-2}$$
$$%MathType!ZZhx46!633xZh9VgeVeH9Kmpzq8se2Bk8rasrFpFiH9enVrq8He2Rg8raVeH9
%Kmpzq8se2BQu0lTa1pb8rasr-kFiH9onVrq8He2Rg8raVeH9Kmpzq8
%se2BQu0lTa1pc8raaa!212F!
B_j=D_jB_{j-1}+N_jB_{j-2}.$$ It is easy to show that the $k$-term
continued fraction can be computed as
$$%MathType!ZZhx46!633xZh9VgeVeH9Mnpzq8se2Bl8rasrFpZpeeFiH9bnVrq8He2Vg8ra
%q8se2tY8KbXlryVTWhbaaa!1500!
f_k={{A_k} \over {B_k}}.$$ By determining whether $f_j$ is close enough
to $f_{j-1}$, it is easy to determine whether to include more terms in
the approximation.

Define a procedure that computes the value of a continued fraction using
this algorithm. The process that evolves when the procedure is applied
should be iterative. Test that your procedure works by computing the
tangent of various angles. Use the tracing feature of SCHEME to
determine how many terms are evaluated.

\vspace{10ex}

Turn in this sheet with your answers to the questions in the problem
set.

\vspace{6ex}

How much time did you spend on this homework assignment? Report
separately time spent before going to lab and time spent in the 6.001
lab.

\vspace{6ex}

If you cooperated with other students working this problem set please
indicate their names in the space below. As you should know, as long as
the guidelines described in the *6.001 Policy on Cooperation* handout
are followed, such cooperation is allowed and encouraged.

[^1]: Evaluating `(atan x)` returns the same value as `(atan x 1)`, an
    angle in the range $(-\pi/2, +\pi/2)$.
