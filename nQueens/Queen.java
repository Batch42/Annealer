package nQueens;

public class Queen implements Runnable {
	private int x,y;
	private Board board;
	public Queen(int x, int y,Board board) {
		this.x=x;
		this.y=y;
		this.board = board;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
