cycle = 0

def display_progress(var):
	global cycle
	cycle = cycle + 1
	print "Cycle:", cycle, "Guess:", var
	
def good_enough(num, guess):
	return (num /(guess*guess) > 0.99999) and (num /(guess*guess) < 1.00001) 
	#return abs(num - (guess * guess)) < 0.00001
	#return num / (guess * guess) == 1

def improve(num, guess):
	return average(guess, num/guess)

def average(x, y):
	return (x+y) / 2

def squaro(num):
	guess = 1.0
	while not good_enough(num, guess):
		display_progress(guess)
		guess = improve(num, guess)
	return guess

print squaro(4)
print squaro(10)
print squaro(439271342789427894219784279842379042108974213)
