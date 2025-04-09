import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameGrid extends VBox {
    private final int width;
    private final int height;
    private final int squareWidth = 80;
    private final int squareHeight = 80;
    private final int[][] searchDirections = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
    private final String[] PLAYER_COL = new String[] {"None","Blue", "Red"};

    private final HBox titleBar;
    private final GridPane gridWidget;
    private final Label titleText;
    private final Button resetButton;

    public int[][] grid;
    public boolean player1go = true;
    private boolean animating = false;
    private int gameWinner = 0;

    public GameGrid() {
        this(7, 6);
    }
    public GameGrid(int width, int height) {
        this.width = width;
        this.height = height;

        this.grid = new int[height][width];
        this.resetButton = new Button("Reset Game");
        this.resetButton.setOnAction(e -> reset());
        this.titleText = new Label("");
        this.titleBar = new HBox(this.resetButton,this.titleText);
        this.gridWidget = new GridPane();
        this.getChildren().add(this.titleBar);
        this.getChildren().add(this.gridWidget);

        setFormating();
        generateGrid();
    }

    private void setFormating() {
        gridWidget.setHgap(3);
        gridWidget.setVgap(3);
        setAlignment(Pos.CENTER);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setSpacing(40);
        titleText.setStyle("-fx-font-size: 30;");
    }
    private void generateGrid() {
        gridWidget.getChildren().clear();
        if (gameWinner == 0) {
            if (player1go) {
                titleText.setText("Red's Turn");
            } else {
                titleText.setText("Blue's Turn");
            }
        } else if (gameWinner == 2) {
            titleText.setText("Red Won");
        } else {
            titleText.setText("Blue Won");
        }

        for (int i=0;i<height+1;i++) {
            for (int j=0;j<width;j++) {
                if (i == 0) {
                    gridWidget.add(createDropButton(j), j, i);
                } else {
                    Rectangle newItem = new Rectangle(0,0,squareWidth,squareHeight);
                    if (grid[i-1][j] == 0) {
                        newItem.setFill(Color.DIMGREY);
                    } else if (grid[i-1][j] == 1) {
                        newItem.setFill(Color.BLUE);
                    } else if (grid[i-1][j] == 2) {
                        newItem.setFill(Color.RED);
                    }
                    gridWidget.add(newItem, j, i);
                }
            }
        }
    }
    private Button createDropButton(int index) {
        Button newButton = new Button("↓");
        newButton.setMinWidth(squareWidth);
        newButton.setOnAction(e -> dropOnColumn(index));
        String css = "-fx-text-fill: #ffffff; -fx-font-size: 30;";
        if (grid[0][index] != 0 || animating || gameWinner!=0) {
            newButton.setDisable(true);
        } else if (player1go) {
            css += "-fx-background-color: #ff4444;";
        } else {
            css += "-fx-background-color: #7777ff;";
        }
        newButton.setStyle(css);
        GridPane.setMargin(newButton ,new Insets(0,0,5,0));
        return newButton;
    }
    public void dropOnColumn(int column) {
        if (player1go) {
            this.grid[0][column] = 2;
        } else {
            this.grid[0][column] = 1;
        }
        player1go = !player1go;
        animating = true;
        animateFall(true);
    }
    private void animateFall(boolean skipFall) {
        if (skipFall || fallTick()) {
            PauseTransition pause = new PauseTransition(Duration.millis(40));
            pause.setOnFinished(e -> animateFall(false));
            pause.play();
        } else {
            animating = false;
            winDetection();
        }
        generateGrid();
    }
    private boolean fallTick() {
        boolean moved = false;
        for (int j=height-1;j>0;j-=1) {
            for (int i=0;i<width;i++) {
                if (grid[j][i] == 0 && grid[j-1][i] != 0) {
                    grid[j][i] = grid[j-1][i];
                    grid[j-1][i] = 0;
                    moved = true;
                }
            }
        }
        return moved;
    }
    private void winDetection() {
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
                        gameWinner = matchCol;
                    }
                }
            }
        }
        generateGrid();
    }
    public void reset() {
        gameWinner = 0;
        animating = false;
        player1go = true;
        for (int j=0;j<height;j++) {
            for (int i = 0; i < width; i++) {
                grid[j][i] = 0;
            }
        }
        generateGrid();
    }
}
