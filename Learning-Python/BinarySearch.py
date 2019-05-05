def binary_search(l, v):
    print "I have:", l, "and I am looking for:", v
    if len(l) <= 3:
        return l[0] == v or l[1] == v or l[2] == v
    else:
        midpoint = int(round(len(l) / 2))
        if v < l[midpoint]:
            return binary_search(l[:midpoint], v)
        else:
            return binary_search(l[midpoint:], v)

l1 = [1, 2, 4, 5, 6, 8, 9]
l1.sort()
print binary_search(l1, 3)
print binary_search(l1, 2)

l2 = range(100)
print binary_search(l2, 55)

l3 = list("Sami Safadi")
l3.sort()
print binary_search(l3, 'S')
print binary_search(l3, 'a')