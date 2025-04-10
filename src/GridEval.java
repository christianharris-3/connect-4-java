public class GridEval {
    private final int width;
    private final int height;
    private final int[][] gridValues;
    private final int[][] searchDirections = {{1,0},{1,1},{0,1},{-1,1}};

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
        for (int j=0;j<height;j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(gridValues[j][i]+"-");
            }
            System.out.println();
        }
    }
    public int evaluate(int[][] grid) {
        int total = 0;
        for (int j=0;j<height;j++) {
            for (int i=0;i<width;i++) {
                total += gridValues[j][i]*(-1.5*grid[j][i]*grid[j][i]+2.5*grid[j][i]);
            }
        }
        return total;
    }
}
