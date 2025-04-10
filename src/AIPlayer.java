public class AIPlayer {
    private final int searchDepth = 3;
    private final int intelligence = 0;
    private final GridEval gridEvaluator;
    public AIPlayer () {
        gridEvaluator = new GridEval();
    }
    public int getMove(int[][] grid) {
        int max_val = -1000;
        int max_index = 0;
        for (int i=0;i<grid[0].length;i++) {
            if (canMove(grid, i)) {
                int[][] newGrid = addMove(grid, i, 1);
                int value = gridEvaluator.evaluate(newGrid);
//                System.out.println("eval for dropping at "+i+" is "+value);
                if (value>max_val) {
                    max_val = value;
                    max_index = i;
                }
            }
        }
//        System.out.println("-- chose "+max_index);
        return max_index;
    }
    private int[][] addMove(int[][] grid, int index, int playerCol) {
        int row = 0;
        while (row<grid.length-1 && grid[row+1][index] == 0) {
            row+=1;
        }
        int[][] gridCopy = new int[grid[0].length][grid.length];
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
