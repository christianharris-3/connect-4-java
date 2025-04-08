import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameGrid extends GridPane {
    private final int width;
    private final int height;
    private final int squareWidth = 30;
    private final int squareHeight = 30;

    public int[][] grid;
    public boolean player1go = true;

    public GameGrid() {
        this(7, 6);
    }
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;

        this.grid = new int[height][width];

        setFormating();
        generateGrid();
    }

    private void setFormating() {
        setHgap(3);
        setVgap(3);
        setAlignment(Pos.CENTER);
    }
    private void generateGrid() {
        for (int i=0;i<height+1;i++) {
            for (int j=0;j<width;j++) {
                if (i == 0) {
                    Button newButton = new Button("↓");
                    newButton.setMinWidth(squareWidth);
                    int index = j;
                    newButton.setOnAction(e -> dropOnColumn(index));
                    add(newButton, j, i);
                } else {
                    Rectangle newItem = new Rectangle(0,0,squareWidth,squareHeight);
                    if (grid[i-1][j] == 0) {
                        newItem.setFill(Color.DIMGREY);
                    } else if (grid[i-1][j] == 1) {
                        newItem.setFill(Color.BLUE);
                    } else if (grid[i-1][j] == 2) {
                        newItem.setFill(Color.RED);
                    }
                    add(newItem, j, i);
                }
            }
        }
    }
    public void dropOnColumn(int column) {
        this.grid[0][column] = 2;
        generateGrid();
    }
    public void fallTick() {

    }
}
