def fibs(n):
    x = 0
    y = 1
    print x, y
    while n > 0:
        z = x
        x = y
        y = z + y
        print x, y
        n = n-1


def fibs2(n):
    if n <= 1: 
        return n
    else:
        return (fibs2(n-1) + fibs2(n-2))
    
def fibs3(n):
    l = [0, 1]
    while n > 1:
        n = n - 1
        l.append(l[-1] + l[-2])
    return l