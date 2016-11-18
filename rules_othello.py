import copy
# import random



# ----------------- define elements of play --------------

BOARD_SIZE = 8

OPPOSITE_DIRS = ("down", "right", "down_right", "down_left", "up_right", "up_left", "left", "up")

# ---------------- position ------------------------
# REMEMBER! othello positions start from top left, not that it matters internally
# but it helps to keep things consistent
class Pos:
    def __init__(self, horizontal, vertical):
        if not(0 <= horizontal < BOARD_SIZE
                and 0 <= vertical < BOARD_SIZE ):
            raise ValueError
        self.h = horizontal
        self.v = vertical

    def to_index(self):
        return self.v * BOARD_SIZE + self.h

    @staticmethod
    def index_to_pos(idx):
        if idx < 0 or idx >= BOARD_SIZE**2:
            raise ValueError
        return Pos(idx % BOARD_SIZE, int(idx / BOARD_SIZE))

    def to_string(self):
        horizontal = chr(ord('1') + self.h)
        vertical = chr(ord('a') + self.v)
        return vertical + ', ' + horizontal

    def down(self):
        if self.v < BOARD_SIZE-1:
            self.v += 1
            return True
        else:
            return False

    def right(self):
        if self.h < BOARD_SIZE-1:
            self.h += 1
            return True
        else:
            return False

    def up(self):
        if self.v > 0:
            self.v -= 1
            return True
        else:
            return False

    def left(self):
        if self.h > 0:
            self.h -= 1
            return True
        else:
            return False

    def down_right(self):
        if (self.h < BOARD_SIZE-1) and (self.v < BOARD_SIZE-1):
            self.h += 1
            self.v += 1
            return True
        else:
            return False

    def down_left(self):
        if (self.h > 0) and (self.v < BOARD_SIZE-1):
            self.h -= 1
            self.v += 1
            return True
        else:
            return False

    def up_right(self):
        if (self.h < BOARD_SIZE-1) and (self.v > 0):
            self.h += 1
            self.v -= 1
            return True
        else:
            return False

    def up_left(self):
        if (self.h > 0) and (self.v > 0):
            self.h -= 1
            self.v -= 1
            return True
        else:
            return False

# -------------- end position class -----------------

# -------------- board ---------------------------
class Board:
    def __init__(self, prev_borad = []):
        if prev_borad:
            self.l = copy.deepcopy(prev_borad.l)
        else:
            self.l = [0]*64
            self.l[27] = self.l[36] = -1
            self.l[28] = self.l[35] = 1

    def to_string(self):
        the_str = "(\n"
        char_map = {-1: 'B', 0: '0', 1: 'W', 2: 'X'}
        for idx, p in enumerate(self.l):
            if idx % 8 == 0:
                the_str += "    ("
            the_str += char_map[p]
            if idx % 8 == 7:
                the_str += ")" + '\n'
            else:
                the_str += ", "
        the_str += ")" + '\n'
        return the_str

    # piece should be -1 (B), 1 (W) or 2 (possible move)
    def place_piece(self, idx, piece):
        self.l[idx] = piece

    # turn should be -1 (B) or 1 (W)
    def possible_moves(self, turn):
        possible_squares = []
        for idx, square in enumerate(self.l):
            # nifty: the opponent's token
            if square * turn == -1:
                empty_squares = []
                anchor_pos = Pos.index_to_pos(idx)
                for direc in OPPOSITE_DIRS:
                    pos = Pos(anchor_pos.h, anchor_pos.v)
                    getattr(pos, direc)()  # possibly here checking for not having moved is irrelevant
                    empty_squares.append(self.l[pos.to_index()] == 0)
                print(empty_squares)
                for i, empty in enumerate(empty_squares):
                    # empty in one direction but not in the opposite
                    if empty and not empty_squares[-(i+1)]:
                        print(i)
                        pos = Pos.index_to_pos(idx)
                        can_go = True
                        temp_idx = idx
                        while(self.l[temp_idx]*turn == -1 and can_go): # TODO need a condition to stop on edges
                            direc = (OPPOSITE_DIRS[-(i+1)])
                            print(pos.to_string())
                            print(direc)
                            can_go = getattr(pos, direc)()
                            temp_idx = pos.to_index()
                        if self.l[temp_idx]*turn == 1:
                            pos = Pos(anchor_pos.h, anchor_pos.v)
                            getattr(pos, OPPOSITE_DIRS[i])()  # TODO this strted off being our empty square right verify edges..
                            possible_squares.append(pos.to_index())
        return possible_squares


    # TODO create a function that copies, adds to the board and prints possible moves
    # TODO also make sure that the above function won't fall off of the board (use the boolean from the move functions)







# -------------- end board ---------------------------

# ---------------- end rules ----------------------------