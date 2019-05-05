# Data Structure, building pairs, and lists based on functions. Sami Safadi 2010.

def combine(x, y):
    def select(num):
        if num == 1: return x
        elif num == 2: return y
    return select

def first(pair): 
    return pair(1)
    
def second(pair): 
    return pair(2)

def create_list(start):
    return combine(start, '')

def is_empty(l):
    return l == ''

def append_list(l, e):
    if is_empty(l):
        return create_list(e)
    else:
        return combine(first(l), append_list(second(l), e))

def get_element(l, index):
    if index == 0:
        return first(l)
    else:
        return get_element(second(l), index - 1)

def display_list(l):
    if second(l) == '':
        print first(l)
    else:
        print first(l), ", ",
        display_list(second(l))

def map_list(l, func):
    if is_empty(l):
        return l
    else:
        return combine(func(first(l)), map_list(second(l), func))
    
a = create_list(1)
b = append_list(a, 3)
c = append_list(b, 5)
d = append_list(c, 7)

primes = d
print get_element(primes, 0)
print get_element(primes, 3)
display_list(primes)

square = lambda x : x*x
PrimeSquares = map_list(primes, square)
display_list(PrimeSquares)


