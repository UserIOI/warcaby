package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;


public class App extends Application{

    Pane border = new Pane();
    Board board = new Board();

    public void start(Stage stage) throws Exception{
        border = board.createBoard(8,true);

        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
