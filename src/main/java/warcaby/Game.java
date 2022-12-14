package warcaby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    private Game thisGame = this;

    private String rules;

    boolean rulesSetted;
    Player currentPlayer;
    int[][] board;
    Boolean kill = false;
    Boolean biciewtyl = false;
    Boolean edgeColor;
    int killx, killy;
    int wynik, wynikMax, globalMax;
    /*
     * tu serwer wrzuci jaka ma miec dlugosc plansza
     */
    int width;
    int[][] pomBoard;

    public void Start(int width, boolean edgeColor) {
        this.width = width;
        this.edgeColor = edgeColor;
        board = new int[width][width];
        pomBoard = new int[width][width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
                if ((j < width/2 - 1) && ((i + j) % 2 == 1)) {
                    board[i][j] = 2;//RED PAWNS
                    // board[i][j] = 22 -> RED QUEEN
                }
                if ((j > width/2) && ((i + j) % 2 == 1)) {
                    board[i][j] = 1;//WHITE PAWNS
                    // board[i][j] = 11 -> WHITE QUEEN
                }
            }
        }
        int[][] pomBoard = new int[width][width];
    }

    //    public boolean check(int x,int y){
    //      if
    // }
    // public synchronized boolean move(Player player, int oldX, int oldY, int newX, int newY) {
    //     if (player != currentPlayer) {
    //         return false;
    //     } else if (player.opponent == null) {
    //         return false;
    //     } else if ((newX + newY) % 2 == 0) {
    //         return false;
    //     } else if (board[oldX][oldY] != player.red) {
    //         return false;
    //     } else if (board[newX][newY] == board[oldX][oldY]) {
    //         return false;
    //     }
    //     currentPlayer = currentPlayer.opponent;
    //     return true;
    // }


    public synchronized boolean move(Player player, int oldX, int oldY, int newX, int newY) {



        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                pomBoard[i][j] = 0;
            }
        }

        globalMax = 0;
        /*
         * sprawdzic najpierw czy pionek jest dama czy nie - 1 or 11 2 or 22
         */
        allKill(player);

        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                System.out.print(pomBoard[j][i] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(pomBoard[oldX][oldY]);

        if (player != currentPlayer) {
            System.out.println("player != currentPlayer");
            return false;
        } else if (player.opponent == null) {
            System.out.println("player.opponent == null");
            return false;
        // } else if (pomBoard[oldX][oldY] != globalMax){
        //     System.out.println("Pionek nie ma maksymalnej ilosci bic");
        //     System.out.println(pomBoard[oldX][oldY] + " " + globalMax);
        //     return false;
        } else if ((newX + newY) % 2 == 0) {
            System.out.println("(newX + newY) % 2 == 0");
            return false;
        } else if (board[oldX][oldY] != player.red) {
            System.out.println("board[oldX][oldY] != player.red");
            return false;
        } else if (board[newX][newY] == board[oldX][oldY]) {
            System.out.println("board[newX][newY] == board[oldX][oldY]");
            return false;
        } else if(oldX == newX || oldY == newY){
            System.out.println("oldX == newX || oldY == newY");
            return false;
        } else if(player.kierunek == -1 && oldY - newY < 0){ // can kill do tylu ?
            System.out.println("kierunek -1");
            return false;
        } else if(player.kierunek == 1 && oldY - newY > 0){ // can kill do tylu ?
            System.out.println("kierunek 1");
            return false;
        }else if(Math.abs(oldX - newX) > 2 || Math.abs(oldY - newY) > 2  ){
            System.out.println("ponad 2 w ruchu");
            return false;
        } else if(Math.abs(oldX - newX) == 2 && Math.abs(oldY - newY) == 2  ){
            System.out.println("oldX - newX) == 2 && Math.abs(oldY - newY) == 2");
            if( oldX > newX && player.kierunek == -1){ //player type wystarczy i guess
                if(board[oldX-1][oldY-1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 1 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }
            else if( oldX < newX && player.kierunek == -1){ //player type wystarczy i guess
                if(board[oldX+1][oldY-1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 2 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }
            else if( oldX > newX && player.kierunek == 1){ //player type wystarczy i guess
                if(board[oldX-1][oldY+1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 3 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }else if( oldX < newX && player.kierunek == 1){ //player type wystarczy i guess
                if(board[oldX+1][oldY+1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 4 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }
            return false;
        } 
        
        currentPlayer = currentPlayer.opponent;
        return true;
    }
    

    public class Player implements Runnable {
        String gameType;
        int red;
        int kierunek;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, int type) throws IOException {
            if(type == 1){
                this.kierunek = -1;
            }
            else this.kierunek = 1;
            System.out.println("konstruktor player");
            this.socket = socket;
            this.red = type;
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            if (type == 1) {
                currentPlayer = this;
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
            }
            if(!rulesSetted) {
                output.println("3");
                output.println("niemieckie");
                output.println("polskie");
                output.println("kanadyjskie");
                gameType = input.nextLine();
                System.out.println(gameType);
                if (gameType.equals("niemieckie")) {
                    thisGame.Start(8, true);
                    rules = "081";
                    output.println("081");
                }
                if (gameType.equals("polskie")) {
                    thisGame.Start(10, true);
                    rules = "101";
                    output.println("101");
                }
                if (gameType.equals("kanadyjskie")) {
                    thisGame.Start(12, true);
                    rules = "101";
                    output.println("121");
                }
                rulesSetted = true;
            }else{
                output.println("0");
                output.println(rules);
            }
        }

        @Override
        public void run() {
            while(true) {
                processCommands();
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                System.out.println(command);
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVE")) {
                    processMoveCommand(Integer.parseInt(command.substring(4, 5)), Integer.parseInt(command.substring(5, 6)), Integer.parseInt(command.substring(6, 7)), Integer.parseInt(command.substring(7, 8)));
                } 
            }
        }

        private void processMoveCommand(int oldX, int oldY, int newX, int newY) {

            

            if (move(this, oldX, oldY, newX, newY)) {
                //wynik = 0;
                //System.out.println(maxKill(oldX, oldY, this));
                board[newX][newY] = board[oldX][oldY];
                board[oldX][oldY] = 0;
                output.println("MOVE"+oldX+oldY+newX+newY);
                opponent.output.println("MOVE"+oldX+oldY+newX+newY);
                System.out.println("move");
                
                /*
                 * tu sprawdzamy czy jest damka i wysylamy do clienta DAMKA x y
                 * board[newX][newY] = 20 or 10 w zaleznosci czy board[newX][newY] = 1 or 2
                 */
                if(kierunek == -1 && newY == 0){ //white
                    output.println("DAMA"+newX+newY);
                    opponent.output.println("DAMA"+newX+newY);
                    board[newX][newY] = 10;
                }
                else if(kierunek == 1 && newY == 7){ //red
                    output.println("DAMA"+newX+newY);
                    opponent.output.println("DAMA"+newX+newY);
                    board[newX][newY] = 20;
                }
            }
            if(kill){
                board[killx][killy] = 0;
                output.println("KILL"+killx+killy);
                opponent.output.println("KILL"+killx+killy);
                kill = false;
            }
        }

    }
        /*
         * Funkcja wywolujaca liczenie najwiekszej mozliwej ilosci zbic dla kazdego pionka danego gracza
         */
        private void allKill(Player player){

            for(int i = 0; i < width; i++){
                for(int j = 0; j < width; j++){
                    if(board[i][j] == player.red){
                        pomBoard[i][j] = maxKill(i, j, player, 0);
                        wynik = 0;
                        wynikMax = 0;
                        globalMax = Math.max(pomBoard[i][j], globalMax);
                    }
                }
            }
        }


        /* kierunki a (sk??d przyszli??my rekurencyjnie aby nie sprawdzac drogi w ktorej bylismy przed chwila)
         *  1   2
         *    x
         *  3   4
         */

        /*
         * Liczenie rekurencyjne najwiekszej liczby pionkow mozliwych do zbicia
         */
            private int maxKill(int x, int y, Player player, int a){ 
            if(x-2 >= 0 && x-2 <= width-1 && y-2 >= 0 && y-2 <= width-1)
                if(board[x-1][y-1] == player.opponent.red && a != 1){
                    if(board[x-2][y-2] == 0){
                        wynik++;
                        System.out.println("1 if");
                        maxKill(x-2, y-2, player, 4);
                    }
                }
            if(x+2 >= 0 && x+2 <= width-1 && y-2 >= 0 && y-2 <= width-1)
                if(board[x+1][y-1] == player.opponent.red && a != 2){
                    if(board[x+2][y-2] == 0){
                        wynik++;
                        System.out.println("2 if");
                        maxKill(x+2, y-2, player, 3);
                    }
                }
            if(x+2 >= 0 && x+2 <= width-1 && y+2 >= 0 && y+2 <= width-1)
                if(board[x+1][y+1] == player.opponent.red && a != 4){
                    if(board[x+2][y+2] == 0){
                        wynik++;
                        System.out.println("3 if");
                        maxKill(x+2, y+2, player, 1);
                    }
                }
            if(x-2 >= 0 && x-2 <= width-1 && y+2 >= 0 && y+2 <= width-1)
                if(board[x-1][y+1] == player.opponent.red && a != 3){
                    if(board[x-2][y+2] == 0){
                        wynik++;
                        System.out.println("4 if");
                        maxKill(x-2, y-2, player, 2);
                    }
                }

                wynikMax = Math.max(wynikMax, wynik);
                wynik--;
                return wynikMax;
   }


//    /*
//              * Liczenie bic dla wszystkich pionkow naszych i przeciwnika
//              */
//             allKill(this);
//             allKill(this.opponent);

//             /*
//              * Wypisanie wynikow 
//              */
//             for(int i = 0; i < width; i++){
//                 for(int j = 0; j < width; j++){
//                     System.out.print(pomBoard[j][i] + " ");
//                 }
//                 System.out.println(" ");
//             }


//             /*
//              * Czyszczenie tablicy wynikow
//              */
//             for(int i = 0; i < width; i++){
//                 for(int j = 0; j < width; j++){
//                     pomBoard[i][j] = 0;
//                 }
//             }
}
