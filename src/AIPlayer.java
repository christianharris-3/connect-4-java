import java.util.Arrays;

public class AIPlayer {
    private final int searchDepth = 5;
    private final int intelligence = 0;
    private final GridEval gridEvaluator;
    private final int AIPlayerCol;
    public AIPlayer (int playerCol) {
        gridEvaluator = new GridEval();
        this.AIPlayerCol = playerCol;
    }
    public int getMove(int[][] grid) {
        return getMove(grid, AIPlayerCol, searchDepth, true);
    }
    public int getMove(int[][] grid, int toPlayCol, int depth,  boolean returnIndex) {
        int best_val = 1000000;
        int best_index = 0;
        for (int i=0;i<grid[0].length;i++) {
            if (canMove(grid, i)) {
                int[][] newGrid = addMove(grid, i, toPlayCol);
                int value;
                if (depth == 0) {
                    value = gridEvaluator.evaluate(newGrid);
                } else {
                    int winner = GridEval.detectWin(newGrid);
                    if (winner == 1) {
                        value = 10000*depth;
                    } else if (winner == 2) {
                        value = -10000*depth;
                    } else {
                        value = getMove(newGrid, (-toPlayCol+3), depth-1, false);
                    }
                }
//                System.out.println("Depth: "+depth+" drop index: "+i+" value: "+value);
                if ((best_val == 1000000) || (value>best_val && toPlayCol == 1) || (value<best_val && toPlayCol == 2)) {
                    best_val = value;
                    best_index = i;
                }
            }
        }
        if (returnIndex) {
            return best_index;
        }
        return best_val;
    }
    private int[][] addMove(int[][] grid, int index, int playerCol) {
        int row = 0;
        while (row<grid.length-1 && grid[row+1][index] == 0) {
            row+=1;
        }
        int[][] gridCopy = new int[grid.length][grid[0].length];
        for (int i=0;i<grid.length;i++) {
            gridCopy[i] = grid[i].clone();
        }
        gridCopy[row][index] = playerCol;
        return gridCopy;
    }
    private boolean canMove(int[][] grid, int index) {
        return (grid[0][index] == 0);
    }

}
