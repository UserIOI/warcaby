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
    static boolean kolorRoguApp;

    /**
     * Metoda wywolujaca klase main()
     * @param client
     * @param bicieDoTylu
     * @param kolorRogu
     * @param boardSize
     */
    public void mainCall(Client client, boolean bicieDoTylu, boolean kolorRogu, int boardSize){
        client1 = client;
        bicieDoTyluApp = bicieDoTylu;
        boardSizeApp = boardSize;
        kolorRoguApp = kolorRogu;
        main(null);
    }

    public static void createBoard(Client client){
        board = new Board(boardSizeApp, kolorRoguApp, client, bicieDoTyluApp);
    }

    /** Metoda odpowiadajaca za stworzenie sceny oraz pokazania jej */
    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(board.root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        createBoard(client1);
        launch(args);
    }
}
