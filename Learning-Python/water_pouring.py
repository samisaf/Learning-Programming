"""
This is a solution to the 'water pouring problem' as presented in unit 4 in the udacity design of computer programs course.

Problem statement:
- We have two glasses of water, a faucet, and a sink. 
- We can have as much water as we want from the faucet, and drain as much as we want into the sink.
- The glasses are of different sizes (for example 4 oz and 9 oz).
- The goal is to measure out a specific amount of water (for example 6 oz), ie, at the end want to have one of the glasses glass filled with exactly 6 oz of water.

Constraints:
Glass level, and capacity are integers, level < capacity. Goal is an integer smaller than the capacities.

Inventory of concepts:
- A glass, (level, capacity), a tuple with two integers representing current level and total capacity.
- A state, (glass1, glass2), a tuple of two glasses
- An action, function(glass(es)) -> glass(es), 3 actions are possible empty a glass, fill a glass, or transfer content from one glass to another
- Successors, function(state) -> set of states that are possible after applying all potential actions to the glasses 
"""

from collections import namedtuple

Glass = namedtuple('Glass', ['level', 'capacity']) # A glass has a current level and a capacity
State = namedtuple('State', ['G1', 'G2']) # A state repreents two glasses

def empty(glass):
    return Glass(0, glass.capacity)

def fill(glass):
    return Glass(glass.capacity, glass.capacity)
    
def transfer(from_glass, to_glass):
    amount = from_glass.level
    space = to_glass.capacity - to_glass.level
    if amount <= space:
        return empty(from_glass), Glass(to_glass.level + amount, to_glass.capacity)
    else:
        return Glass(amount - space, from_glass.capacity), fill(to_glass)

def is_goal(states, goal_level):
    for (glass1, glass2) in states:
        if glass1.level == goal_level or glass2.level == goal_level:
            return True
    return False
    
def successors(state):
    result = {}
    g1, g2 = state[0], state[1]
    result[(empty(g1), g2)] = 'Empty Glass One'
    result[(g1, empty(g2))] = 'Empty Glass Two'
    result[(fill(g1), g2)] = 'Fill Glass One'
    result[(g1, fill(g2))] = 'Fill Glass Two'
    result[(transfer(g1, g2))] = 'Transfer One -> Two'
    result[(transfer(g2, g1)[1], transfer(g2, g1)[0])] = 'Transfer Two -> One' # to maintain the order of glasses
    return result

def search(start_state: State, goal: int):
    if is_goal((start_state,), goal): return {start_state}
    explored = set()
    fringe = [[start_state]] # ordered list of paths that we followed
    while fringe:
        path = fringe.pop()
        last_state = path[-1] # start witht he last state in a path
        for newstate, newaction in successors(last_state).items():
            if not newstate in explored:
                explored.add(newstate)
                newpath = path + [newaction, newstate]
                if is_goal((newstate, ), goal): 
                    return newpath
                else:
                    fringe.append(newpath)
    return []

def solve(capacity1: int, capacity2:int , goal: int):
    assert goal <= capacity1 or goal <=capacity2
    g1 = Glass(0, capacity1)
    g2 = Glass(0, capacity2)
    state0 = (g1, g2)
    return search(state0, goal)

def printsolution(solution: list):
    if solution:
        for i in range(0, len(solution) - 1, 2):
            print("{} \t{} =>".format(solution[i], solution[i+1]))
        print("{} \tDone".format(solution[-1]))
    else: 
        print("No solution is available")

def tests():
    printsolution(solve(4, 9, 6))
    printsolution(solve(4, 8, 6))
    
if __name__ == "__main__": tests()
