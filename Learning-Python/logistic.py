import numpy as np
from numpy import exp, log
import matplotlib.pyplot as plt
import scipy.optimize

def sigmoid(z):
    # computes the sigmoid of z
    return 1.0 / (1.0 + exp(-z))

def hypothesis(X, theta):
    # hypothesis function for logistic regression
    return sigmoid(X.dot(theta))

def featureNormalize(M):
    # normalizing the columns of a matrix M except for the first one (the intercept column)
    M_norm = np.ones(M.shape)
    for c in range(1, M.shape[1]):
        mu, sigma = np.mean(M[:, c]) , np.std(M[:, c])
        M_norm[:, c] = (M[:, c] - mu) /  sigma
    return M_norm

def gradient(theta, X, y, l = 0):
    # returns the gradient of the cost of (X, y) at theta
    m = len(y) # number of training examples
    HthetaX = hypothesis(X, theta)

    initial_grad = (HthetaX - y).dot(X) / m
    penalty_grad = (l / m) * theta
    penalty_grad[0] = 0 # don't penalize intercept
    GRAD = initial_grad + penalty_grad
    return GRAD

def cost(theta, X, y, l = 0):
    # returns the cost of theta at (X, y)
    m = len(y) # number of training examples
    HthetaX = hypothesis(X, theta)

    initial_cost = sum( (-y * log(HthetaX)) -  ((1 - y) * log(1 - HthetaX))) / m
    penalty_cost = sum(l * theta[1:]**2) / (2 * m)
    J = initial_cost + penalty_cost
    return J

def fit(X, y, theta = None, l = 0):
    m, f = X.shape
    assert len(y) == m
    if theta is None: theta = np.zeros(f + 1)
    else: theta = np.array(theta, dtype='float64')
    res = scipy.optimize.minimize(cost, x0 = theta, jac=gradient, method='TNC')
    return res

def one_vs_all(vector):
    """convert a 1 dimentional vector into n colmun binary (1/0) matrix where n is the number of unique elements in vector
    >>> one_vs_all([1, 1, 2, 1])
    array([[ 1.,  0.], [ 1.,  0.], [ 0.,  1.], [ 1.,  0.]])
    """
    elements = np.unique(vector)
    result = np.zeros((len(vector), len(elements)))
    # create column for each unique element in vector
    # fill the column with either 0 or 1
    for c in range(len(elements)): result[:, c] = (vector == elements[c])
    return result

def predict(theta, X):
    return sigmoid(X.dot(theta))

def plotDecisionBoundary(theta, X, y):
    # plot data points
    plotData(X, y)    
    # plotting decision line
    N = 10000
    xrand = np.random.uniform(X.min(), X.max(), N)
    yrand = np.random.uniform(X.min(), X.max(), N)
    ones = np.ones(N)
    xs = np.array((ones, xrand, yrand))
    xs = np.transpose(xs)
    pred = predict(theta, xs)
    keep = xs[(pred > 0.45) & (pred < 0.55)]
    plt.plot(keep[:, 1], keep[:, 2], 'k,')
    
def plotDecisionBoundary2(theta, X, y):
    # plot data points
    plotData(X, y)    
    # plotting decision line
    N = 100
    xr = np.linspace(X.min(), X.max(), N)
    yr = np.linspace(X.min(), X.max(), N)
    zs = np.zeros((len(xr), len(yr)))
    for i in range(len(xr)):
        for j in range(len(yr)):
            temp = sigmoid(np.array((1, xr[i], yr[j])).dot(theta))
            temp = temp > 0.5
            zs[i, j] = temp
    plt.contour(xr, yr, zs, cmap = 'gray')
    
def plotData(X, y):
    G1 = X[y == 0]
    G2 = X[y == 1]
    plt.figure()
    plt.plot(G1[:, 0], G1[:, 1], 'ro', label='y = 0')
    plt.plot(G2[:, 0], G2[:, 1], 'gx', label='y = 1')
