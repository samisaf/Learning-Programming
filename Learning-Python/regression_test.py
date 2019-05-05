import unittest
import matplotlib.pyplot as plt
from sklearn import datasets, linear_model
import numpy as np
import logging, sys
import regression
logging.basicConfig(stream=sys.stderr, level=logging.DEBUG)

class TestStringMethods(unittest.TestCase):
    def test_linear1(self):
        cars = np.matrix("4,2; 4,10; 7,4; 7,22; 8,16; 9,10; 10,18; 10,26; 10,34; 11,17; 11,28; 12,14; 12,20; 12,24; 12,28; 13,26; 13,34; 13,34; 13,46; 14,26; 14,36; 14,60; 14,80; 15,20; 15,26; 15,54; 16,32; 16,40; 17,32; 17,40; 17,50; 18,42; 18,56; 18,76; 18,84; 19,36; 19,46; 19,68; 20,32; 20,48; 20,52; 20,56; 20,64; 22,66; 23,54; 24,70; 24,92; 24,93; 24,120; 25,85")
        cars = np.array(cars)
        speed, dist = cars[:, 0], cars[:, 1]
        lr = regression.LinearReg(speed, dist)
        lr.featureNormalize()
        lr.fitGradientDescent(alpha = 0.01, initialTheta = np.array((1, 1)), num_iters = 1000, debug = True)
        t1 = lr.theta
        lr.fitNormalEq()
        t2 = lr.theta
        plt.plot(lr.cost_history)
        plt.xlabel("Iteration")
        plt.ylabel("Cost of theta")
        plt.show()
        assert(sum(t1-t2) < 0.1)

    def test_linear2(self):
        # importing sample dataset
        boston = datasets.load_boston()
        X = boston.data[:, 6:8] # will fit only age, and distance
        y = boston.target
        # fitting the model with sklearn
        regr = linear_model.LinearRegression()
        regr.fit(X, y)
        print("\nFitting without normalized features")
        print("Fitting via sklearn...")
        print('Expected Coefficients: {} {}'.format(regr.intercept_, regr.coef_)) # The coefficients
        # fitting the model with my class via normal equation
        regs = regression.LinearReg(X, y)
        regs.fitNormalEq()
        print("Fitting via normal equation...")
        print('Calculated Coefficients: {} {}'.format(regs.intercept_, regs.coef_)) # The coefficients
        assert(abs(regr.intercept_ - regs.intercept_) < 0.001)
        assert(abs(sum(regr.coef_ - regs.coef_)) < 0.01)
        
        print("\nFitting with normalized features")
        print("Fitting via normal equation...")
        # fitting the model with my class via normal equation with normalized features
        regs1 = regression.LinearReg(X, y)
        regs1.featureNormalize()
        regs1.fitNormalEq()
        print('Calculated Coefficients: {} {}'.format(regs1.intercept_, regs1.coef_)) # The coefficients
        print('Cost of fit = {} \n'.format(regs1.computeCost(regs1.theta)))

        # fitting the model with my class via manual gradient descent
        print("Fitting via manual gradient descent")
        regs2 = regression.LinearReg(X, y)
        regs2.featureNormalize()
        regs2.fitGradientDescent(alpha = 0.5, num_iters = 200, debug = True)
        #plt.plot(regs2.cost_history)
        print('Calculated Coefficients: {} {}'.format(regs2.intercept_, regs2.coef_)) # The coefficients
        print('Cost of fit = {} \n'.format(regs2.computeCost(regs2.theta)))
        assert(abs(regs2.computeCost(regs2.theta) - regs1.computeCost(regs1.theta)< 10))
        
        # fitting the model with my class via scipy optimize
        print("Fitting via via scipy optimize")
        regs3 = regression.LinearReg(X, y)
        regs3.featureNormalize()
        regs3.fitMinimize()
        print('Calculated Coefficients: {} {}'.format(regs2.intercept_, regs2.coef_)) # The coefficients
        print('Cost of fit = {} \n'.format(regs3.computeCost(regs3.theta)))
        assert(abs(regs3.computeCost(regs3.theta) - regs1.computeCost(regs1.theta)< 10))
        
    def test_linear3(self):
        boston = datasets.load_boston()
        X = boston.data[:, 6:8] # will fit only age, and distance
        y = boston.target
        # fitting the model with my class via normal equation
        regs = regression.LinearReg(X, y)
        regs.fitNormalEq()
        print("Fitting via normal equation...")
        print('Calculated Coefficients: {} {}'.format(regs.intercept_, regs.coef_)) # The coefficients

        # plotting fitting line
        print("\nPlotting fitting line...")
        regs.plotXy()
        
    def test_linear4(self):
        boston = datasets.load_boston()
        X = boston.data[:, 5].reshape(506, 1) # will fit only age, and distance
        _ = np.stack((X[:, 0], X[:, 0]**2)).T
        X = _
        y = boston.target
        # fitting the model with my class via normal equation
        regs = regression.LinearReg(X, y)
        regs.fitNormalEq()
        print("Fitting via normal equation...")
        print('Calculated Coefficients: {} {}'.format(regs.intercept_, regs.coef_)) # The coefficients

        # plotting fitting line
        print("\nPlotting fitting line...")
        regs.plotXy()

def run():
    unittest.main()

if __name__ == '__main__':
    run()    



