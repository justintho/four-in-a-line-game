import java.util.Scanner;

public class FourInALine {

	public static void main(String[] args) {
		//Declaring variables
		Scanner scan = new Scanner(System.in); //For receiving user input
		int turnChoice;
		int[][] board = new int[8][8];
		boolean gameover = false;
		
		//Prints introduction and prompts user for turn order
		printIntroduction();
		turnChoice = promptTurn(scan);
		
		//Prints the initial board
		System.out.println("\nCurrent Board:\n");
		printBoard(board);
		
		//Repeats until the game ends
		while(!gameover) {
			//If player chooses to go first
			if (turnChoice == 1) {
				board = getMove(board, scan);
				gameover = checkGameOver(board);
				if (!gameover) {
					board = makeMove(board);
					gameover = checkGameOver(board);
				}
			}
			//If player chooses to go second
			else {
				board = makeMove(board);
				gameover = checkGameOver(board);
				if (!gameover) {
					board = getMove(board, scan);
					gameover = checkGameOver(board);
				}
			}
		}
		//Exits program when game ends
		System.exit(0);
	}
	
	//Prints the introduction for the game including the rules.
	public static void printIntroduction() {
		System.out.println("CS4200 Four-In-A-Line Game:");
		System.out.println("The goal of the game is to make a column or row of four and");
		System.out.println("whoever accomplishes this first is the winner. The player's");
		System.out.println("moves are represented by 'O', while the computer is represented");
		System.out.println("by 'X'.");
		System.out.println();
	}
	
	//Prompts user for the turn order and validates input
	public static int promptTurn(Scanner scan) {
		int turnChoice;
		
		System.out.println("Please choose whether you wish to go first or second:");
		System.out.println("[1] First");
		System.out.println("[2] Second");
		System.out.print("Input: ");
		turnChoice = scan.nextInt();
		
		//Input validation
		while (turnChoice != 1 && turnChoice != 2) {
			System.out.println("Invalid input. Please enter 1 or 2.");
			System.out.println("Please choose whether you wish to go first or second:");
			System.out.println("[1] First");
			System.out.println("[2] Second");
			System.out.print("Input: ");
			turnChoice = scan.nextInt();
		}
		
		return turnChoice;
	}
	
	//Receives a move from the player, validates the move, and updates the board
	public static int[][] getMove(int[][] board, Scanner scan) {
		int[][] newBoard = board;
		String move = "";
		boolean validMove = false;
		
		//Input validation
		while (!validMove) {
			System.out.print("Please enter your move: ");
			move = scan.next();
			move = move.toUpperCase();
			if (move.length() != 2)
				System.out.println("Invalid move. Please enter a letter from A-H followed by a number from 1-8.");
			else if (!(move.charAt(0) >= 'A' && move.charAt(0) <= 'H') || !(move.charAt(1) >= '1' && move.charAt(1) <= '8'))
				System.out.println("Invalid move. Please enter a letter from A-H followed by a number from 1-8.");
			else if (board[move.charAt(0) - 'A'][Character.getNumericValue(move.charAt(1)) - 1] != 0)
				System.out.println("Invalid move. The inputted position is already occupied by another piece.");
			else {
				validMove = true;
				newBoard[move.charAt(0) - 'A'][Character.getNumericValue(move.charAt(1)) - 1] = 1;
			}
		}

		//Printing move chosen and the new board state
		System.out.println();
		System.out.println("You chose position " + move + "!");
		System.out.println();
		printBoard(newBoard);
		
		return newBoard;
	}
	
	//Uses Minimax algorithm with alpha-beta pruning and cut-off test to choose best move
	public static int[][] makeMove(int[][] board) {
		int best = -20000;
		int depth = 4; //Depth 5 takes too long with Java and returns inaccurate results if given 5 seconds
		int score;
		int bestI = 0;
		int bestJ = 0;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		//Variables to help end computations after 5 seconds
		long start = System.currentTimeMillis();
		long end = start + 5 * 1000;
		
		System.out.println("Computer is making its move...");
		
		//Main process
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (System.currentTimeMillis() > end) //Breaks out of loop after 5 seconds
					break;
				if (board[i][j] == 0) {
					board[i][j] = 2; //Set move
					score = min(board, depth - 1, alpha, beta);
					if (score > best) { //Record best move
						bestI = i;
						bestJ = j;
						best = score;	
					}
					board[i][j] = 0; //Reset move
				}
			}
		}
		board[bestI][bestJ] = 2; //Updates board to use best move
		
		//Prints out the move and the new board state
		System.out.println();
		System.out.println("The computer chose position " + (char)(bestI + 65) + (bestJ + 1) + "!");
		System.out.println();
		printBoard(board);
		
		return board;
	}
	
	//Computes the MIN value for the Minimax algorithm with alpha-beta pruning
	//This is calculates the opponent's next best move
	public static int min(int[][] board, int depth, int alpha, int beta) {
		int best = 20000;
		int score;
		
		//Check if terminal state or depth = 0
		if (checkWinner(board) != 0)
			return checkWinner(board);
		if (depth == 0)
			return heuristicFunction(board, 1);
		
		//Determine the best move
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 0) {
					board[i][j] = 1;
					score = max(board, depth - 1, alpha, beta);
					if (score < best)
						best = score;
					beta = Math.min(beta, best);
					board[i][j] = 0;
					if (alpha >= beta)
						break;
				}
			}
		}
		
		return best;
	}
	
	//Computes the MAX value for the Minimax algorithm with alpha-beta pruning
	//This is calculates the computer's next best move
	public static int max(int[][] board, int depth, int alpha, int beta) {
		int best = -20000;
		int score;
		
		//Checks if terminal state or depth = 0
		if (checkWinner(board) != 0)
			return checkWinner(board);
		if (depth == 0)
			return heuristicFunction(board, 2);
		
		//Determines the best move
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 0) {
					board[i][j] = 2;
					score = min(board, depth - 1, alpha, beta);
					if (score > best)
						best = score;
					alpha = Math.max(alpha, best);
					board[i][j] = 0;
					if (alpha >= beta)
						break;
				}
			}
		}
		
		
		return best;
	}
	
	//Calculates the score value of a specified move based on the number of 2, 3, and 4 in a rows/columns
	//that the player/computer can make. It also decreases the score for opponent rows of 3.
	public static int heuristicFunction(int[][] board, int target) {
		int score = 0;
		int numTarget;
		int numEmpty;
		
		//Checks and computes the score value for the rows
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				numTarget = 0;
				numEmpty = 0;
				
				if (board[i][j] == target)
					numTarget++;
				if (board[i][j] == 0)
					numEmpty++;
				if (board[i][j+1] == target)
					numTarget++;
				if (board[i][j+1] == 0)
					numEmpty++;
				if (board[i][j+2] == target)
					numTarget++;
				if (board[i][j+2] == 0)
					numEmpty++;
				if (board[i][j+3] == target)
					numTarget++;
				if (board[i][j+3] == 0)
					numEmpty++;
				
				if (numTarget == 4 && numEmpty == 0)
					score += 100;
				if (numTarget == 3 && numEmpty == 1)
					score += 20;
				if (numTarget == 2 && numEmpty == 2)
					score += 5;
				
				if (numTarget == 0 && numEmpty == 3)
					score -= 5;
			}
		}
		
		//Checks and computes the score value for the columns
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				numTarget = 0;
				numEmpty = 0;
				
				if (board[j][i] == target)
					numTarget++;
				if (board[j][i] == 0)
					numEmpty++;
				if (board[j+1][i] == target)
					numTarget++;
				if (board[j+1][i] == 0)
					numEmpty++;
				if (board[j+2][i] == target)
					numTarget++;
				if (board[j+2][i] == 0)
					numEmpty++;
				if (board[j+3][i] == target)
					numTarget++;
				if (board[j+3][i] == 0)
					numEmpty++;
				
				if (numTarget == 4 && numEmpty == 0)
					score += 100;
				if (numTarget == 3 && numEmpty == 1)
					score += 20;
				if (numTarget == 2 && numEmpty == 2)
					score += 5;
				
				if (numTarget == 0 && numEmpty == 3)
					score -= 5;
			}
		}

		return score;
	}
	
	//Checks for any winner and returns different values depending on the current state of the board
	public static int checkWinner(int[][] board) {
		
		//Checks for a line of four horizontally and returns 100 if computer wins and -100 if player wins
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[i][j] == 2 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3]) 
					return 100;
				if (board[i][j] == 1 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3]) 
					return -100;
			}
		}
		
		//Checks for a line of four vertically and returns 100 if computer wins and -100 if player wins
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[j][i] == 2 && board[j][i] == board[j+1][i] && board[j][i] == board[j+2][i] && board[j][i] == board[j+3][i]) 
					return 100;
				if (board[j][i] == 1 && board[j][i] == board[j+1][i] && board[j][i] == board[j+2][i] && board[j][i] == board[j+3][i]) 
					return -100;
			}
		}
		
		//Checks if all positions are full
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 0) 
					return 0; // Game still ongoing
			}
		}
		
		return 1; // Game is a draw
	}
	
	//Checks if the game is over and prints the winner or if the match was a draw
	public static boolean checkGameOver(int[][] board) {
		//Player won the match
		if (checkWinner(board) == -100) {
			System.out.println("Player wins!");
			return true;
		}
		//Computer won the match
		else if (checkWinner(board) == 100) {
			System.out.println("Computer wins!");
			return true;
		}
		//Game was a draw
		else if (checkWinner(board) == 1) {
			System.out.println("Game ended in a draw!");
			return true;
		}
		//Game ongoing
		else {
			return false;
		}
	}
	
	//Prints the board in a formatted manner
	public static void printBoard(int[][] board) {
		//Prints top row
		System.out.println("  1 2 3 4 5 6 7 8");
		
		//Prints the rest of the rows
		for (int i = 0; i < 8; i++) {
			for (int j = -1; j < 8; j++) {
				if (j == -1) 
					System.out.print(""+ (char)('A' + i));
				else {
					if (board[i][j] == 0) 
						System.out.print("-");
					else if (board[i][j] == 1) 
						System.out.print("O");
					else 
						System.out.print("X");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
