def squaro(x):
	return fixed_point(average_damp(lambda y:x/y))

def average_damp(func):
	return lambda z: average(z, func(z))

def fixed_point(func):
	start = 1.0
	while not close_enough(start, func(start)):
		start = func(start)
	return func(start)

def close_enough(x, y):
	tolerance =  0.000001
	return 	x/y > (1-tolerance) and x/y < (1+tolerance)

def average(x, y):
	return (x+y) / 2

print squaro(4.0)
print squaro(100)
print squaro(1234567890)


    