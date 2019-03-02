import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;



/**
 * @author Derek Windahl, Isaac Freshour, and Steven Proctor
 */
public class Application {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		File sudokuFile;
		int[][] sudokuBoard = new int[25][];
		boolean[][] readOnly = new boolean[25][];

		int choice = 0;
		boolean verbose = false;

		System.out.println("Please enter which problem you would like to run. ");
		System.out.println("1 = n-Queens, 2 = Sudoku ");
		System.out.print(">>");
		choice = input.nextInt();
		System.out.println("Please enter whether or not you would like verbose output. ");
		System.out.println("1 = Yes, 2 = No");
		System.out.print(">>");
		if(input.nextInt() == 1) {
			verbose = true;
		} else {
			verbose = false;
		}

		if (choice == 1) {
			
			System.out.println("\nPlease enter your 'n' value for this problem. ");
			System.out.print(">>");
			int nVal = input.nextInt();
			
			System.out.println("\nHill Climbing");
			long time = System.currentTimeMillis();
			boolean[][] solution = new Board(nVal,false,verbose).solve();
			System.out.println("Time: " + (System.currentTimeMillis()-time) + "ms");
			for (int i = 0; i < nVal; i++) {
				for (int j = 0; j < nVal; j++) {
					
					if (solution[i][j]) 
						System.out.print(" Q");
					else
						System.out.print(" =");
					
				}
				System.out.println();
			}
			System.out.println("\nSimulated Annealing");
			time = System.currentTimeMillis();
			solution = new Board(nVal,true,verbose).solve();
			System.out.println("Time: " + (System.currentTimeMillis()-time) + "ms");
			for (int i = 0; i < nVal; i++) {
				for (int j = 0; j < nVal; j++) {
					
					if (solution[i][j]) 
						System.out.print(" Q");
					else
						System.out.print(" =");
					
				}
				System.out.println();
			}
			
		} else if (choice == 2) {
			// Open a file browser to get the sudoku board .csv file
			System.out.println("\nPlease enter your sudoku file's name. ");
			System.out.print(">>");
			sudokuFile = new File(input.next());

			try (BufferedReader reader = new BufferedReader(new FileReader(sudokuFile.getPath()))) {

				String line = "";
				
				int i = 0;
				while ((line = reader.readLine()) != null) {
					int j = 0;
					sudokuBoard[i] = new int[25];
					readOnly[i] = new boolean[25];
					Stack<Integer> used = new Stack<Integer>();
					for (String item : line.split(",")) {
						int val = Integer.parseInt(item);
						sudokuBoard[i][j] = val;
						readOnly[i][j] = val != 0;
						if (val != 0)
							used.push(val);
						j++;
					}
					int val = 1;
					for (j=0;j<25;j++) {
						while(used.contains(val))
							val++;
						if (readOnly[i][j])
							continue;
						sudokuBoard[i][j] = val;
						val++;
					}
					i++;
				}
				for (i = 0; i < 25; i++) {
					for (int j = 0; j < 25; j++) {
						System.out.print(sudokuBoard[i][j] + ", ");
						if (sudokuBoard[i][j] < 10) {
							System.out.print(" ");

						}
					}
					System.out.println();
				}
				System.out.println("\nHill Climbing");
				long time = System.currentTimeMillis();
				int[][] solution = new SudokuAgent(sudokuBoard, readOnly, false, verbose).solve();
				System.out.println("Time: " + (System.currentTimeMillis()-time) + "ms");
				for (i = 0; i < 25; i++) {
					for (int j = 0; j < 25; j++) {
						System.out.print(solution[i][j] + ", ");
						if (solution[i][j] < 10) {
							System.out.print(" ");

						}
					}
					System.out.println();
				}
				System.out.println("\nSimulated Annealing");
				time = System.currentTimeMillis();
				solution = new SudokuAgent(sudokuBoard, readOnly, true, verbose).solve();
				System.out.println("Time: " + (System.currentTimeMillis()-time) + "ms");
				for (i = 0; i < 25; i++) {
					for (int j = 0; j < 25; j++) {
						System.out.print(solution[i][j] + ", ");
						if (solution[i][j] < 10) {
							System.out.print(" ");

						}
					}
					System.out.println();
				}
			} catch (FileNotFoundException fnfe) {
				System.err.println(fnfe);
			} catch (IOException ioe) {
				System.err.println(ioe);
			}

		} else {
			System.err.println("Invalid input. Please try again. ");
		}
		input.close();

	}// end main
}// end class