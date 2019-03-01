import java.util.Random;

public class SudokuAgent {
	private int[][] sudokuBoard;
	private boolean[][] readOnly;
	private Random oracle;
	private final int maxTime = 100, r = 5;

	public SudokuAgent(int[][] sudokuBoard, boolean[][] readOnly) {
		this.sudokuBoard = sudokuBoard;
		this.readOnly = readOnly;
		oracle = new Random();
	}

	public int[][] solve() {
		boolean notdone = true;
		int timeout = 0;
		while (notdone && timeout < maxTime) {
			timeout++;
			notdone = false;
			int x1=-1,x2=-1,y1=-1,y2=-1;
			double val=0;
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					if (readOnly[i][j])
						continue;
					if (getConflicts(i, j, sudokuBoard[i][j]) != 0)
						notdone = true;

					for (int k = 0; k < 25; k++) {
						for (int l = 0; l < 25; l++) {
							double temp = getConflicts(i,j, sudokuBoard[i][j])+getConflicts(k,l, sudokuBoard[k][l])
								- (getConflicts(i,j, sudokuBoard[k][l])+getConflicts(k,l, sudokuBoard[i][j]))
								+ r*oracle.nextDouble()*(maxTime-timeout)/maxTime;
							if (temp>val) {
								val = temp;
								x1 = i;
								y1=j;
								x2=k;
								y2=l;
							}
						}
						
					}
					
					
				}
			}
			int temp = sudokuBoard[x1][y1];
			sudokuBoard[x1][y1] = sudokuBoard[x2][y2];
			sudokuBoard[x2][y2] = temp;
			
		}
		if (timeout == maxTime) {
			int totalerror=0;
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					totalerror+=getConflicts(i,j,sudokuBoard[i][j]);
				}
			}
			System.out.println("Time! " + totalerror);
		}
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

	private void p(Object o) {
		System.out.println(o);
	}
}
