import java.util.Arrays;

public class GridEval {
    private final int width;
    private final int height;
    private final int[][] gridValues;
    private final static int[][] searchDirections = {{1,0},{1,1},{0,1},{-1,1}};

    public GridEval() {
        this(7, 6);
    }
    public GridEval(int width, int height) {
        this.width = width;
        this.height = height;
        gridValues = new int[height][width];
        generateGridValues();
    }
    private void generateGridValues() {
        for (int j=0;j<height;j++) {
            for (int i=0;i<width;i++) {
//                System.out.println("==Checking "+i+","+j);
                for (int[] dir : searchDirections) {
                    int[] pos = new int[] {i + dir[0] * 3, j + dir[1] * 3};
                    if (pos[0]>=0 && pos[1]>=0 && pos[0]<width && pos[1]<height) {
//                        System.out.println("Found Line: start("+i+","+j+") direction("+dir[0]+","+dir[1]+")");
                        for (int dis=0;dis<4;dis++) {
//                            System.out.println("adding value: "+(i + dir[0] * dis)+","+(j + dir[1] * dis));
                            gridValues[j + dir[1] * dis][i + dir[0] * dis] += 1;
                        }
                    }
                }
            }
        }
//        for (int j=0;j<height;j++) {
//            for (int i = 0; i < width; i++) {
//                System.out.print(gridValues[j][i]+"-");
//            }
//            System.out.println();
//        }
    }
    public int evaluate(int[][] grid) {
        int total = 0;
        int winner = detectWin(grid);
        if (winner == 1) {
            return 10000;
        } else if (winner == 2) {
            return -10000;
        }
        for (int j=0;j<height;j++) {
            for (int i=0;i<width;i++) {
                if (grid[j][i] == 1) {
                    total += gridValues[j][i];
                } else if (grid[j][i] == 2) {
                    total -= gridValues[j][i];
                }
            }
        }
        return total;
    }
    public static int detectWin(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;
        for (int j=0;j<height;j++) {
            for (int i=0;i<width;i++) {
                for (int[] dir : searchDirections) {
                    int dis = -1;
                    int matchCol = grid[j][i];
                    if (matchCol != 0) {
                        int[] pos;
                        do {
                            dis+=1;
                            pos = new int[] {i + dir[0] * dis, j + dir[1] * dis};
                        } while (pos[0]>=0 && pos[1]>=0 && pos[0]<width && pos[1]<height && matchCol==grid[pos[1]][pos[0]]);
                    }
                    if (dis>=4) {
                        return matchCol;
                    }
                }
            }
        }
        return 0;
    }
}
