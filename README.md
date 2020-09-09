# Shadow-Bounce
A Java game built via bagel library

Shadow Bounce is an arcade game where the player must attempt to clear the game board of red
pegs with a limited number of shots. Once the board is cleared, the player can progress to the next
board, and so on until all boards are cleared or the player runs out of shots, whereupon the game
should end. A turn begins when the board is first loaded, and ends when all balls from the turn
have fallen off the bottom of the screen. When the turn ends, a new one begins.
The player begins with 20 shots. Each turn after the first, the number of shots should decrease by 1. When there are no more shots left, the game should end.
The game can be divided into three main types of objects: pegs, which can be destroyed to advance
to the next level; balls, which are used to destroy pegs; and a powerup, which can be activated by
striking it with the ball to get a bonus.
