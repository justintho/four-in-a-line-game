# Four In A Line Game

Overview: This project is a Four In A Line Game where the goal is to make a horizontal or vertical line of four in a row before the opponent. In this project, I developed a "smart" AI by implementing the Minimax algorithm to allow the AI to look several steps ahead and choose the best possible to make. The Minimax algorithm uses a heuristic function which computes a numerical score for the state of the board after making a certain move. Alpha-beta pruning was also implemented in order to allow the CPU to make decisions faster by removing the need to continue searching down paths that are guaranteed to not be the best move. 

<h2>Sample Output</h2>

```
CS4200 Four-In-A-Line Game:
The goal of the game is to make a column or row of four and
whoever accomplishes this first is the winner. The player's
moves are represented by 'O', while the computer is represented
by 'X'.

Please choose whether you wish to go first or second:
[1] First
[2] Second
Input: 1

Current Board:
1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - - - - -
E - - - - - - - -
F - - - - - - - -
G - - - - - - - -
H - - - - - - - -
Please enter your move: e5

You chose position E5!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - - - - -
E - - - - O - - -
F - - - - - - - -
G - - - - - - - -
H - - - - - - - -
Computer is making its move...

The computer chose position D5!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - X - - -
E - - - - O - - -
F - - - - - - - -
G - - - - - - - -
H - - - - - - - -
Please enter your move: e4

You chose position E4!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - X - - -
E - - - O O - - -
F - - - - - - - -
G - - - - - - - -
H - - - - - - - -
Computer is making its move...

The computer chose position E3!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - X - - -
E - - X O O - - -
F - - - - - - - -
G - - - - - - - -
H - - - - - - - -
Please enter your move: f4

You chose position F4!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - - X - - -
E - - X O O - - -
F - - - O - - - -
G - - - - - - - -
H - - - - - - - -
Computer is making its move...

The computer chose position D4!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X - - -
E - - X O O - - -
F - - - O - - - -
G - - - - - - - -
H - - - - - - - -
Please enter your move: e6

You chose position E6!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X - - -
E - - X O O O - -
F - - - O - - - -
G - - - - - - - -
H - - - - - - - -
Computer is making its move...

The computer chose position E7!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X - - -
E - - X O O O X -
F - - - O - - - -
G - - - - - - - -
H - - - - - - - -
Please enter your move: g4

You chose position G4!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X - - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - - - - - -
Computer is making its move...

The computer chose position H4!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X - - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - X - - - -
Please enter your move: d6

You chose position D6!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - - X X O - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - X - - - -
Computer is making its move...

The computer chose position D3!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - - X X X O - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - X - - - -
Please enter your move: d2

You chose position D2!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - - - - - - -
D - O X X X O - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - X - - - -
Computer is making its move...

The computer chose position C3!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - X - - - - -
D - O X X X O - -
E - - X O O O X -
F - - - O - - - -
G - - - O - - - -
H - - - X - - - -
Please enter your move: f3

You chose position F3!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - - - - - - -
C - - X - - - - -
D - O X X X O - -
E - - X O O O X -
F - - O O - - - -
G - - - O - - - -
H - - - X - - - -
Computer is making its move...

The computer chose position B3!

1 2 3 4 5 6 7 8
A - - - - - - - -
B - - X - - - - -
C - - X - - - - -
D - O X X X O - -
E - - X O O O X -
F - - O O - - - -
G - - - O - - - -
H - - - X - - - -
Computer wins!
