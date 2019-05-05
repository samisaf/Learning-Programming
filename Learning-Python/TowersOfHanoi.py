cycle = 0

def move_towers(num, fro, to, spare):
    global cycle
    cycle = cycle + 1
    if num != 0:
        move_towers(num - 1, fro, spare, to)
        print "Move tower:", num, "from:", fro, "to:", to
        move_towers(num - 1, spare, to, fro)

move_towers(4, 'A', 'B', 'C')
print cycle
