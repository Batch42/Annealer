

public class Queen{
	private int x,y;
	

	private Board board;

	public Queen(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}

	public int getConflicts(int y) {
		int conf = 0;
		for (int i = 1; i < board.getN(); i++) {
			for (Queen q : board.getQueens()) {
				if (q.x == x + i && q.y == y + i)
					conf++;
				if (q.x == x + i && q.y == y - i)
					conf++;
				if (q.x == x - i && q.y == y + i)
					conf++;
				if (q.x == x - i && q.y == y - i)
					conf++;
			}

		}
		return conf;
	}


	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getConflicts() {
		return getConflicts(y);
	}

	
	

	
}
