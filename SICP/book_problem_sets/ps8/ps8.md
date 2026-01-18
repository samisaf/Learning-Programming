\input

../6001mac

\psetheader{Sample Problem Set}{Evaluators}

::: center
\large

**Scheme Evaluators**
:::

\bigskip

You will be working with evaluators as described in Chapter 4 of the
notes. If you don't have a good understanding of how the evaluators are
structured, it is very easy to become confused between the programs that
the evaluator is interpreting, the procedures that implement the
evaluator itself, and Scheme procedures called by the evaluator. You
will need to have carefully studied Chapter 4 through subsection 4.2.2
in order to do this assignment.

To help you focus on some of the important ideas in the Notes, we ask
you to prepare:

# Using the Evaluators

The code for this problem set includes these files:

-   `meval.scm` is the metacircular evaluator described in section 4.1.1
    of the notes. It has been extended to allow declarations of lazy
    procedure parameters, as described in section 4.2 and exercise 4.25.
    In order to avoid confusing the `eval` and `apply` of this evaluator
    with the `eval` and `apply` of the underlying Scheme system, we have
    renamed these procedures `meval` and `m-apply`.

-   `analyze.scm` is the `analyze` evaluator of section 4.1.6, extended
    to allow declarations of lazy procedure parameters, as described in
    section 4.2 and exercise 4.25.

-   `evdata.scm` contains the procedures that define the evaluator's
    data structures, as in section 4.1.3. In order to interface the
    evaluator to the underlying Scheme system, we have made the
    modification outlined in exercise 4.14. Namely, any variable that is
    not found in the evaluator's global environment will be looked up in
    the underlying Scheme; Scheme procedures found in this way will
    treated by the evaluator as primitive procedures. Programs being
    executed by the metacircular evaluator can therefore make use of any
    procedure (primitive or compound) which may be defined in your
    Scheme environment.

-   `syntax.scm` contains the procedures that define the syntax of
    expressions, as described in section 4.1.2.

You'll be switching back and forth between both of these evaluators. The
two evaluators use the same implementation of environment data
structures. This arrangement requires that you reinitialize the
evaluator's environment when you switch from one to the other. Here's
how to manage this:

-   To start up, load the files `syntax.scm`, `evdata.scm`, `meval.scm`,
    and `analyze.scm`. Then evaluate `\mbox{\tt (start-meval)}`{=latex}
    in Scheme; this starts the read-eval-print loop for the
    meta-circular evaluator with a freshly initialized global
    environment.

    To evaluate an expression, you may type the expression into the
    `\mbox{\tt *scheme*}`{=latex} buffer followed by `ctrl-x ctrl-e`.
    (Don't use `M-z` to evaluate the expression in the
    `\mbox{\tt *scheme*}`{=latex} buffer---the presence of the evaluator
    prompts confuses the `M-z` mechanism. But `M-z` works fine for
    sending expressions to the `\mbox{\tt *scheme*}`{=latex} buffer from
    other Scheme-mode buffers.)

    We've arranged that each read-eval-print loop identifies itself by
    its input and output prompts. For example,

    \beginlisp

    MEVAL=\> (+ 3 4) ;;M-value: 7 `\endlisp`{=latex} shows an
    interaction with the `meval` evaluator.

-   You should keep in a separate file any procedure definitions you
    want to install in an evaluator. If your Edwin Scheme buffer is
    running the read-eval-print loop of an evaluator, you can then visit
    this definitions file and type `M-o` to enter the definitions into
    the evaluator. To get you started, we have included an alternative
    implementation of lazy lists in the buffer `ps8defs.scm`.

-   You can interrupt an evaluator by typing `ctrl-c ctrl-c`. To restart
    it with the recent definitions still intact, evaluate
    ` (eval-loop)`.

-   To start a read-eval-print loop for the analyzing evaluator in a
    fresh global environment, return to Scheme and evaluate
    ` (start-analyze)`.

-   The evaluators you are working with do not include error systems. If
    you hit an error you will bounce back into ordinary Scheme. You can
    restart the current evaluator, with its global environment still
    intact, by evaluating `(eval-loop)`.

-   The variable `current-evaluator` is defined as an alias for the
    current evaluator in each of the files `meval.scm` and
    ` analyze.scm`. This should ensure that `current-evaluator` will be
    bound to the most recently started evaluator at all times during
    your lab session.

-   It can be instructive to trace `current-evaluator`, ` m-apply`,
    and/or `exapply` during evaluator executions. (You will also
    probably need to do this while debugging your code for this
    assignment.)

-   Since environments are generally complex, circular list structures,
    we have set Scheme's printer so that it will not go into an infinite
    loop when asked to print a circular list. This was done by
    `\beginlisp`{=latex} (set! \*unparser-list-depth-limit\* 7) (set!
    \*unparser-list-breadth-limit\* 10) `\endlisp`{=latex} at the end of
    the file `evdata.scm`. You may want to alter the values of these
    limits to vary how much list structure will be printed as output.

# Exercises

#### Lab exercise 1A:

Start the `\mbox{\tt meval}`{=latex} evaluator and evaluate a few simple
expressions and definitions. It's a good idea to make an intentional
error and practice restarting the read-eval-print loop. Notice that we
have already installed `let` in the evaluator, so you can use this in
your programs.

#### Lab exercise 1B:

Now start the analyzing evaluator. You can tell that you are typing at
the analyzing evaluator because the prompt will be `AEVAL=>` rather than
`MEVAL=>`. Now once again evaluate a few simple expressions and
definitions.

#### Lab exercise 1C:

Start the meta-circular evaluator again. Now trace evaluation of the
application of some simple procedure to some argument.

\medskip

Ben Bitdiddle thinks it is silly to reinitialize the environment every
time he switches from one evaluator to the other. So, after having run
the meta-circular evaluator, he revises the
`\mbox{\tt start-analyze}`{=latex} procedure so it does not reinitialize
the global environment:

\beginlisp

(define (start-analyze) (set! current-evaluator (lambda (exp env)
((analyze exp) env))) (set! current-prompt \"AEVAL=\> \") (set!
current-value-label \";;A-value: \") ; (init-env) ;Ben comments-out this
line (eval-loop)) `\endlisp`{=latex}

He reasons that now, if he types `\mbox{\tt (start-analyze)}`{=latex},
the definitions he already evaluated in the meta-circular evaluator will
be available to the analyzing evaluator. Eva Lu Ator agrees that the
definitions of the meta-circular evaluator will indeed be available to
the analyzing evaluator, but that this will usually crash the system
rather then being helpful.

#### Lab exercise 2A:

Try Ben's suggestion and observe what Eva Lu Ator meant.

#### Post-Lab exercise 2B:

Briefly, but clearly, explain what goes wrong with Ben's suggestion. Are
there any definitions it would be safe to preserve when switching
between evaluators?

## Comparing evaluation speeds

This next problem asks you to demonstrate the improved efficiency of the
analyzing evaluator over the meta-circular one.

To time things, you can use the procedure `show-time`, which you used in
Problem Set 2. Thus, for example, you can find out how long it takes the
evaluator to evaluate `(fib 10)` by quitting out of the evaluator and
evaluating

\beginlisp

(show-time (lambda() (current-evaluator '(fib 10)
the-global-environment))) `\endlisp`{=latex} in Scheme. Be careful to
define the procedure you are timing, e.g., ` fib`, in the evaluator, not
in ordinary Scheme! Otherwise, you'll end up timing the underlying
Scheme interpreter. This is because of the way we've linked the
evaluator into Scheme: if you define `fib` in Scheme, and not in the
evaluator's global environment, `lookup-variable-value` will find the
Scheme procedure `fib` and `m-apply` will treat this as a primitive.

#### Lab exercise 3A:

Design and carry out an experiment to compare the speeds of the `meval`
and `analyze` evaluators on some procedures. Use tests that run for a
reasonable amount of time (say 10 or 20 seconds). It may be helpful to
use test procedures for which small changes in the input cause large
changes in running time, so you can rapidly increase the time by
increasing the input.

Summarize what you observe about the relative speeds of the two
evaluators on your test programs.

#### Lab exercise 3B (Optional---Extra Credit):

See if you can find an example where the interpreters run for at least
five seconds and the `analyze` evaluator runs *no more* than 1.5 times
the speed of `meval`.

## Adding Language Constructs

#### Pre-Lab exercise 4A:

Lazy parameter declarations are already supported by both the
`\mbox{\tt meval}`{=latex} and `\mbox{\tt analyze}`{=latex} evaluators
supplied to you. Suppose we are given some *fixed* $n \geq 0$. With lazy
parameters, it is possible to define within an evaluator an $n$-argument
*procedure*, `and-proc`, that yields the same results as the Scheme
special form `\mbox{\tt and}`{=latex} when it is used with $n$ subforms.
That is, `\mbox{\tt (and $E_1$ \ldots $E_n$)}`{=latex} in Scheme, and
`\mbox{\tt (and-proc $E_1$ \ldots $E_n$)}`{=latex} in an evaluator,
should produce exactly the same results (including side-effects) for any
expressions $E_1 \ldots
E_n$. Outline how to define the $n$-argument
`\mbox{\tt and-proc}`{=latex} within our evaluators.

#### Lab exercise 4B:

Evaluate the definition of a three-argument `and-proc` in both the
`\mbox{\tt meval}`{=latex} and `\mbox{\tt analyze}`{=latex} evaluators.

#### Lab exercise 5:

Since our evaluators do not yet have the ability to define procedures
with varying numbers of arguments, we can't add the general `and`
construct with the approach of Exercise 4. So instead, add `and` as a
special form to one of the evaluators (you may choose either one, but
see exercise 7A below before you make the choice).

\medskip

We can also extend the evaluators to handle Scheme's convention for
defining procedures with varying numbers of arguments. For example, an
abstraction of the form `\mbox{\tt (lambda (x y . z) \ldots)}`{=latex}
defines a procedure of two or more arguments, in which the value of the
first argument would be bound to `\mbox{\tt x}`{=latex}, the value of
the second to `\mbox{\tt y}`{=latex}, and a list consisting of the
values of any further arguments would be bound to
`\mbox{\tt z}`{=latex}. If, instead of `\mbox{\tt z}`{=latex}, we had
`\mbox{\tt (w lazy)}`{=latex}, then a list consisting of the *delayed*
values of any further arguments would be bound to
`\mbox{\tt w}`{=latex}. Similarly, if `\mbox{\tt (w
lazy-memo)}`{=latex} appears instead of z, this indicates that a list
consisting of the memoized delayed values of any further arguments would
be bound to `\mbox{\tt w}`{=latex}.

We accomplish the extension by modifying the procedure
`\mbox{\tt process-arg-procs}`{=latex} in the file `evdata.scm`[^1]. As
written, `\mbox{\tt (process-arg-procs params aprocs env)}`{=latex}
expects `\mbox{\tt params}`{=latex} and `\mbox{\tt aprocs}`{=latex} to
be *lists* of equal length, and it returns a list of parameter values of
the same length. If `\mbox{\tt params}`{=latex} is not a list of
parameter declarations, but instead ends with a dotted pair of
parameters, then we want `\mbox{\tt process-arg-procs}`{=latex} to
return a list of values in which the last value is itself a list of all
the \"extra\" values, possibly all delayed or all memoized depending on
whether the final parameter is declared to be lazy or memoized. For
example,

\beginlisp

((lambda (x y . (w lazy)) (list x y (car w))) 1 2 (if #t 3 4) (print
\"this should not print\")) `\endlisp`{=latex} will return
`\mbox{\tt (1 2 3)}`{=latex}, without printing anything. This is because
in the application, the variable `\mbox{\tt x}`{=latex} will be bound to
1, variable `\mbox{\tt y}`{=latex} will be bound to 2, and variable
`\mbox{\tt w}`{=latex} will be bound to the list consisting of the
delayed values of `\beginlisp`{=latex}(if #t 3 4)`\endlisp`{=latex} and
`\beginlisp`{=latex}(print \"this should not print\").`\endlisp`{=latex}
The first delayed value gets forced when the primitive procedure
`\mbox{\tt list}`{=latex} is applied to it. The second delayed value is
not ever forced, so no printing occurs.

So we revise `\mbox{\tt process-arg-procs}`{=latex} to reformat its
arguments if necessary, after which it proceeds as before:

\beginlisp

(define (process-arg-procs params aprocs env) (let ((params (undot
params)) (aprocs (matchup-args params aprocs))) (map (lambda (param
aproc) (cond ((variable? param) (aproc env)) ((lazy? param) (delay-it
aproc env)) ((memo? param) (delay-it-memo aproc env)) (else (error
\"Unknown declaration\" param)))) params aprocs))) `\endlisp`{=latex}

The procedure `\mbox{\tt matchup-args}`{=latex} has the task of taking
the list of arguments and returning the list with all the \"extra\"
arguments put into a single list at the end. But at this point each
argument in the input list is actually packaged as an \"argument
procedure.\" The argument procedure will yield the argument value when
it is applied to the environment. So `\mbox{\tt matchup-args}`{=latex}
actually has to create a similar package which yields the list of extra
argument values when it is applied to the environment.

\beginlisp

(define (matchup-args params aprocs) (cond ((null? params) (if (null?
aprocs) '() (error \"matchup-args: too many args\" aprocs))) ((variable?
params) (list (lambda (env) (map (lambda (aproc) (aproc env)) aprocs))))
((lazy? params) (list (lambda (env) (map (lambda (aproc) (delay-it aproc
env)) aprocs)))) ((memo? params) (list (lambda (env) (map (lambda
(aproc) (delay-it-memo aproc env)) aprocs)))) ((null? aprocs) (error
\"matchup-args: too few args\" params)) (else (cons (car aprocs)
(matchup-args (cdr params) (cdr aprocs)))))) `\endlisp`{=latex}

\medskip
\beginlisp

(define (undot params) \<blob6a\>) `\endlisp`{=latex}

#### Lab exercise 6A:

Complete the definition of `\mbox{\tt <blob6a>}`{=latex}, insert these
definitions in the file `evdata.scm` and reload the file into Scheme.
You will find a copy of the above definitions in buffer
`\mbox{\tt ps8-ans.scm}`{=latex}.

#### Lab exercise 6B:

Now we can add, as a procedure, a genuine `and` construct to the
evaluators. Namely,

\beginlisp

(define and (lambda (l lazy) (and-lproc l))) `\endlisp`{=latex}

where `and-lproc` is a procedure that takes as its argument a single
list[^2] of delayed values. Give the definition of ` and-lproc`.

\medskip

Even with lazy and varying numbers of parameters, not all language
facilities can be defined as Scheme procedures. An example is the
special form `(for-list <var> <l> <body>)` where
`\mbox{\tt <var>}`{=latex} is a variable and the value of `<l>` is a
list. Evaluation of this expression causes `\mbox{\tt <body>}`{=latex}
to be repeatedly evaluated as ` <var>` is successively assigned to be
the elements of the value of `<l>`. More precisely,
`(for-list <var> <l> <body>)` is syntactic sugar for[^3]

\beginlisp

(let (rest-l \<l\>)) (define (iter) (if (null? rest-l)
the-unspecified-value (begin (set! \<var\> (car rest-l)) \<body\> (set!
rest-l (cdr rest-l)) (iter)))) (iter)) `\endlisp`{=latex}

#### Lab exercise 7A:

Add the `for-list` construct as a special form (*not* as syntactic sugar
like `let` or `cond`) to whichever evaluator you did *not* choose in
exercise 4.

#### Lab exercise 7B:

Exhibit a `\mbox{\tt <body>}`{=latex} for which
`(for-list x ’(1 2) <body>)` does not have the same behavior as
`(map (lambda (x) >body>) ’(1 2))`.

#### Lab and Post-Lab exercise 8 (Optional):

The evaluators are not quite powerful enough to evaluate themselves.
That is, it doesn't quite work to evaluate all the
`\mbox{\tt meval}`{=latex} definition files within the
`\mbox{\tt MEVAL}`{=latex} read-eval-print loop, and then to apply
`\mbox{\tt meval}`{=latex} as a procedure defined *within* the
`\mbox{\tt MEVAL}`{=latex} evaluator. You may want to try this to see
what happens.

Discuss what further features or definitions would be required in order
to successfully run the evaluators within themselves and/or within each
other.

[^1]: The procedure `\mbox{\tt parameter-names}`{=latex} in `syntax.scm`
    must also be written to handle the dotted-pair form of parameters,
    but we have done this already.

[^2]: Both `\mbox{\tt lazy}`{=latex} and `\mbox{\tt lazy-memo}`{=latex}
    are now specified to be keywords. This prevents misreading
    `\mbox{\tt (lambda (l lazy) \ldots)}`{=latex} as a procedure with
    two formals, `\mbox{\tt l}`{=latex} and `\mbox{\tt lazy}`{=latex}.
    Rather, it is a procedure which may be applied to zero or more
    arguments---all of which will be delayed.

[^3]: We're assuming that the local variables `\mbox{\tt iter}`{=latex}
    and `\mbox{\tt rest-l}`{=latex} are not `\mbox{\tt eq?}`{=latex} to
    `\mbox{\tt <var>}`{=latex} or any variable free in
    `\mbox{\tt <body>}`{=latex}.
