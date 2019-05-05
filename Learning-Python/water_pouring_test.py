import unittest
from water_pouring import *

class TestGlasses(unittest.TestCase):
    def test_glass(self):
        g1 = Glass(0, 5)
        g2 = Glass(2, 7)
        g3 = Glass(9, 10)
        assert empty(g1) == g1
        assert fill(g1) == Glass(5, 5)
        assert transfer(g2, g1) == (Glass(0, 7), Glass(2, 5))
        assert transfer(g2, g3) == (Glass(1, 7), Glass(10, 10))
        assert transfer(g3, g2) == (Glass(4, 10), Glass(7, 7))

    def test_states(self):
        g1 = Glass(1, 5)
        g2 = Glass(2, 6)
        assert successors((g1, g2)).keys() == {((0, 5), (2, 6)), ((5, 5), (2, 6)), ((1, 5), (0, 6)), ((1, 5), (6, 6)), ((0, 5), (3, 6)), ((3, 5), (0, 6))}

    def test_solve(self):
        assert solve(2, 4, 3) == []
        assert solve(4, 7, 3)

def runtests():
    return unittest.main()

if __name__ == '__main__': runtests()
