def squaro(x):
	tolerance = 0.000001
	average = lambda x, y: (x+y)/2.0
	average_damp = lambda func: lambda z: average(z, func(z))
	close_enough = lambda x, y: x/y > (1-tolerance) and x/y < (1+tolerance)
	
	def fixed_point(func):
		start = 1.0
		while not close_enough(start, func(start)): start = func(start)
		return func(start)
		
	return fixed_point(average_damp(lambda y:x/y))

print squaro(4.0)
print squaro(100)
print squaro(1234567890)