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


    BorderPane root = new BorderPane();

    Pawn[][] tabPawns = new Pawn[8][8];

    Client client;

    Kafelek[][] tablica = new Kafelek[8][8];
    private int toBoard(double pixel){
        //System.out.println(pixel);
        return (int)((pixel)/100);
    }

    /* 
     * width - szerokosc planszy jako ilosc pol
     * bol - jesli true to rog jest ciemny, jesli false to rog jest jasny
    */
    public Board(int width, Boolean bol, Client cl){
        //System.out.println("Board "+ cl);
        Thread watek = new Thread(this);
        watek.start();
        root.setPrefSize(width * 100, width * 100);

        client = cl;
        //ToolBar toolbar = new ToolBar( new Button("Poddaj sie"), new Button("Remis"));
        // Button Poddajbutton = new Button("Poddaj sie");
        // Button remisButton = new Button("Remis");

        //root.setBottom(toolbar);

        //GridPane grid = new GridPane();

        Pane pane = new Pane();
        root.setCenter(pane);

        // Pawn pawn = new Pawn(50,50,25);
        // pawn.setColor(Color.valueOf("#c40003"));
        // pawn.setOnMouseReleased(e -> {
        //     int newX = toBoard(e.getSceneX());
        //     int newY = toBoard(e.getSceneY());
        //     System.out.println("X: " + newX + " Y: " + newY);
        //     System.out.println(tablica[newX][newY].jakiKolor());
        //     if(tablica[newX][newY].jakiKolor() == kolorKafelka.CIEMNY)
        //         pawn.move(newX, newY);
        //     else pawn.abortMove();
        // });

        /* 
         * Tworzenie planszy 
         */
        for( int i = 0; i <  width; i++){
            for( int j = 0; j < width; j++){
                Kafelek kafelek = new Kafelek(i, j, bol);
                pane.getChildren().add(kafelek);
                tablica[i][j] = kafelek;

            }
        }
        /*
         * Dodawanie pionkow
         */
        for( int i = 7; i >= 0; i--){
            for( int j = 7; j >= 0; j--){
                if((tablica[i][j].jakiKolor() == kolorKafelka.CIEMNY && j < 3)){

                    Pawn pionek = new Pawn(i*100 + 50, j * 100 + 50, 25);
                    tabPawns[i][j] = pionek;
                    //System.out.println(tabPawns[i][j]);
                    //System.out.println(i + " " + j);
                    tablica[i][j].addPawn();
                    //System.out.println(pionek);
                    pionek.setColor(Color.valueOf("#c40003"));

                    pane.getChildren().add(pionek);

                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/100, (int)pionek.oldY/100, newX, newY);
                        pionek.abortMove();
                        //System.out.println(pionek.oldX + " " + pionek.oldY);
                        //System.out.println("X: " + newX + " Y: " + newY);
                        // System.out.println(tablica[newX][newY].jakiKolor());
                    });
                }
                else if((tablica[i][j].jakiKolor() == kolorKafelka.CIEMNY && j > 4 )){
                    Pawn pionek = new Pawn(i*100 + 50, j * 100 + 50, 25);
                    tabPawns[i][j] = pionek;
                    pionek.setColor(Color.valueOf("#fff9f4"));

                    pane.getChildren().add(pionek);
                    tablica[i][j].addPawn();
                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/100, (int)pionek.oldY/100, newX, newY);
                        pionek.abortMove();
                    });
                }

            }
        }
        //pane.getChildren().add(pawn);
        //tabPawns[1][2].move(2,3);
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
        tabPawns[x][y].move(1000, 1000);
        tabPawns[x][y] = null;
    }


}
