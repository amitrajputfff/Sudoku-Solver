import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI {
    private JFrame frame;
    private JTextField[][] cells;
    private JButton solveButton;

    public SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        frame.setLayout(new GridLayout(9, 9));
        cells = new JTextField[9][9];

        // Initialize text fields for the Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField(1);
                frame.add(cells[row][col]);
            }
        }

        // Add Solve button
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        frame.add(solveButton);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void solveSudoku() {
        int[][] grid = new int[9][9];
        // Parse the input Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String val = cells[row][col].getText();
                grid[row][col] = val.isEmpty() ? 0 : Integer.parseInt(val);
            }
        }

        if (solve(grid)) {
            // Update the GUI with the solved Sudoku
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cells[row][col].setText(Integer.toString(grid[row][col]));
                }
            }
            JOptionPane.showMessageDialog(frame, "Sudoku solved successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "No solution exists for the given Sudoku.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Backtracking algorithm to solve Sudoku
    private boolean solve(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solve(grid)) {
                                return true;
                            } else {
                                grid[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Check if the move is valid
    private boolean isValidMove(int[][] grid, int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Check subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuSolverGUI();
            }
        });
    }
}
