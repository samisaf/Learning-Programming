# -*- coding: utf-8 -*-
"""
@author: Sami Safadi
"""

#%%
import numpy as np
from scipy.optimize import minimize
import regression_test
import matplotlib.pyplot as plt

class LinearReg:
    """
    Linear Regression Class 
    """
    def __init__(self, X, y): 
        assert X.shape[0] == y.shape[0] 
        if X.shape[0] == X.size: 
            X = X.reshape(X.size, 1)
        self.n, self.f = X.shape[0], X.shape[1]  # number of training examples, and number of features
        X =  np.hstack((np.ones((self.n, 1)), X)) # Add a column of ones to x  for the intercept        
        self.X =  np.array(X,  dtype='float64')
        self.y =  np.array(y,  dtype='float64')
        self.cost_history = None
        self.theta_history = None
         
    def fitNormalEq(self):
        term1 = np.linalg.pinv(np.dot(self.X.T, self.X))
        term2 = np.dot(self.X.T, self.y)
        self.theta = np.dot(term1, term2)
        self.intercept_ = self.theta[0]
        self.coef_ = self.theta[1:]
        return self.theta
    
    def fitMinimize(self, theta = None):
        if theta is None: 
            theta = np.zeros(self.f + 1)
        else:
            theta = np.array(theta, dtype='float64')
        res = minimize(self.computeCost, x0 = theta, method='TNC', jac=self.computeDer)
        self.theta = res.x        
        self.intercept_ = self.theta[0]
        self.coef_ = self.theta[1:]
        return self.theta

    def featureNormalize(self):
        # normalizing the columns of a matrix M except for the first one (the intercept column)
        X_norm = np.ones(self.X.shape)
        for c in range(1, self.X.shape[1]):
            mu, sigma = np.mean(self.X[:, c]) , np.std(self.X[:, c])
            X_norm[:, c] = (self.X[:, c] - mu) /  sigma
        self.X = X_norm
        return X_norm
    
    def computeCost(self, theta):
        temp = sum((np.dot(self.X[i, :], theta) - self.y[i]) ** 2 for i in range(self.n)) / (2 * self.n)
        return np.float64(temp)
    
    def computeDer(self, theta):
        temp = np.zeros(self.f + 1)
        for j in range(self.f + 1):
            for i in range(self.n):
                temp[j] = temp[j] + np.dot(self.X[i, :], theta - self.y[i]) * self.X[i, j]
            temp[j] = temp[j] / self.n
        return np.array(temp)
  
    def computePartialDer(self, currentTheta, j):
        temp = sum(np.multiply(np.dot(self.X, currentTheta) - self.y, self.X[:, j])) / self.n
        return np.float64(temp)
    
    def fitGradientDescent(self, initialTheta = None, alpha = 0.01, num_iters = 100, debug = False):
        self.cost_history = np.zeros(num_iters) # additional variable for debugging
        self.theta_history = np.zeros((num_iters, self.f + 1)) # additional variable for debugging
        
        if initialTheta is None: currentTheta = np.zeros(self.f + 1)
        else: currentTheta = np.array(initialTheta, dtype='float64')

        tempTheta = currentTheta
        for i in range(num_iters):
            if debug:
                self.cost_history[i] = self.computeCost(currentTheta)
                self.theta_history[i] = currentTheta
            for j in range(len(tempTheta)):
                tempTheta[j] = currentTheta[j] - alpha * self.computePartialDer(currentTheta, j)
            currentTheta = tempTheta
        self.theta = currentTheta
        
        self.intercept_ = np.float64(self.theta[0])
        self.coef_ = np.array(self.theta[1:])
        return self.theta

    def predicted_y(self):
        return np.dot(self.X, self.theta)
    
    def plotXy(self):
        predicted_y = self.predicted_y()
        for i in range(1, self.f + 1):
            plt.scatter(self.X[:, i], self.y)
            plt.plot(self.X[:, i], predicted_y, 'r.')
            plt.show()

#%%
if __name__ == "__main__":
    regression_test.run()
