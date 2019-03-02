
import java.util.Random;

public class Board {
	private Random rnd;
	private Queen[] queens;
	private int n;
	
	private boolean anneal,vb;
	private final int maxTime = 10000,r=3;
			
	public Board(int n,boolean anneal, boolean verbose) {
		this.n = n;
		this.anneal = anneal;
		this.vb = verbose;
		rnd = new Random();
		queens = new Queen[n];
	}
	
	public Queen[] getQueens() {
		return queens;
	}
	
	public boolean[][] solve() {
		//setup
		for (int i = 0; i<n; i++) {
			
			queens[i]=new Queen(i,i,this);
				
		}
		boolean notdone = true;
		int timeout = 0;
		while(notdone) {
			timeout++;
			notdone = false;
			int q1=-1, q2=-1;
			double val = 0;
			/*
			 * The solution is such that each queen will be in its own column, therefore, the problem space
			 * can be reconstructed as queens swapping y values. Randomizing these swaps
			 * through simulated annealling will always lead to an optimal solution provided each queen starts
			 * in it's own column and such a solution exists because the annealing will eventually 
			 * become hillclimbing which is capable of solving this problem on its own.
			 */
			class QueenWorker implements Runnable{//used to perform multithreaded processing
				private int q1,q2,i,timeout;
				private double val;
				private boolean notdone;
				private boolean workerisdone;
				private Queen[] queens;
				QueenWorker(Queen[] q,int i,int timeout){
					queens=q;
					this.i=i;
					this.timeout=timeout;
					workerisdone=false;
				}

				
				
				@Override
				public void run() {
					int conf = queens[i].getConflicts();
					if (conf > 1) 
						notdone = true;
					for (int j = 0; j<n; j++) {
						double temp = conf + queens[j].getConflicts()
							- (queens[i].getConflicts(queens[j].getY()) + queens[j].getConflicts(queens[i].getY()))
							+ (anneal?r*rnd.nextDouble()*(maxTime-timeout)/maxTime:0);
						if (vb) {
							System.out.println("Considering Swapping queen "+i+" at "+queens[i].getY()+" with queen "+j+" at "+queens[j].getY()); 
							System.out.println("Gain value is " + temp);
						}
						if (temp>val) {
							val = temp;
							q1=i;
							q2=j;
						}
					}
					workerisdone=true;
					synchronized(this) {
						notify();
					}
				}
				
				
			}
			QueenWorker[] qw = new QueenWorker[n];
			for (int i = 0; i<n; i++) {
				qw[i] = new QueenWorker(queens,i,timeout);
				new Thread(qw[i]).start();
			}
			for (QueenWorker q:qw) {
				if(!q.workerisdone)
					try {
						synchronized(q) {
							q.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (q.val>val) {
					q1=q.q1;
					q2=q.q2;
					val=q.val;
				}
				notdone=notdone||q.notdone;
			}
			if(q1==-1||timeout==maxTime)
				break;
			int temp = queens[q1].getY();
			queens[q1].setY(queens[q2].getY());
			queens[q2].setY(temp);
			
			System.out.println("Swapping queen "+q1+" at "+queens[q1].getY()+" with queen "+q2+" at "+queens[q2].getY()); 
			System.out.println("Gain value is " + val);
		}
		
		if(notdone) {
			int remaining = 0;
			for(Queen q:queens)
				remaining+=q.getConflicts()>0?1:0;
			System.out.println("Non-optimal state, " + remaining + " queens in conflict. ");
		}
		else
			System.out.println("Optimal State");
		
		boolean[][] output = new boolean[n][];
		for (int i=0; i<n; i++) {
			output[i] = new boolean[n];
			for (int j=0; j<n; j++) {
				output[i][j]=false;
			}
		}
		
		for(Queen q:queens) {
			output[q.getX()][q.getY()]= true;
		}
		
		return output;
	}
	
	public int getN() {
		return n;
	}
	
	public void p(Object o) {
		System.out.println(o);
	}
	
}
