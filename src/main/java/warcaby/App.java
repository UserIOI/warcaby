package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;

public class App extends Application{

    static Board board;
    static Client client1;
    static Boolean bicie = false; // false jesli bez bicia do tylu a true jesli z biciem do tylu
    static int boardSize = 8; // wielkosc planszy, tez do zmiany przy wywolywaniu u clienta

    public void mainCall(Client client){
        client1 = client;
        //System.out.println("mainCall " +client);
        main(null);
    }

    public static void createBoard(Client client){
        //System.out.println("createBoard " + client);
        board = new Board(8, true, client, bicie);
        //System.out.println(board.client);
    }

    public void start(Stage stage) throws Exception{
        //System.out.println("Board ze startu" + board);
        //System.out.println(client1);
        Scene scene = new Scene(board.root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        //System.out.println("main ");
        createBoard(client1);
        launch(args);
    }


}
