# Domain Analysis
# Board: Represents a sudoku board. This is a list of 81 cells.
# Cell: Represents a sudoku cell. Its value is 0 if the cell is unfilled, or 1-9 when filled with these numbers.
# Unit: A sudko board has 9 row units, 9 column units, and 9 box units. Each unit is a list of 9 cell positions (ranging from 0 to 91)
# The board positions are as follows:

# 00 01 02 | 03 04 05 | 06 07 08
# 09 10 11 | 12 13 14 | 15 16 17
# 18 19 20 | 21 22 23 | 24 25 26
# ---------+----------+---------
# 27 28 29 | 30 31 32 | 33 34 35
# 36 37 38 | 39 40 41 | 42 43 44
# 45 46 47 | 48 49 50 | 51 52 53
# ---------+----------+---------
# 54 55 56 | 57 58 59 | 60 61 62
# 63 64 65 | 66 67 68 | 69 70 71
# 72 73 74 | 75 76 77 | 78 79 80

ROWUNITS =[ [ 0,  1,  2,  3,  4,  5,  6,  7,  8],
			[ 9, 10, 11, 12, 13, 14, 15, 16, 17],
            [18, 19, 20, 21, 22, 23, 24, 25, 26],
            [27, 28, 29, 30, 31, 32, 33, 34, 35],
            [36, 37, 38, 39, 40, 41, 42, 43, 44],
            [45, 46, 47, 48, 49, 50, 51, 52, 53],
            [54, 55, 56, 57, 58, 59, 60, 61, 62],
            [63, 64, 65, 66, 67, 68, 69, 70, 71],
            [72, 73, 74, 75, 76, 77, 78, 79, 80]]

COLUNITS = [[ 0,  9, 18, 27, 36, 45, 54, 63, 72],
            [ 1, 10, 19, 28, 37, 46, 55, 64, 73],
            [ 2, 11, 20, 29, 38, 47, 56, 65, 74],
            [ 3, 12, 21, 30, 39, 48, 57, 66, 75],
            [ 4, 13, 22, 31, 40, 49, 58, 67, 76],
            [ 5, 14, 23, 32, 41, 50, 59, 68, 77],
            [ 6, 15, 24, 33, 42, 51, 60, 69, 78],
            [ 7, 16, 25, 34, 43, 52, 61, 70, 79],
            [ 8, 17, 26, 35, 44, 53, 62, 71, 80]]

BOXUNIT =  [[ 0,  1,  2,  9, 10, 11, 18, 19, 20],
            [ 3,  4,  5, 12, 13, 14, 21, 22, 23],
            [ 6,  7,  8, 15, 16, 17, 24, 25, 26],
            [27, 28, 29, 36, 37, 38, 45, 46, 47],
            [30, 31, 32, 39, 40, 41, 48, 49, 50],
            [33, 34, 35, 42, 43, 44, 51, 52, 53],
            [54, 55, 56, 63, 64, 65, 72, 73, 74],
            [57, 58, 59, 66, 67, 68, 75, 76, 77],
            [60, 61, 62, 69, 70, 71, 78, 79, 80]]     

# Board -> Boolean | Board
# Solves a sukoku board
def solve_board(b):
	if is_solved(b): return b					# if the board is full, then the board is solved
	else: return solve_boards(next_boards(b))   # otherwise generate children board, and then solve them

# listof Board -> Boolean | Board
# Solves a list of boards
def solve_boards(lob):
	if len(lob) == 0: 
		return False					# if the listof boards is empty then there is no solution
	else:
		first, rest = l[0], l[1:]
		trial = solve_board(first)		# otherwise try to solve the first board
		if first: return trial			# if there is a solution then return that solution
		else: return solve_boards(rest) # otherwise solve the rest of the boards

# Board -> Boolean
# Returns true if the board is solved, ie, if the board is full, and none of its cells are empty
def is_solved(b):
	empty_cells = [c is False for c in b]
	return sum(empty_cells) == 0
	
# Board -> listof Board
# Returns the children board of the given board
def next_boards(b):
  return keep_valid(fill_1_9(find_blank(b)))

# listof Board -> listof Board
# keep valid board according to sudoku rules. No duplicates in any rows, columns, or boxes
def keep_valid(lob):
	return [vb for vb in lob if is_valid(vb)]

# Board -> Boolean
# A valid board is one where there are no duplicates in any rows, columns, or boxes
def is_valid(b):
  (let ((units (append (rows b) (cols b) (boxes b))))
    (andmap valid-unit? units)))

;; listof Values -> Boolean
(define (valid-unit? u)
  (no-duplicates? (remove-blanks u)))

;; listof Values -> listof Values
;; Removes blank (false) cells
(define (remove-blanks lov)
  (if (empty? lov)
      lov
      (if (number? (first lov))
          (cons (first lov) (remove-blanks (rest lov)))
          (remove-blanks (rest lov)))))

;; listof values -> Boolean
;; Returns true if there are no duplicates in the list
(define (no-duplicates? lov)
  (if (false? (check-duplicates lov)) true false))
              
;; listof Position Board -> Value
;; Takes a list of positions and returns the corrosponding values in the board
(define (values-of lop board)
  (map (lambda (p) (list-ref board p)) lop))
