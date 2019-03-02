import java.util.Random;

public class SudokuAgent {
	private int[][] sudokuBoard;
	private boolean[][] readOnly;
	private Random oracle;
	private boolean anneal, vb;
	private final int maxTime = 100000, r = 100;

	public SudokuAgent(int[][] sudokuBoard, boolean[][] readOnly, boolean anneal, boolean verbose) {
		this.sudokuBoard = sudokuBoard;
		this.readOnly = readOnly;
		this.anneal = anneal;
		this.vb = verbose;
		oracle = new Random();
	}
	
	
	public int[][] solve() {
		boolean notdone = true;
		int timeout = 0;
		while (notdone && timeout < maxTime) {
			timeout++;
			notdone = false;
			
			int bx=-1, y1=-1,y2=-1;
			double val=0;
			/*
			 * The solution is such that each digit will be in its own row, therefore, the problem space
			 * can be reconstructed as tiles in a given row swapping x values. Randomizing these swaps
			 * through simulated annealling will lead to a more optimal solution at least as well as hill climbing
			 * because it will eventually become hill climbing but also overcome local maxima as it "cools."
			 */
			for (int i = 0; i < 25; i++) {
				
				for (int j = 0; j < 25; j++) {
					if (readOnly[i][j])
						continue;
					if (getConflicts(i, j, sudokuBoard[i][j]) != 0)
						notdone = true;
					for (int k = 0; k < 25; k++) {
						if (readOnly[i][k])
							continue;
						double temp = getConflicts(i,j, sudokuBoard[i][j])+getConflicts(i,k, sudokuBoard[i][k])
							- (getConflicts(i,j, sudokuBoard[i][k])+getConflicts(i,k, sudokuBoard[i][j]))
							+ (anneal?r*oracle.nextDouble()*(maxTime-timeout)/maxTime:0);
						if (vb) {
							System.out.println("Considering Swapping "+sudokuBoard[i][j]+" at "+i+","+j+" with "+sudokuBoard[i][k]+" at "+i+","+k); 
							System.out.println("Gain value is " + val);
						}
						if (temp>val) {
							val = temp;
							bx=i;
							y1=j;
							y2=k;
						}
						
					}
					
				}
			}
			if(bx==-1) {
				break;
			}
			System.out.println("Swapping "+sudokuBoard[bx][y1]+" at "+bx+","+y1+" with "+sudokuBoard[bx][y2]+" at "+bx+","+y2);
			System.out.println("Gain value is " + val);
			int temp = sudokuBoard[bx][y1];
			sudokuBoard[bx][y1] = sudokuBoard[bx][y2];
			sudokuBoard[bx][y2] = temp;
			
		}
		
			
		if(notdone) {
			int remaining = 0;
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					remaining+=getConflicts(i,j,sudokuBoard[i][j])>0?1:0;
				}
			}
			System.out.println("Non-optimal state, " + remaining + " tiles in conflict. ");
		}
		else
			System.out.println("Optimal State");
		
		return sudokuBoard;
	}

	private int getConflicts(int x, int y, int v) {
		int count = 0;
		int cx = x / 5, cy = y / 5;
		for (int i = 0; i < 25; i++) {
			if (sudokuBoard[x][i] == v && i != y)
				count++;
			if (sudokuBoard[i][y] == v && i != x)
				count++;
		}
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				if (sudokuBoard[cx * 5 + i][cy * 5 + j] == v && cx * 5 + i != x && cy * 5 + j != y)
					count++;

		return count;
	}

}
