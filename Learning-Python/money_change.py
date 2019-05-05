# Coin Change is the problem of finding the number of ways of making changes 
# for a particular amount of cents, n, using a given set of denominations, d1 ... dm
# It is a general case of Integer Partition, and can be solved with dynamic programming.
# A greey algorithm works for canonical coin system such as the US system

def memoize(f):
    cache = {}
    return lambda *args: cache[args] if args in cache else cache.update({args: f(*args)}) or cache[args]

@memoize
def rec_minchange_n(money: int, denominations = [1, 5, 10, 25]):
    """returns the minimum number of coins from a list of denominations needed to change an integer amount of money"""
    return 0 if money == 0 else min(rec_minchange_n(money - d) + 1 for d in denominations if d <= money)

@memoize
def rec_minchange(money: int, denominations = [1, 5, 10, 25]): 
    """returns the sequence of coins from a list of denominations needed to change an integer amount of money"""
    return [] if money == 0 else min((rec_minchange(money - d) + [d] for d in denominations if d <= money), key = len)

def greedy_minchange_n(money: int, denominations = [1, 5, 10, 25]):
    """returns the number of coins from a list of denominations needed to change an integer amount of money"""
    remainder, change = money, 0
    denominations = sorted(denominations, reverse = True)
    for d in denominations:
        if remainder >= d:
            n = remainder // d
            remainder = remainder % d
            change = change + n
    return change

def greedy_minchange(money: int, denominations = [1, 5, 10, 25]):
    """returns the sequence of coins from a list of denominations needed to change an integer amount of money"""
    remainder, change = money, []
    denominations = sorted(denominations, reverse = True)
    for d in denominations:
        if remainder >= d:
            n = remainder // d
            remainder = remainder % d
            change = change + [d] * n
    return change

def change_greedy(n):
    return "Changing {} cents into {} coins {}".format(n, greedy_minchange_n(n), greedy_minchange(n))

def change_recursive(n):
    return "Changing {} cents into {} coins {}".format(n, rec_minchange_n(n), rec_minchange(n))
        
if __name__ == "__main__": 
    print(change_greedy(23))
    print(change_recursive(23))