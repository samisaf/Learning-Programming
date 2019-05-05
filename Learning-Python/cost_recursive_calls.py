# Goal: 
# - illustrate the cost of recursive algorithms in terms of tne number of calls that are made.
# - show how this is improved with memoization

VERBOSE = True

def memoize(func):
    """decortator function that memoizes a function"""
    table = {}
    def _f(*args):
        if args in table.keys():
            return table[args]
        else:
            result = table[args] = func(*args)
            return result
    return _f

def fibs(n: int, c = 0):
    """returns the (n)th fibonacci number"""
    if VERBOSE: print("{} {}({})".format('.'*c, fibs.__name__, n))
    if n < 2: return n
    else: return fibs(n - 1, c+1) + fibs(n - 2, c+1)

def minchange(money: int, c = 0):
    """returns the minimum number of coins [1, 5, 10, 25] needed to change an integer amount of money"""
    if VERBOSE: print("{} {}({})".format('.'*c, minchange.__name__, money))
    if money <= 1: return money
    elif money < 5: return (minchange(money - 1, c+1) + 1)
    elif money < 10: return min((minchange(money - 1, c+1) + 1), (minchange(money - 5, c+1) + 1))
    elif money < 25: return min((minchange(money - 1, c+1) + 1), (minchange(money - 5, c+1) + 1), (minchange(money - 10, c+1) + 1))
    else: return min((minchange(money - 1, c+1) + 1), (minchange(money - 5, c+1) + 1), (minchange(money - 10, c+1) + 1), (minchange(money - 25, c+1) + 1))
    
def tests():
    print("\nRunning fibs(5)")
    print("Result => ", fibs(5))
    print("\nRunning minchange(7)")
    print("Result => ",minchange(7))

if __name__ == "__main__": tests()