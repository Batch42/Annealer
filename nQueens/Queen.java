package nQueens;

public class Queen implements Runnable {
	private int x, y, temperature;
	private Board board;

	public Queen(int x, int y, int temperature, Board board) {
		this.x = x;
		this.y = y;
		this.temperature = temperature;
		this.board = board;
	}

	@Override
	public void run() {
		while(true) {
			int by=y;//better x and y
			int lowconf = board.gety(y);
			for(int i = 0; i<board.getN(); i++)
				if(!(i==y))
				{
					int conf = board.gety(i);
					if(conf==0) {
						by = i;
						break;
					}
					else if(conf<lowconf) {
						by = i;
						lowconf=conf;
					}
						
				}
			if(y==by)
				if (temperature==0)
					break;
			board.movey(y, by);
			y=by;
		}
	}

}
