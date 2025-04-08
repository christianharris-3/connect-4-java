import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class GUI extends Application{
    @Override
    public void start(Stage stage) {

        Button button = new Button("basic button");
        GameGrid grid = new GameGrid();

        VBox root = new VBox(button,grid);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
//        root.setMargin(new Insets(10,10,10,10));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
