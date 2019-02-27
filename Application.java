import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
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
					"C:\\Users\\Isaac\\Documents\\School\\College Year 3\\Spring 2019\\COSC 370.01\\Projects\\Project 2\\sudoku\\sudoku1.csv"))) {

				String line = "";
				{
					int i = 0;
					while ((line = reader.readLine()) != null) {
						int j = 0;
						sudokuBoard[i] = new int[25];
						readOnly[i] = new boolean[25];
						for (String item : line.split(",")) {
							int val = Integer.parseInt(item);
							sudokuBoard[i][j] = val == 0 ? rnd.nextInt(25) + 1 : val;
							readOnly[i][j] = val != 0;
							j++;
						}
						i++;
					}
				}

				int[][] solution = new SudokuAgent(sudokuBoard, readOnly).solve();
				for (int i = 0; i < 25; i++) {
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