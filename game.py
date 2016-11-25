
# bunch of imports

## game class?
# turn
# timer = [0,0]
# board_list (will keep moves stored)
# eval_list

# start - set turn, count time, initialize board and board_list, eval_list, no_move = 0

## Are we playing internally or with another agent? hb - heuristic for black, hw - ... if not playing_self h-other = []
# play_match(playing_self = true, hb, hw)
# --------- decide who's playing who ---------
# if not playing_self
#   if hb
#       me = 1
#   else if hw
#       me = -1
#   else
#       raise WhoKnowsWhatException...
# ----------- now play --------------
# loop - while (no_move < 2)
#   stop_timer(turn)
#   turn = -turn
#   start_timer(turn)
#   position.print_board_possible_moves
#   if playing_self
#       play_turn()
#   else
#       if turn == me
#           play_turn()
#       else
#           # - wait on input

# play_turn()
#   [board, eval] = minimax(board, depth, alpha, beta, turn) # (alternatively negamax...)
#           if board
#               board_list.append(board)
#               eval_list.append(eval)
#          else
#               no_move += 1