
public class SudokuAgent {
	private int[][] sudokuBoard;
	private boolean[][] readOnly;

	public SudokuAgent(int[][] sudokuBoard, boolean[][] readOnly) {
		this.sudokuBoard = sudokuBoard;
		this.readOnly = readOnly;
	}

	public int[][] solve() {
		boolean notdone = true;
		int timeout = 0;
		while (notdone && timeout < 100000) {
			p(timeout);
			timeout++;
			notdone = false;
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					if (readOnly[i][j] || getConflicts(i, j, sudokuBoard[i][j]) == 0)
						continue;
					notdone = true;
					int key = 1;
					int val = getConflicts(i, j, key);
					for (int k = 2; k <= 25; k++) {
						int temp = getConflicts(i, j, k);
						if (temp < val) {
							key = k;
							val = temp;
						}
					}
					sudokuBoard[i][j] = key;
				}
			}
		}
		if (timeout == 100000)
			System.out.println("Shit timed out yo");
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
