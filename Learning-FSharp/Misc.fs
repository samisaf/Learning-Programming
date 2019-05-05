module Misc

/// Return the (n)th element in list
let rec circularNth (l: 'a list) n = 
    if (n > l.Length) 
        then circularNth l (n - l.Length) 
        else (if n = 1 then l.Head else circularNth (l.Tail) (n-1))

/// Remove the (n)th element in list
let rec circularRemoveNth (l: 'a list) n = 
    let rec iter (l: 'a list) n (acc: 'a list) = if n = 1 then acc@l.Tail else iter (l.Tail) (n-1) (acc@[l.Head])
    if (n > l.Length) then circularRemoveNth l (n - l.Length) else iter l n []

/// Euclid algorithm for greatest common divisor
let rec gcd a b = 
    if b = 0 then a else gcd b (a % b)

(*  There are people standing in a circle waiting to be executed. 
    The counting out begins at some point in the circle and proceeds around the circle in a fixed direction. 
    In each step, a certain number of people are skipped and the next person is executed. 
    The elimination proceeds around the circle until only the last person remains, who is given freedom.
*)
/// Josephus problem for (N) people starting with the (n)th person
let josephus N n = 
    let rec helper m (l: 'a list) = 
        printf "%A\n" l
        if l.Length = 1 then l.Head else helper m (circularRemoveNth l m)
    helper n [1..N]
