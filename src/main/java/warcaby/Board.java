package warcaby;

// import java.awt.Color;
import javafx.scene.paint.Color;
import warcaby.Kafelek.kolorKafelka;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.swing.event.SwingPropertyChangeSupport;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;



public class Board implements Runnable {

    public static int kafelekSize;

    BorderPane root = new BorderPane();

    boolean biciedotylu = false;

    Pawn[][] tabPawns; //do zmiany przy wersji polskiej na 10 x 10

    Client client;

    Pane pane = new Pane();

    Kafelek[][] tablica; //do zmiany przy wersji polskiej na 10 x 10
    private int toBoard(double pixel){
        //System.out.println(pixel);
        return (int)((pixel)/kafelekSize);
    }

    /* 
     * width - szerokosc planszy jako ilosc pol
     * bol - jesli true to rog jest ciemny, jesli false to rog jest jasny
     * cl - client
     * biciedotylu - jesli true to mozna bic do tylu 
    */
    public Board(int width, Boolean bol, Client cl, boolean biciedotylu){
        //System.out.println("Board "+ cl);
        kafelekSize = 1200/width - 30;
        tablica = new Kafelek[width][width];
        tabPawns = new Pawn[width][width];
        this.biciedotylu = biciedotylu;
        Thread watek = new Thread(this);
        watek.start();
        System.out.println();
        root.setPrefSize(width * kafelekSize, width * kafelekSize);

        client = cl;
        //ToolBar toolbar = new ToolBar( new Button("Poddaj sie"), new Button("Remis"));
        // Button Poddajbutton = new Button("Poddaj sie");
        // Button remisButton = new Button("Remis");

        //root.setBottom(toolbar);

        //GridPane grid = new GridPane();

        
        root.setCenter(pane);

        /* 
         * Tworzenie planszy 
         */
        for( int i = 0; i <  width; i++){
            for( int j = 0; j < width; j++){
                Kafelek kafelek = new Kafelek(i, j, bol, kafelekSize);
                pane.getChildren().add(kafelek);
                tablica[i][j] = kafelek;

            }
        }
        /*
         * Dodawanie pionkow
         */
        for( int i = width - 1; i >= 0; i--){ 
            for( int j = width - 1; j >= 0; j--){ 
                if((tablica[i][j].jakiKolor() == kolorKafelka.CIEMNY && j < width/2 - 1)){  

                    Pawn pionek = new Pawn(i*kafelekSize + kafelekSize/2, j * kafelekSize + kafelekSize/2, kafelekSize/4); 
                    tabPawns[i][j] = pionek;
                    //tablica[i][j].addPawn();
                    pionek.setColor(Color.valueOf("#c40003"));

                    pane.getChildren().add(pionek);

                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/kafelekSize, (int)pionek.oldY/kafelekSize, newX, newY);
                        pionek.abortMove();
                    });
                }
                else if((tablica[i][j].jakiKolor() == kolorKafelka.CIEMNY && j > width/2 )){ 
                    Pawn pionek = new Pawn(i*kafelekSize + kafelekSize/2, j * kafelekSize + kafelekSize/2, kafelekSize/4);
                    tabPawns[i][j] = pionek;
                    pionek.setColor(Color.valueOf("#fff9f4"));

                    pane.getChildren().add(pionek);
                    //tablica[i][j].addPawn();
                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/kafelekSize, (int)pionek.oldY/kafelekSize, newX, newY);
                        pionek.abortMove();
                    });
                }

            }
        }
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("przed wejsciem do petli");
            while (client.in.hasNextLine()) {
                System.out.println("wchodze do petli");
                String odp = client.in.nextLine();
                System.out.println(odp);
                if (odp.startsWith("MOVE")) {
                    System.out.println("czyta2");
                    //movepionek((int)pionek.oldX/100, (int)pionek.oldY/100, newX, newY);
                    movepionek(Integer.parseInt(odp.substring(4, 5)), Integer.parseInt(odp.substring(5, 6)), Integer.parseInt(odp.substring(6, 7)), Integer.parseInt(odp.substring(7, 8)));
                    System.out.println("c2");
                }
                if(odp.startsWith("KILL")){
                    System.out.println("kill");
                    killpionek(Integer.parseInt(odp.substring(4, 5)), Integer.parseInt(odp.substring(5, 6)));
                }
            }
        }
    }

    public void movepionek(int oldX, int oldY, int newX, int newY){
        //System.out.println(oldX +" "+ " "+ oldY +" " + newX +" " + newY);
        tabPawns[oldX][oldY].move(newX, newY);
        tabPawns[newX][newY] = tabPawns[oldX][oldY];
        tabPawns[oldX][oldY] = null;
    }

    public void killpionek(int x, int y){
        // pane.getChildren().remove(tabPawns[x][y]);
        tabPawns[x][y].move(1000, 1000);
        tabPawns[x][y] = null;
    }

    public void addQueen(int x, int y){

    }

}
