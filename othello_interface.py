import othello as o
import position as p
import board as b
import othello_node as n

PLY = 1

game = o.game()


def batch():
    tree = create_tree()
    return tree


def apply_action(values, root):
    idx = values.index(min(values))
    move = root.children[idx].move
    game.play_move(move, root.turn)


def create_tree():
    root = n.Node(game.board, game.turn)
    create_sub_tree(root)
    return root


def create_sub_tree(root):
    if root.depth >= PLY:
        return
    moves = root.board.get_valid_moves(game.turn)
    for move in moves:
        new_node = n.Node.child_node(root, move)
        root.insert_child(new_node)
        create_sub_tree(new_node)
