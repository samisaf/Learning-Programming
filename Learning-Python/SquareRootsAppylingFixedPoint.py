cycle = 0

def close_enough(x, y):
	return 	x/y > 0.99999 and x/y < 1.00001
	#return abs(x - y) < 0.00001
	#return x/y==1

def display_progress(var):
	global cycle
	cycle = cycle + 1
	print "Cycle:", cycle, "Guess:", var

def find_fixed_point(func, var):
	display_progress(var)
	if close_enough(var, func(var)):
		return var
	else:
		return find_fixed_point(func, average(var, func(var)))
		
def average(x, y):
	return (x+y) / 2

def squaro(x):
	guess = 1.0
	return find_fixed_point(lambda y: (x/y), guess)


print squaro(4.0)
print squaro(100)
print squaro(1234567890)


    