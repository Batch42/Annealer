package nQueens;
import java.util.Random;

public class Board {
	private Random rnd;
	private int[] x,y;
	private Queen[] queens;
	private int n;
	public Board(int n) {
		this.n = n;
		rnd = new Random();
		queens = new Queen[n];
		x = new int[n];
		y = new int[n];
	}
	
	public void anneal() {
		//setup
		for (int i = 0; i<n; i++) {
			int rx = rnd.nextInt(n);
			
			this.x[rx]++;
			this.y[i]++;
			queens[i]=new Queen(rx,i,this);
				
		}
		for (int i = 0; i<n; i++) {
			new Thread(queens[i]).run();
		}
		
		
	}
	
	public synchronized void movex(int x1, int x2) {
		x[x1]--;
		assert(x[x1]>=0);
		x[x2]++;
	}
	
	public synchronized void movey(int y1, int y2) {
		y[y1]--;
		assert(y[y1]>=0);
		y[y2]++;
	}
	
	public int getx(int i) {
		if (i<0||i>=n)
			return -1;
		return x[i];
	}
	
	public int gety(int i) {
		if (i<0||i>=n)
			return -1;
		return y[i];
	}
}
