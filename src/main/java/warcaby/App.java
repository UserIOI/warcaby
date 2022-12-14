package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;

public class App extends Application{

    static Board board;
    static Client client1;
    static boolean bicieDoTyluApp;
    static int boardSizeApp;
    static boolean kolorRoguApp; // jesli ciemny w rogu to true

    public void mainCall(Client client, boolean bicieDoTylu, boolean kolorRogu, int boardSize){
        client1 = client;
        bicieDoTyluApp = bicieDoTylu;
        boardSizeApp = boardSize;
        kolorRoguApp = kolorRogu;
        //System.out.println("mainCall " +client);
        main(null);
    }

    public static void createBoard(Client client){
        //System.out.println("createBoard " + client);
        board = new Board(boardSizeApp, kolorRoguApp, client, bicieDoTyluApp);
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
