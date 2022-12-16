package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;

public class App extends Application{

    Board board = new Board();
    Client client;

    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(board.createBoard(8, true));
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void mainCall(Client client){
        this.client = client;
        main(null);
    }

}
