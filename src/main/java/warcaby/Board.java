package warcaby;

import javafx.scene.paint.Color;
import warcaby.Kafelek.kolorKafelka;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;



public class Board implements Runnable {

    public static int kafelekSize;

    BorderPane root = new BorderPane();

    boolean biciedotylu = false;

    Pawn[][] tabPawns;

    Client client;

    Pane pane = new Pane();

    Kafelek[][] tablica;

    /**
     * Metoda zamieniajaca koordynaty myszki na koordynaty planszy
     * @param pixel
     * @return
     */
    private int toBoard(double pixel){
        return (int)((pixel)/kafelekSize);
    }

    /**
     * Konstruktor planszy 
     * @param width szerokosc planszy jako ilosc pol
     * @param bol jesli true to lewy dolny rog jest ciemny, jesli false to rog jest jasny
     * @param cl client
     * @param biciedotylu jesli true to mozna bic do tylu 
     */
    public Board(int width, Boolean bol, Client cl, boolean biciedotylu){
        kafelekSize = 800/width - 20;
        tablica = new Kafelek[width][width];
        tabPawns = new Pawn[width][width];
        this.biciedotylu = biciedotylu;
        Thread watek = new Thread(this);
        watek.start();
        System.out.println();
        root.setPrefSize(width * kafelekSize, width * kafelekSize);

        client = cl;

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
                    pionek.setColor(Color.valueOf("#c40003")); //czerwony

                    pane.getChildren().add(pionek);

                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/kafelekSize, (int)pionek.oldY/kafelekSize, newX, newY);
                        //pionek.setQueen();
                        pionek.abortMove();
                    });
                }
                else if((tablica[i][j].jakiKolor() == kolorKafelka.CIEMNY && j > width/2 )){ 
                    Pawn pionek = new Pawn(i*kafelekSize + kafelekSize/2, j * kafelekSize + kafelekSize/2, kafelekSize/4);
                    tabPawns[i][j] = pionek;
                    pionek.setColor(Color.valueOf("#fff9f4")); //bialy

                    pane.getChildren().add(pionek);
                    //tablica[i][j].addPawn();
                    pionek.setOnMouseReleased(e -> {
                        int newX = toBoard(e.getSceneX());
                        int newY = toBoard(e.getSceneY());
                        cl.pushToServer((int)pionek.oldX/kafelekSize, (int)pionek.oldY/kafelekSize, newX, newY);
                        //pionek.setQueen();
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
                /* 
                 * if(odp.startsWith("DAMA"))
                 * addQueen(x,y);
                 */
                if(odp.startsWith("DAMA")){
                    System.out.println("dama");
                    addQueen(Integer.parseInt(odp.substring(4, 5)), Integer.parseInt(odp.substring(5, 6)));
                }
            }
        }
    }

    /**
     * Metoda przemieszczajaca pionek
     * @param oldX
     * @param oldY
     * @param newX
     * @param newY
     */
    public void movepionek(int oldX, int oldY, int newX, int newY){
        tabPawns[oldX][oldY].move(newX, newY);
        tabPawns[newX][newY] = tabPawns[oldX][oldY];
        tabPawns[oldX][oldY] = null;
    }

    /**
     * Metoda odpowiadajaca za usuniecie pionka z planszy
     * @param x
     * @param y
     */
    public void killpionek(int x, int y){
        tabPawns[x][y].move(50, 50);
        tabPawns[x][y] = null;
    }

    /**
     * Metoda odpowiadajaca za zamienienie pionka na damke
     * @param x
     * @param y
     */
    public void addQueen(int x, int y){
        tabPawns[x][y].setQueen();
    }

}
