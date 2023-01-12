package warcaby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    private Game thisGame = this;

    private String rules;

    boolean rulesSetted;
    boolean flag = false;
    int flagx, flagy;
    Player currentPlayer;
    int[][] board;
    Boolean kill = false;
    Boolean biciewtyl;
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
    }


    /*
     * Funkcja odpowiadajaca za sprawdzanie poprawnosci ruchu gracza
     */
    public synchronized boolean move(Player player, int oldX, int oldY, int newX, int newY) {


        /*
         * sprawdzamy flage dotyczaca czy pionek mial wielokrotne bicie czy nie
         */
        if(!flag){
            for(int i = 0; i < width; i++){
                for(int j = 0; j < width; j++){
                    pomBoard[i][j] = 0;
                }
            }
    
            globalMax = 0;
    
            allKill(player);
        }

        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                System.out.print(pomBoard[j][i] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(pomBoard[oldX][oldY]);

        //System.out.println("pusty commit");

        /*
         * Logika bez bic
         */
        if (player != currentPlayer) {
            System.out.println("player != currentPlayer");
            return false;
        } else if (player.opponent == null) {
            System.out.println("player.opponent == null");
            return false;
        } else if (pomBoard[oldX][oldY] != globalMax && globalMax != 0){
            System.out.println("Pionek nie ma maksymalnej ilosci bic"); 
            System.out.println(pomBoard[oldX][oldY] + " " + globalMax);
            return false;
        } else if ((newX + newY) % 2 == 0 && edgeColor || (newX + newY) % 2 != 0 && !edgeColor) { //zalezne od booleana 
            System.out.println("(newX + newY) % 2 == 0");
            return false;
        } else if (board[oldX][oldY] != player.red && board[oldX][oldY] != player.red * 10) {
            System.out.println("board[oldX][oldY] != player.red");
            return false;
        } else if (board[newX][newY] == board[oldX][oldY]) {
            System.out.println("board[newX][newY] == board[oldX][oldY]");
            return false;
        } else if(oldX == newX || oldY == newY){
            System.out.println("oldX == newX || oldY == newY");
            return false;
        } else if((Math.abs(oldX - newX) > 2 && board[oldX][oldY] < 3 ) || (Math.abs(oldY - newY) > 2 && board[oldX][oldY] < 3 )){
            System.out.println("ponad 2 w ruchu dla pionka");
            return false;
        } else if(board[newX][newY] != 0 ){
            System.out.println("miejsce zajete");
            return false;
        }

        /*
         * logika do bic pionkami
         * 
         * W ktora strone bijemy
         *  1   2
         *    x
         *  3   4
         */
        if(pomBoard[oldX][oldY] == globalMax && board[oldX][oldY] == player.red){
            if(Math.abs(oldX - newX) == 2 || Math.abs(oldY - newY) == 2  ){
                if(flag){
                    if(oldX != flagx && oldY != flagy ){
                        System.out.println("zly pionek");
                        return false;
                    }

                }
                if(newX < oldX && newY < oldY){     /* 1 */
                    if((board[oldX-1][oldY-1] != player.opponent.red) && (board[oldX-1][oldY-1] != player.opponent.red * 10))
                        return false;
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 1 if");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    System.out.println("globalMax : " + globalMax);
                    return true;
                } else if(newX > oldX && newY < oldY){    /* 2 */
                    if((board[oldX+1][oldY-1] != player.opponent.red) && (board[oldX+1][oldY-1] != player.opponent.red * 10))
                        return false;
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 2 if");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    System.out.println("globalMax : " + globalMax);
                    return true;
                } else if(newX < oldX && newY > oldY){    /* 3 */
                    if((board[oldX-1][oldY+1] != player.opponent.red) && (board[oldX-1][oldY+1] != player.opponent.red * 10))
                        return false;
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 3 if");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    System.out.println("globalMax : " + globalMax);
                    return true;
                } else if(newX > oldX && newY > oldY){    /* 4 */
                    if((board[oldX+1][oldY+1] != player.opponent.red) && (board[oldX+1][oldY+1] != player.opponent.red * 10))
                        return false;
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 4 if");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    System.out.println("globalMax : " + globalMax);
                    return true;
                }
                
            } 
        }
        
        /*
         * logika do bic damka
         */
        if(pomBoard[oldX][oldY] == globalMax && board[oldX][oldY] == player.red * 10){
            if(flag){
                if(oldX != flagx && oldY != flagy ){
                    System.out.println("zly pionek");
                    return false;
                }
            }

            if(newX < oldX && newY < oldY){     /* 1 */
                System.out.println("dama 1 if kila");
                if(board[newX+1][newY+1] == player.opponent.red && board[newX][newY] == 0 && pionkiPoDrodze(oldX, oldY, newX, newY, player, 1) == 1){
                    this.kill = true;
                    this.killx = newX+1;
                    this.killy = newY+1;
                    System.out.println("kill = true, 1 if DAMKA");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    return true;
                }
                return false;
            } else if(newX > oldX && newY < oldY){    /* 2 */
                System.out.println("dama 2 if kila");
                if(board[newX-1][newY+1] == player.opponent.red && board[newX][newY] == 0 && pionkiPoDrodze(oldX, oldY, newX, newY, player, 2) == 1){
                    this.kill = true;
                    this.killx = newX-1;
                    this.killy = newY+1;
                    System.out.println("kill = true, 2 if DAMKA");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    return true;
                }
                return false;
            } else if(newX < oldX && newY > oldY){    /* 3 */
                System.out.println("dama 3 if kila");
                System.out.println(board[newX+1][newY-1] + " " + board[newX][newY]);
                System.out.println("pionki po drodze : " + pionkiPoDrodze(oldX, oldY, newX, newY, player, 3));
                if(board[newX+1][newY-1] == player.opponent.red && board[newX][newY] == 0 && pionkiPoDrodze(oldX, oldY, newX, newY, player, 3) == 1){
                    this.kill = true;
                    this.killx = newX+1;
                    this.killy = newY-1;
                    System.out.println("kill = true, 3 if DAMKA");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    return true;
                }
                return false;
            } else if(newX > oldX && newY > oldY){    /* 4 */
                System.out.println("dama 4 if kila");
                if(board[newX-1][newY-1] == player.opponent.red && board[newX][newY] == 0 && pionkiPoDrodze(oldX, oldY, newX, newY, player, 4) == 1){
                    this.kill = true;
                    this.killx = newX-1;
                    this.killy = newY-1;
                    System.out.println("kill = true, 4 if DAMKA");
                    globalMax--;
                    if(globalMax == 0){
                        currentPlayer = currentPlayer.opponent;
                        flag = false;
                    } else {
                        pomBoard[newX][newY] = globalMax;
                        flag = true;
                        flagx = newX;
                        flagy = newY;
                    }
                    return true;
                }
                return false;
            }
        }
        
        /*
         * ruch damka bez bicia
         */
        if((Math.abs(oldX - newX) > 2 && board[oldX][oldY] > 3 ) || (Math.abs(oldY - newY) > 2 && board[oldX][oldY] > 3 )){
            System.out.println("ruch damka");
            if(Math.abs(oldX - newX) != Math.abs(oldY - newY)) {
                System.out.println("nie ten sam rzad");
                return false;
            }
            currentPlayer = currentPlayer.opponent;
            return true;
        }

        /*
         * Poruszanie sie do tylu bez bicia
         */
        if(Math.abs(oldX - newX) == 1 || Math.abs(oldY - newY) == 1  ){
            if(player.kierunek == -1 && oldY - newY < 0){ 
                System.out.println("kierunek -1");
                return false;
            } else if(player.kierunek == 1 && oldY - newY > 0){ 
                System.out.println("kierunek 1");
                return false;
            }
        }
        if(Math.abs(oldX - newX) == 2 || Math.abs(oldY - newY) == 2  ){
            System.out.println("2 w ruchu bez bicia");
            return false;
        }

        /*
         * Jesli masz bicie a nie bijesz
         */
        if(pomBoard[oldX][oldY] == globalMax && globalMax > 0){
            System.out.println("musisz bic !!!");
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
                    rules = "121";
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
                    if(board[i][j] == player.red * 10){
                        pomBoard[i][j] = maxKillDamki(i, j, player, 0);
                        wynik = 0;
                        wynikMax = 0;
                        globalMax = Math.max(pomBoard[i][j], globalMax);
                    }
                }
            }
        }


        /* kierunki a (skąd przyszliśmy rekurencyjnie aby nie sprawdzac drogi w ktorej bylismy przed chwila)
         *  1   2
         *    x
         *  3   4
         */
        /*
         * Liczenie rekurencyjne najwiekszej liczby pionkow mozliwych do zbicia dla pionkow
         */

        private int maxKill(int x, int y, Player player, int a){ 
            if(x-2 >= 0 && x-2 <= width-1 && y-2 >= 0 && y-2 <= width-1)
                if((board[x-1][y-1] == player.opponent.red  && a != 1 ) || (board[x-1][y-1] == player.opponent.red * 10  && a != 1 )){
                    if(board[x-2][y-2] == 0){
                        wynik++;
                        System.out.println("1 if");
                        maxKill(x-2, y-2, player, 4);
                    }
                }
            if(x+2 >= 0 && x+2 <= width-1 && y-2 >= 0 && y-2 <= width-1)
                if((board[x+1][y-1] == player.opponent.red  && a != 2 ) || (board[x+1][y-1] == player.opponent.red * 10  && a != 2 )){
                    if(board[x+2][y-2] == 0){
                        wynik++;
                        System.out.println("2 if");
                        maxKill(x+2, y-2, player, 3);
                    }
                }
            if(x+2 >= 0 && x+2 <= width-1 && y+2 >= 0 && y+2 <= width-1)
                if((board[x+1][y+1] == player.opponent.red  && a != 4 ) || (board[x+1][y+1] == player.opponent.red * 10  && a != 4 )){
                    if(board[x+2][y+2] == 0){
                        wynik++;
                        System.out.println("3 if");
                        maxKill(x+2, y+2, player, 1);
                    }
                }
            if(x-2 >= 0 && x-2 <= width-1 && y+2 >= 0 && y+2 <= width-1)
                if((board[x-1][y+1] == player.opponent.red  && a != 3 ) || (board[x-1][y+1] == player.opponent.red * 10  && a != 3 )){
                    if(board[x-2][y+2] == 0){
                        wynik++;
                        System.out.println("4 if");
                        maxKill(x-2, y+2, player, 2);
                    }
                }

                wynikMax = Math.max(wynikMax, wynik);
                wynik--;
                return wynikMax;
   }


        /* kierunki a (skąd przyszliśmy rekurencyjnie aby nie sprawdzac drogi w ktorej bylismy przed chwila)
         *  1   2
         *    x
         *  3   4
         */
        /*
         * Liczenie rekurencyjne najwiekszej liczby pionkow mozliwych do zbicia dla damek
         */

        private int maxKillDamki(int xx, int yy, Player player, int kierunek){
            int minRem = 0;
            int x;
            int y;
            if(kierunek != 1){
                x = xx;
                y = yy;
                minRem = Math.min(x,y) - 1;
                while(minRem >= 0){
                    if(board[x][y] == player.opponent.red || board[x][y] == player.opponent.red * 10){
                        if(board[x-1][y-1] == 0){
                            wynik++;
                            maxKillDamki(x-1, y-1, player, 4);
                        } 
                    }
                    x--;
                    y--;
                    minRem--;
                }
            }
            if(kierunek != 2){
                x = xx;
                y = yy;
                minRem = Math.min(Math.abs(width-1 - x),y) -1;
                while(minRem >= 0){
                    if(board[x][y] == player.opponent.red || board[x][y] == player.opponent.red * 10){
                        if(board[x+1][y-1] == 0){
                            wynik++;
                            maxKillDamki(x+1, y-1, player, 3);
                        } 
                    }
                    x++;
                    y--;
                    minRem--;
                }
            }
            if(kierunek != 3){
                x = xx;
                y = yy;
                minRem = Math.min(x,Math.abs(width-1 - y)) -1;
                while(minRem >= 0){
                    if(board[x][y] == player.opponent.red || board[x][y] == player.opponent.red * 10){
                        if(board[x-1][y+1] == 0){
                            wynik++;
                            maxKillDamki(x-1, y+1, player, 2);
                        } 
                    }
                    x--;
                    y++;
                    minRem--;
                }
            } 
            if(kierunek != 4){
                x = xx;
                y = yy;
                minRem = Math.min(Math.abs(width-1 - x),Math.abs(width-1 - y)) -1;
                while(minRem >= 0){
                    if(board[x][y] == player.opponent.red || board[x][y] == player.opponent.red * 10){
                        if(board[x+1][y+1] == 0){
                            wynik++;
                            maxKillDamki(x+1, y+1, player, 1);
                        } 
                    }
                    x++;
                    y++;
                    minRem--;
                }
            }

            wynikMax = Math.max(wynikMax, wynik);
            wynik--;
            
            return wynikMax;
        }

        /*
         * Funkcja przechodzaca od punktu odlX,oldY do newX,newY i liczaca ile jest po drodze pionkow
         */
        private int pionkiPoDrodze(int oldX, int oldY, int newX, int newY, Player player, int kierunek){
            int odpowiedz = 0;
            int x = oldX;
            int y = oldY;

            if(kierunek == 1){
                x--;
                y--;
                while(true){
                    if(board[x][y]!=0)
                        odpowiedz++;
                    if(x == newX && y == newY)
                        break;

                    x--;
                    y--;
                }

                
            } else if(kierunek == 2){
                x++;
                y--;
                while(true){
                    if(board[x][y]!=0)
                        odpowiedz++;
                    if(x == newX && y == newY)
                        break;

                    x++;
                    y--;
                }

            } else if(kierunek == 3){
                x--;
                y++;
                while(true){
                    if(board[x][y]!=0){
                        odpowiedz++;
                        System.out.println(x + " " + y);
                    }
                    if(x == newX && y == newY)
                        break;

                    x--;
                    y++;
                }

            } else if(kierunek == 4){
                x++;
                y++;
                while(true){
                    if(board[x][y]!=0)
                        odpowiedz++;
                    if(x == newX && y == newY)
                        break;

                    x++;
                    y++;
                }
                
            }
            return odpowiedz;
        }
}
