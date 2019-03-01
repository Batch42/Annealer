import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;

/**
 * @author Derek Windahl, Isaac Freshour, and Steven Proctor
 */
public class Application {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		JFileChooser fileFind = new JFileChooser();
		File sudokuFile;
		int[][] sudokuBoard = new int[25][];
		boolean[][] readOnly = new boolean[25][];
		Random rnd = new Random();

		int choice = 0;

		System.out.println("Please enter which problem you would like to run. ");
		System.out.println("1 = n-Queens, 2 = Sudoku ");
		System.out.print(">>");
		choice = input.nextInt();

		if (choice == 1) {

		} else if (choice == 2) {
			// Open a file browser to get the sudoku board .csv file
			// fileFind.showOpenDialog(null);
			// sudokuFile = fileFind.getSelectedFile();

			try (BufferedReader reader = new BufferedReader(new FileReader(
					"sudoku1.csv"))) {

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
				System.out.println("Hill Climbing");
				long time = System.currentTimeMillis();
				int[][] solution = new SudokuAgent(sudokuBoard, readOnly,false,false).solve();
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
				System.out.println("Simulated Annealing");
				time = System.currentTimeMillis();
				solution = new SudokuAgent(sudokuBoard, readOnly,true,false).solve();
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