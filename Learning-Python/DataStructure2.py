# Data Structure

def make_rat(x, y):
    return (lambda s: return x if s == 1 else return y)
    
def first(rat):
    return rat(1)
    
def second(rat):
    return rat(2)

a = make_rat(5, 6)