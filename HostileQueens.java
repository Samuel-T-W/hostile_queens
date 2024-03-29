import java.util.Scanner;
public class HostileQueens
{
	public static void main (String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("input board size: ");
		int size=keyboard.nextInt();

		int[] queenPos = new int[size];
		ChessBoard board = new ChessBoard(size);

		
		System.out.print("Enter row for queen in column 0: ");
		int row = keyboard.nextInt();
		if (EightQueens.setQueens(queenPos, row, size))
		{
			board.setQueens(queenPos);
			board.drawBoard();
		}
		else System.out.println("No solution");
	}
}
class ChessBoard
{
	private int size;
	private boolean[][] board;		
	public ChessBoard()
	{
		size=8;
		board = new boolean[size][size];
	}
		public ChessBoard(int s)
	{
		size= s;
		board = new boolean[size][size];
	}
	private void clearBoard()
	{
		for(int i=0;i < size;i++)
			for(int j=0;j < size;j++)
				board[i][j] = false;
	}
	//Post:  8 queens set on board at cells (queenPos[0], 0), (queenPos[1], 1), …, (queenPos[size-1], size-1)
	public void setQueens(int[] queenPos)
	{
		clearBoard();
		for (int col = 0; col < size; col++)
			board[queenPos[col]][col] = true;
	}
	public void drawBoard()
	{
		System.out.print("  ");
		for (int p = 0; p <= size-1; p++){
			System.out.printf("%3d", p);
		}
		System.out.println();
		for (int i = 0; i < size; i++)
		{
			System.out.printf("%2d", i);
			for (int j = 0; j < size; j++)
				if (board[i][j] == true) System.out.printf("%3c", 'Q');
				else System.out.printf("%3c", '-');
			System.out.println();
		}
	}
}
class EightQueens
{
	//Return:True if (row,col) is safe, that is, queens set in queenPos[0]..queenPos[col-1] cannot 
	//	attack queen at (row,col); return false otherwise.
	//Note:	(j, i) and (row,col) in the same \ diagonal if i-j=col-row
	//	(j, i) and (row,col) in the same / diagonal if i+j=col+row
	public static boolean safeLocation(int row, int col, int[] queenPos)
	{
		for (int i = 0; i < col; i++)	
		{
			int j = queenPos[i];				//there is a queen at (j,i)
			if (j == row) return false;				//same row
			else if(i-j==col-row || i+j==col+row) return false;	//same diagonal
		}
		return true;
	}
	//Pre:	row is the position of the first queen
	//Post:	If there is a solution, queenPos[0] set to row, queenPos[1]..queenPos[size-1] set to safe 
	//	positions
	//Return:	True if there is a solution; false otherwise.
	public static boolean setQueens(int[] queenPos, int row, int size)
	{
		queenPos[0] = row;
		if (placeQueens(queenPos, 1, size)) return true;	//recursive; the 2nd argument col keeps 
		else return false;				//getting bigger until it reaches size
	}
	//Pre:	queenPos[0]..queenPos[col-1] have queens set safely
	//	col is the next column to be set
	//	col>0
	//Post:	If there is a solution, queenPos[col]..queenPos[size-1] set to safe positions so that 
	//	queenPos[0]..queenPos[size-1] are all safe
	//Return:	True if there is a solution; false otherwise.
	//Def: 	I will set queenPos[col], then recursive call sets queenPos[col+1] to queenPos[size-1]
	//	If I cannot set queenPos[col], then I will return false so the previous step can backtrack
	private static boolean placeQueens(int[] queenPos, int col, int size)
	{
		int row;
		boolean found;
		if (col == size) found = true;
		else
		{
			found = false; 
			row = 0;
			while (row < size && !found)
			{
				if (safeLocation(row, col, queenPos) == true)
				{
					queenPos[col] = row;
					found = placeQueens(queenPos,col+1, size); //assuming the 
					if (!found) row++; 	//recursive call does what it does,
				}				//if it returns true, found solution
								//if not, back track and try next row
				else row++;			
			}	
		}
		return found;
	}
}
