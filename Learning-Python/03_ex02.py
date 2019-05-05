# -*- coding: utf-8 -*-
"""
Machine Learning Online Class - Exercise 2: Logistic Regression
"""
#%% Initialization and loading data
# import os; os.system('clear') - regular python
# cls - ipython

import numpy as np
import matplotlib.pyplot as plt
import scipy.optimize
import functools

import logistic
import importlib; importlib.reload(logistic)

#  The first two columns contains the exam scores and the third column contains the label.
data = np.loadtxt('ex2data1.txt', delimiter=',')
X = data[:, 0:2]
y = data[:, 2]

#%% Plotting
# We start the exercise by first plotting the data to understand the problem we are working with.

print('Plotting data with + indicating (y = 1) examples and o indicating (y = 0) examples.\n')
logistic.plotData(X, y)
plt.xlabel("Score in exam 1")
plt.ylabel("Score in exam 2")
plt.legend()
plt.show()

input('\nProgram paused. Press enter to continue.\n')
#%% Computing Cost and Gradient
# In this part of the exercise, you will implement the cost and gradient for logistic regression.

# Setup the data matrix appropriately, and add ones for the intercept term
m, f = X.shape
X = np.insert(X, 0, 1, axis = 1)

# Initialize fitting parameters
initial_theta = np.zeros(f + 1)

# Compute and display initial cost and gradient
cost = logistic.cost(initial_theta, X, y)
grad = logistic.gradient(initial_theta, X, y)

print('Cost at initial theta (zeros): %f\n' % cost)
print('Expected cost (approx): 0.693\n')
print('Gradient at initial theta (zeros):')
print(grad)
print('Expected gradients (approx):\n -0.1000  -12.0092  -11.2628\n')

# Compute and display cost and gradient with non-zero theta
test_theta = np.array((-24, 0.2, 0.2))
cost = logistic.cost(test_theta, X, y)
grad = logistic.gradient(test_theta, X, y)

print('\nCost at test theta: %f\n' % cost)
print('Expected cost (approx): 0.218\n')
print('Gradient at test theta:')
print(grad)
print('Expected gradients (approx):\n 0.043  2.566  2.647\n')

input('\nProgram paused. Press enter to continue.\n')
#%% Optimizing using a minimization optimizatoin function
# In this exercise, we use a built-in function (minimize) to find the optimal parameters theta.

cost_func = functools.partial(logistic.cost, X=X, y=y)
grad_func = functools.partial(logistic.gradient, X=X, y=y)
res = scipy.optimize.minimize(cost_func, jac=grad_func, x0 = initial_theta, method='TNC')
theta, cost =  res.x, res.fun

print('Cost at theta found by fminunc: %f\n' % cost)
print('Expected cost (approx): 0.203\n')
print('theta: \n')
print(theta)
print('Expected theta (approx):\n')
print(' -25.161  0.206  0.201\n')

input('\nProgram paused. Press enter to continue.\n')
#%% Plotting decision boundary
print('Plotting decision boundary...')
logistic.plotDecisionBoundary(theta, X[:, 1:3], y)
plt.legend()
plt.show()

logistic.plotDecisionBoundary2(theta, X[:, 1:3], y)
plt.legend()
plt.show()

input('\nProgram paused. Press enter to continue.\n')
#%% Predict and Accuracies
# After learning the parameters, you'll like to use it to predict the outcomes on unseen data. 
# In this part, you will use the logistic regression model to predict the probability that a student with 
# score 45 on exam 1 and score 85 on exam 2 will be admitted.
# Furthermore, you will compute the training and test set accuracies of our model.

# Predict probability for a student with score 45 on exam 1 and score 85 on exam 2 

score = np.array((1, 45, 85))
prob = logistic.sigmoid(score.dot(theta))
print('For a student with scores 45 and 85, we predict an admission probability of %f\n' % prob)
print('Expected value: 0.775 +/- 0.002\n\n')

# Compute accuracy on our training set
p = logistic.predict(theta, X)
acc = np.mean((p > 0.5) == y) * 100
print('Train Accuracy: %f\n'% acc)
print('Expected accuracy (approx): 89.0\n')
print('Done....\n')


