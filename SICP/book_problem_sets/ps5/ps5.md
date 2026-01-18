\tracingmacros=3
\input

../6001mac

\outer
\def\beginlispbig{%
  \begin{minipage}[t]{\linewidth}
  \begin{list}{$\bullet$}{%
    \setlength{\topsep}{0in}
    \setlength{\partopsep}{0in}
    \setlength{\itemsep}{0in}
    \setlength{\parsep}{0in}
    \setlength{\leftmargin}{1.5em}
    \setlength{\rightmargin}{0in}
    \setlength{\itemindent}{0in}
  }\item[]
  \obeyspaces
  \obeylines \tt}
\psetheader{Sample Problem Set}{Math Set}

::: center
\large

**A Generic Arithmetic Package**
:::

\medskip

`\noindent`{=latex} This problem set is based on Sections 2.5 and 2.6 of
the notes, which discuss a generic arithmetic system that is capable of
dealing with rational functions (quotients of polynomials). You should
study and understand these sections and also carefully read and think
about this handout before attempting to solve the assigned problems.

There is a larger amount of code for you to manage in this problem set
than in previous ones. Furthermore, the code makes heavy use of
data-directed techniques. We do not intend for you to study it all---and
you may run out of time if you try. This problem set will give you an
opportunity to acquire a key professional skill: mastering the code
*organization* well enough to know what you need to understand and what
you don't need to understand.

Be aware that in a few places, which will be explicitly noted below,
this problem set modifies (for the better!) the organization of the
generic arithmetic system described in the text.

# Generic Arithmetic

The generic arithmetic system consists of a number of pieces. The
complete code is attached at the end of the handout. All of this code
will be loaded into Scheme when you load the files for this problem set.
You will not need to edit any of it. Instead you will augment the system
by adding procedures and installing them in the system.

Hand in your PreLab work, computer listings of all the procedures you
write in lab, and transcripts showing that the required functionality
was added to the system. The transcript should include enough tests to
exercise the functionality of your modifications and to demonstrate that
they work properly.

## The basic generic arithmetic system

There are three kinds, or *subtypes*, of generic numbers in the system
of this Problem Set: generic ordinary numbers, generic rational numbers,
and generic polynomials. Elements of these subtypes are tagged items
with one of the tags `\mbox{\tt number}`{=latex},
`\mbox{\tt rational}`{=latex}, or `\mbox{\tt polynomial}`{=latex},
followed by a data structure representing an element of the
corresponding subtype. For example, a generic ordinary number has tag
`\mbox{\tt number}`{=latex} and another part, called its *contents*,
which represents an ordinary number.

We can summarize this in a type equation:
$$\mbox{Generic-\-Num}= (\{\mbox{\tt number}\}\times\mbox{RepNum}) \cup(\{\mbox{\tt rational}\}\times\mbox{RepRat}) \cup
        (\{\mbox{\tt polynomial}\}\times\mbox{RepPoly}).$$

The type tagging mechanism is the simple one described on p. 165 of the
text, and the `\mbox{\tt apply-generic}`{=latex} is the one *without
coercions* described in section 2.5.3. The code for these is in
`\mbox{\tt types.scm}`{=latex}.

We will also assume that the commands `put` and `get` are available to
automagically update the table of methods around which the system is
designed. You needn't be concerned in this problem set how ` put` and
`get` are implemented[^1].

Some familiar arithmetic operations on generic numbers are

\beginlisp

(define (add x y) (apply-generic 'add x y)) (define (sub x y)
(apply-generic 'sub x y)) (define (mul x y) (apply-generic 'mul x y))
(define (div x y) (apply-generic 'div x y)) `\endlisp`{=latex} These are
all of type
$(\mbox{Generic-\-Num},\mbox{Generic-\-Num}) \rightarrow\mbox{Generic-\-Num}$.
We also have

\beginlisp

(define (negate x) (apply-generic 'negate x)) `\endlisp`{=latex} of type
$\mbox{Generic-\-Num}\rightarrow\mbox{Generic-\-Num}$, and

\beginlisp

(define (=zero? x) (apply-generic '=zero? x)) `\endlisp`{=latex} of type
$\mbox{Generic-\-Num}\rightarrow\mbox{Sch-\-Bool}$.

Using these operations, compound generic operations can be defined, such
as

\beginlisp

(define (square x) (mul x x)) `\endlisp`{=latex}

## Packages

The code for the generic number system of this problem set has been
organized in `\mbox{\tt ps5-code.scm}`{=latex} into groups of related
definitions labelled as "packages." A package generally consists of all
the procedures for handling a particular type of data, or for handling
the interface between packages.

The packages described in the text are enclosed in a package
installation procedure that sets up internal definitions of the
procedures in the package. An example is
`\mbox{\tt the-number-package}`{=latex} on p. 178. This ensures there
will be no conflict if a procedure with the same name is used in another
package, allowing packages to be developed separately with minimal
coordination of naming conventions.

In this assignment it will be more convenient *not* to enclose the
packages into internal definitions. Instead, the code is laid out
textually in packages, but essentially everything is defined at "top
level." You will see that we have therefore been careful to choose
different names for corresponding procedures in different packages,
e.g., `\mbox{\tt +}`{=latex} which adds in the number package and
`\mbox{\tt +poly}`{=latex} which adds in the polynomial package.

## Ordinary numbers

To install ordinary numbers, we must first decide how they are to be
represented. Since Scheme already has an elaborate system for handling
numbers, the most straightforward thing to do is to use it, namely, let
$$\mbox{RepNum}= \mbox{Sch-\-Num}.$$ This allows us to define the
methods that handle generic ordinary numbers simply by calling the
Scheme primitives `\mbox{\tt +}`{=latex}, `\mbox{\tt -}`{=latex}, ...,
as in section 2.6.1. So we can immediately define interface procedures
between `\mbox{RepNum}`{=latex}'s and the Generic Number System:

\beginlisp

(define (+number x y) (make-number (+ x y))) (define (-number x y)
(make-number (- x y))) (define (\*number x y) (make-number (\* x y)))
(define (/number x y) (make-number (/ x y))) `\endlisp`{=latex} These
are of type
$(\mbox{RepNum}, \mbox{RepNum}) \rightarrow(\{\mbox{\tt number}\}\times\mbox{RepNum})$.
Also,

\beginlisp

(define (negate-number x) (make-number (- x))) `\endlisp`{=latex} of
type
$\mbox{RepNum}\rightarrow(\{\mbox{\tt number}\}\times\mbox{RepNum})$,

\beginlisp

(define (=zero-number? x) (= x 0)) `\endlisp`{=latex} of type
$\mbox{RepNum}\rightarrow\mbox{Sch-\-Bool}$, and

\beginlisp

(define (make-number x) (attach-tag 'number x)) `\endlisp`{=latex} of
type
$\mbox{RepNum}\rightarrow(\{\mbox{\tt number}\}\times\mbox{RepNum})$.

All but the last of these procedures get installed in the table as
methods for handling generic ordinary numbers:

\beginlisp

(put 'add '(number number) +number) (put 'sub '(number number) -number)
(put 'mul '(number number) \*number) (put 'div '(number number) /number)
(put 'negate '(number) negate-number) (put '=zero? '(number)
=zero-number?) `\endlisp`{=latex}

The number package should provide a means for a user to create generic
ordinary numbers, so we include a user-interface procedure[^2] of type
$\mbox{Sch-\-Num}\rightarrow(\{\mbox{\tt number}\}\times\mbox{RepNum})$,
namely, `\beginlisp`{=latex} (define (create-number x) (attach-tag
'number x)) `\endlisp`{=latex}

#### Exercise 5.1A

The generic equality predicate
$$\mbox{\tt equ?}:(\mbox{Generic-\-Num},\mbox{Generic-\-Num}) \rightarrow\mbox{Sch-\-Bool}$$
is supposed to test equality of its arguments. Define an
`\mbox{\tt =number}`{=latex} procedure in the Number Package suitable
for installation as a method allowing generic `\mbox{\tt equ?}`{=latex}
to handle generic ordinary numbers. Include the type of
`\mbox{\tt =number}`{=latex} in comments accompanying your definition.

#### Lab exercise 5.1B

Install `equ?` as an operator on numbers in the generic arithmetic
package. Test that it works properly on generic ordinary numbers.

## Rational numbers

The second piece of the system is a Rational Number package like the one
described in section 2.1.1. The difference is that the arithmetic
operations used to combine numerators and denominators are *generic*
operations, rather than the primitive `+`, `-`, etc. This difference is
important, because it allows "rationals" whose numerators and
denominators are arbitrary generic numbers, rather than only integers or
ordinary numbers. The situation is like that in Section 2.6.3 in which
the use of generic operations in `+terms` and `terms` allowed
manipulation of polynomials with arbitrary coefficients.

We begin by specifying the representation of rationals as *pairs* of
`\mbox{Generic-\-Num}`{=latex}'s:
$$\mbox{RepRat}= \mbox{Generic-\-Num}\times\mbox{Generic-\-Num}$$ with
constructor

\beginlisp

(define (make-rat numerator denominator) (cons numerator denominator))
`\endlisp`{=latex} of type
$\mbox{Generic-\-Num}, \mbox{Generic-\-Num}\rightarrow\mbox{RepRat}$,
and selectors

\beginlisp

(define numer car) (define denom cdr) `\endlisp`{=latex}

Note that `make-rat` does not reduce rationals to lowest terms as in
Section 2.1.1, because `gcd` makes sense only in certain cases---such as
when numerator and denominator are integers---but we are allowing
arbitrary numerators and denominators.

Now we define basic procedures of type
$(\mbox{RepRat}, \mbox{RepRat}) \rightarrow\mbox{RepRat}$ within the
Rational Number package:

\beginlisp

(define (+rat x y) (make-rat (add (mul (numer x) (denom y)) (mul (denom
x) (numer y))) (mul (denom x) (denom y))))

(define (-rat x y) (make-rat (sub (mul (numer x) (denom y)) (mul (denom
x) (numer y))) (mul (denom x) (denom y))))

(define (\*rat x y) (make-rat (mul (numer x) (numer y)) (mul (denom x)
(denom y))))

(define (/rat x y) (make-rat (mul (numer x) (denom y)) (mul (denom x)
(numer y)))) `\endlisp`{=latex} There is also

\beginlisp

(define (negate-rat x) (make-rat (negate (numer x)) (denom x)))
`\endlisp`{=latex} of type $\mbox{RepRat}\rightarrow\mbox{RepRat}$,

\beginlisp

(define (=zero-rat? x) (=zero? (numer x))) `\endlisp`{=latex} of type
$\mbox{RepRat}\rightarrow\mbox{Sch-\-Bool}$, and finally,

\beginlisp

(define (make-rational x) (attach-tag 'rational x)) `\endlisp`{=latex}
of type
$\mbox{RepRat}\rightarrow(\{\mbox{\tt rational}\}\times\mbox{RepRat})$.

Next, we provide the interface between the Rational package and the
Generic Number System, namely the methods for handling rationals.

\beginlisp

(define (+rational x y) (make-rational (+rat x y))) (define (-rational x
y) (make-rational (-rat x y))) (define (\*rational x y) (make-rational
(\*rat x y))) (define (/rational x y) (make-rational (/rat x y)))
`\endlisp`{=latex} of type
$(\mbox{RepRat}, \mbox{RepRat}) \rightarrow(\{\mbox{\tt rational}\}\times\mbox{RepRat})$,

\beginlisp

(define (negate-rational x) (make-rational (negate-rat x)))
`\endlisp`{=latex} of type
$\mbox{RepRat}\rightarrow(\{\mbox{\tt rational}\}\times\mbox{RepRat})$,
and

\beginlisp

(define (=zero-rational? x) (=zero-rat? x)) `\endlisp`{=latex} of type
$\mbox{RepRat}\rightarrow\mbox{Sch-\-Bool}$.

To install the rational methods in the generic operations table, we
evaluate

\beginlisp

(put 'add '(rational rational) +rational) (put 'sub '(rational rational)
-rational) (put 'mul '(rational rational) \*rational) (put 'div
'(rational rational) /rational)

(put 'negate '(rational) negate-rational) (put '=zero? '(rational)
=zero-rational?) `\endlisp`{=latex}

The Rational Package should also provide a means for a user to create
Generic Rationals, so we include an external procedure of type
$(\mbox{Generic-\-Num},\mbox{Generic-\-Num}) \rightarrow(\{\mbox{\tt rational}\}\times\mbox{RepRat})$,
namely,

\beginlisp

(define (create-rational x y) (make-rational (make-rat x y)))
`\endlisp`{=latex}

#### Exercise 5.2

Produce expressions that define `r5/13` to be the rational number $5/13$
and `r2` to be the rational number $2/1$. Assume that the expression
`\beginlisp`{=latex} (define rsq (square (add r5/13 r2)))
`\endlisp`{=latex} is evaluated. Draw a box and pointer diagram that
represents `rsq`.

#### Exercise 5.3A

Define a predicate `equ-rat?` inside the Rational package that tests
whether two rationals are equal. What is its type?

#### Exercise 5.3B

Install the relevant method in the generic arithmetic package so that
`equ?` tests the equality of two generic rational numbers as well as two
generic ordinary numbers. Test that ` equ?` works properly on both
subtypes of generic numbers.

#### Operations across Different Types

At this point all the methods installed in our system require all
operands to have the subtype---all `\mbox{\tt number}`{=latex}, or all
`\mbox{\tt rational}`{=latex}. There are no methods installed for
operations combining operands with distinct subtypes. For example,

\beginlisp

(define n2 (create-number 2)) (equ? n2 r2) `\endlisp`{=latex} will
return a "no method" error message because there is no equality method
at the subtypes
(`\mbox{\tt number}`{=latex} `\mbox{\tt rational}`{=latex}). We have not
built into the system any connection between the number 2 and the
rational $2/1$.

Some operations across distinct subtypes are straightforward. For
example, to combine a rational with a number, $n$, coerce $n$ into the
rational $n/1$ and combine them as rationals.

#### Exercise 5.4A

Define a procedure
$$\mbox{\tt repnum->reprat}: \mbox{RepNum}\rightarrow\mbox{RepRat}$$
which coerces $n$ into $n/1$.

Now, for any type, $T$, you can obtain a
$$(\mbox{RepNum},\mbox{RepRat}) \rightarrow T$$ method from a
$$(\mbox{RepRat},\mbox{RepRat}) \rightarrow T$$ method by applying the
procedure `\mbox{\tt RRmethod->NRmethod}`{=latex}:

\beginlisp

(define (RRmethod-\>NRmethod method) (lambda (num rat) (method
(repnum-\>reprat num) rat))) `\endlisp`{=latex}

Use `\mbox{\tt RRmethod->NRmethod}`{=latex} to define methods for
generic `\mbox{\tt add}`{=latex}, `\mbox{\tt sub}`{=latex},
`\mbox{\tt mul}`{=latex}, and `\mbox{\tt div}`{=latex} at argument types
(`\mbox{\tt number}`{=latex} `\mbox{\tt rational}`{=latex}). Define
methods for these operations at argument types
(`\mbox{\tt rational}`{=latex} `\mbox{\tt number}`{=latex}). Also define
`\mbox{\tt equ?}`{=latex} these argument types.

#### Exercise 5.4B

Install your new methods. Test them on
`\mbox{\tt (equ?\ n2 r2)}`{=latex} and
$$\mbox{\tt (equ?\ (sub (add n2 r5/13) r5/13) n2)}$$

## Polynomials

The Polynomial package defines methods for handling generic polynomials
which are installed by

\beginlisp

(put 'add '(polynomial polynomial) +polynomial) (put 'mul '(polynomial
polynomial) \*polynomial) (put '=zero? '(polynomial) =zero-polynomial?)
`\endlisp`{=latex}

The package also includes an external procedure so the user can
construct generic polynomials. Namely,
$$\mbox{\tt create-polynomial}: (\mbox{Variable}, \mbox{List}(\mbox{Generic-\-Num})) \rightarrow(\{\mbox{\tt polynomial}\}\times
\mbox{RepPoly})$$ constructs generic polynomials from a variable and the
list of coefficients starting at the high order term (this is the
preferred representation for *dense* polynomials described in Section
2.6.3).

Within the Polynomial package, polynomials are represented by *abstract*
term lists, using the list format preferred for *sparse* polynomials as
described in Section 2.6.3. These abstract term lists are not
necessarily Scheme lists, but have their own constructors and selectors.
(They are, in fact, implemented as ordinary lists in
`\mbox{\tt ps5-code.scm}`{=latex}, but the abstraction makes it easier
to change to a possibly more efficient term list representation without
changing code outside the Term List package.) So we have the type
equations $$\begin{aligned}
\mbox{RepPoly}& = & \mbox{Variable}\times\mbox{RepTerms}\\
\mbox{RepTerms}& = & \mbox{Empty-Term-List}  \cup(\mbox{RepTerm}\times\mbox{RepTerms})\\
\mbox{RepTerm}& = & \mbox{Sch-\-NatNum}\times\mbox{Generic-\-Num}
\end{aligned}$$ with term list constructors $$\begin{aligned}
\mbox{\tt the-empty-termlist} &:& \mbox{Empty-type}\rightarrow\mbox{RepTerms}\\
\mbox{\tt adjoin-term} &:& (\mbox{RepTerm}, \mbox{RepTerms}) \rightarrow\mbox{RepTerms},
\end{aligned}$$ and selectors `\mbox{\tt first-term}`{=latex} and
`\mbox{\tt rest-terms}`{=latex}[^3].

In this problem set, we do modify the definition of
`\mbox{\tt *-term-by-all-terms}`{=latex} given on p. 193 of the test.
The new definition is:

\beginlisp

(define (\*-term-by-all-terms t1 tlist) (map-terms (lambda (term)
(\*term t1 term)) tlist)) `\null`{=latex} (define (\*term t1 t2)
(make-term (+ (order t1) (order t2)) (mul (coeff t1) (coeff t2))))
`\endlisp`{=latex}

#### Exercise 5.5A

What is the type of the procedure `\mbox{\tt map-terms}`{=latex}? Supply
its definition.

#### Exercise 5.5B

Define a procedure `\mbox{\tt create-numerical-polynomial}`{=latex}
which, given a variable name, `\mbox{\tt x}`{=latex}, and list of
`\mbox{Sch-\-Num}`{=latex}, returns a generic polynomial in
`\mbox{\tt x}`{=latex} with the list as its coefficients.

Use `\mbox{\tt create-numerical-polynomial}`{=latex} to define
`\mbox{\tt p1}`{=latex} to be the generic polynomial
$$p_1(x) = x^3 + 5x^2 + -2.$$

#### Exercise 5.5C

Evaluate your definition of `\mbox{\tt map-terms}`{=latex}, thereby
completing the definition of multiplication of generic polynomials. Use
the generic ` square` operator to compute the square of
`\mbox{\tt p1}`{=latex}, and the square of its square. Turn in the the
**pretty-printed** results of the squarings, as computed in lab.

\medskip

There are still few methods installed which work with operands of mixed
types. This means that generic arithmetic on polynomials with generic
coefficients of different types is likely to fail. For example, a
representation of the polynomial $p_{2}(z,x) = p_{1}(x)z^{2} + 3z + 5$
is defined in buffer `\mbox{\tt ps5-ans.scm}`{=latex} as:

\beginlisp

(define p2-mixed (create-polynomial 'z (list p1 (create-number 3)
(create-number 5)))) `\endlisp`{=latex} Now squaring
`\mbox{\tt p2-mixed}`{=latex} will generate a "no method" error message,
because there is no method for multiplying the numerical coefficients 3
and 5 by the polynomial coefficient `\mbox{\tt p1}`{=latex}. A
definition which will work better in our system would be to replace 3
and 5 by the corresponding constant polynomials in
`\mbox{\tt x}`{=latex}:

\beginlisp

(define p2 (create-polynomial 'z (list p1 (create-polynomial 'x (list
(create-number 3)) (create-polynomial 'x (list (create-number 5)))))))
`\endlisp`{=latex}

#### Exercise 5.6A

Use `\mbox{\tt create-rational}`{=latex} and
`\mbox{\tt create-numerical-polynomial}`{=latex} to define the following
rationals whose numerators and denominators are polynomials in
`\mbox{\tt y}`{=latex}: $$3/y,\ (y^{2}+1)/y,\ 1/(y + -1),\ 2$$ Then
define a useful representation for $p_3(x,y)=(3/y)x^4 +
((y^{2}+1)/y)x^2 + (1/(y + -1))x + 2$.

#### Exercise 5.6B

Use the generic `square` operator to compute the square of
`\mbox{\tt p2}`{=latex} and `\mbox{\tt p3}`{=latex}, and the square of
the square of `\mbox{\tt p2}`{=latex}. Turn in the definitions you typed
to create `\mbox{\tt p3}`{=latex} and the **pretty-printed** results of
the squarings, as computed in lab.

## Completing the polynomial package

If you construct a chart of the dispatch table we have been building,
you will see that there are some unfilled slots dealing with
polynomials. Notably, the generic `negate` and `sub` operations do not
know how to handle polynomials. (There is also no method for polynomial
`div`, but this is more problematical since polynomials are not closed
under division, e.g., dividing $x+1$ by $x^2$ yields a *rational
function* $$\frac{x+1}{x^2}$$ which is not equivalent to any
polynomial.)

#### Exercise 5.7A

Use the procedure `\mbox{\tt map-terms}`{=latex} to write a procedure
`\mbox{\tt negate-terms}`{=latex} that negates all the terms of a term
list. Then use `\mbox{\tt negate-terms}`{=latex} to define a procedure
`\mbox{\tt negate-poly}`{=latex}, and a method
`\mbox{\tt negate-polynomial}`{=latex}. Include the types in the
comments accompanying your code.

#### Exercise 5.7B

Using the `negate-poly` procedure you created in exercise 5.7A, and the
procedure `+poly`, implement a polynomial subtraction procedure `-poly`,
and a method `-polynomial`. Use `-poly` and
`\mbox{\tt =zero-poly?}`{=latex} to implement
`\mbox{\tt equ-poly?}`{=latex} and `\mbox{\tt equ-polynomial?}`{=latex}

#### Exercise 5.7C

Install `\mbox{\tt negate-polynomial}`{=latex} in the table as the
generic `negate` method for polynomials. Install `-polynomial` and
`\mbox{\tt equ-polynomial?}`{=latex} as the generic `sub` and
`\mbox{\tt equ?}`{=latex} operations on polynomials. Test your
procedures on the polynomials `\mbox{\tt p1}`{=latex},
`\mbox{\tt p2}`{=latex}, and `\mbox{\tt p3}`{=latex} of exercises 5.5
and 5.6.

## More Operations across Different Types

To combine a polynomial, $p$, with a number, we coerce the number into a
constant polynomial over the variable of $p$, and combine them as
polynomials.

#### Exercise 5.8A

Define a procedure
$\mbox{\tt repnum->reppoly}: (\mbox{Variable}, \mbox{RepNum}) \rightarrow\mbox{RepPoly}$
which coerces $n$ into a constant polynomial $n$ over a given variable.
Define methods for generic `\mbox{\tt add}`{=latex},
`\mbox{\tt sub}`{=latex}, `\mbox{\tt mul}`{=latex} and
`\mbox{\tt equ?}`{=latex} at types
(`\mbox{\tt number}`{=latex} `\mbox{\tt polynomial}`{=latex}) and
(`\mbox{\tt polynomial}`{=latex} `\mbox{\tt number}`{=latex}), and for
generic `\mbox{\tt div}`{=latex} at types
(`\mbox{\tt polynomial}`{=latex} `\mbox{\tt number}`{=latex}).

#### Lab exercise 5.8B

Install your new methods. Test them on
`\mbox{\tt (square p2-mixed)}`{=latex} and
$$\mbox{\tt (equ?\ (sub (add p1 p3) p1) p3)}.$$

#### Exercise 5.8C

To multiply a rational by a number, it was ok to coerce the number $n$
into the rational $n/1$. Give an example illustrating why handling
multiplication of a polynomial and a rational by coercing the polynomial
$p$ into the rational $p/1$ is not always a good thing to do. How about
coercing the rational into a constant polynomial over the variable of
$p$?

## Polynomial Evaluation

Polynomials are generic numbers on the one hand, but on the other hand,
they also describe *functions* which can be applied to generic numbers.
For example, the polynomial $p_1(x) = x^3 + 5x^2 - 2$ evaluates to 26
when $x=2$. Similarly, when $z = x+1$, the polynomial $p_2(z,x)$
evaluates to the polynomial $$x^5 + 7x^4 + 11x^3 + 3x^2 - x + 6.$$

It is easy to define an `\mbox{\tt apply-polynomial}`{=latex} procedure:

\beginlisp

(define (apply-term t gn) (mul (coeff t) (power gn (order t))))
`\endlisp`{=latex}

\beginlisp

(define (power gn k) (if (\< k 1) (create-number 1) (mul gn (power gn
(dec 1))))) `\endlisp`{=latex}

\beginlisp

(define (apply-terms terms gn) \<\*\*blob5.9A\*\*\>) `\endlisp`{=latex}

\beginlisp

(define (apply-polynomial p gn) (apply-terms (term-list (contents p))
gn)) `\endlisp`{=latex}

#### Exercise 5.9A

Fill in `\mbox{\tt <**blob5.9A**>}`{=latex} to complete the definition
of `\mbox{\tt apply-terms}`{=latex}.

#### Exercise 5.9B

Test your definition by applying $p_1$ to 2, $p_2$ to $x+1$, and
verifying

\beginlisp

(define x (create-numerical-polynomial 'x '(1 0))) (equ?
(apply-polynomial p1 x) p1) `\endlisp`{=latex}

[^1]: This will be explained when we come to section 3.3.3 of the Notes.

[^2]: In Exercise 2.76 in the text, the implementation of the type
    tagging system is modified to maintain the illlusion that generic
    ordinary numbers have a `\mbox{\tt number}`{=latex} tag, without
    actually attaching the tag to Scheme numbers. This implementation
    has the advantage that generic ordinary numbers are represented
    exactly by Scheme numbers, so there is no need to provide the
    user-interface procedure `\mbox{\tt create-number}`{=latex}. Note
    that in Section 6 following Exercise 2.76, the text implicitly
    assumes that this revised implementation of tags has been installed.
    In this problem set we stick to the straightforward implementation
    with actual `\mbox{\tt number}`{=latex} tags.

[^3]: The `\mbox{Empty-type}`{=latex} has no elements. The type
    statement
    $$\mbox{\tt make-an-element} : \mbox{Empty-type}\rightarrow T$$
    indicates that the procedure `\mbox{\tt make-an-element}`{=latex}
    take no arguments, and evaluating
    `\mbox{\tt (make-an-element)}`{=latex} returns a value of type $T$.
    Such procedures are sometimes called "thunks." There wasn't any
    special need to use a thunk as constructor for empty term lists---a
    constant equal to the empty term list would have served as well
    (better? `\mbox{\tt :-)}`{=latex})---but it serves as a reminder
    that term lists are created differently than Scheme's lists.
