// Learn more about F# at http://fsharp.net
// See the 'F# Tutorial' project for more help.

[<EntryPoint>]
let main argv = 
    let a = int argv.[0]
    let b = int argv.[1]
    let c = Misc.gcd a b
    printfn "%A, %i" argv c
    0 // return an integer exit code
