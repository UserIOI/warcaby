package warcaby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Player currentPlayer;
    int[][] board = new int[8][8];
    Boolean kill = false;
    int killx, killy;

    public Game() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
                if ((j < 3) && ((i + j) % 2 == 1)) {
                    board[i][j] = 2;//RED PAWNS
                }
                if ((j > 4) && ((i + j) % 2 == 1)) {
                    board[i][j] = 1;//WHITE PAWNS
                }
            }
        }
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
        if (player != currentPlayer) {
            System.out.println("player != currentPlayer");
            return false;
        } else if (player.opponent == null) {
            System.out.println("player.opponent == null");
            return false;
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
        } else if(player.kierunek == -1 && oldY - newY < 0){
            System.out.println("kierunek -1");
            return false;
        } else if(player.kierunek == 1 && oldY - newY > 0){
            System.out.println("kierunek 1");
            return false;
        } else if(Math.abs(oldX - newX) == 2 && Math.abs(oldY - newY) == 2  ){
            System.out.println("oldX - newX) == 2 && Math.abs(oldY - newY) == 2");
            if( oldX > newX && player.kierunek == -1){
                if(board[oldX-1][oldY-1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 1 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }
            else if( oldX < newX && player.kierunek == -1){
                if(board[oldX+1][oldY-1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY-1;
                    System.out.println("kill = true, 2 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }
            else if( oldX > newX && player.kierunek == 1){
                if(board[oldX-1][oldY+1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX-1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 2 if");
                    currentPlayer = currentPlayer.opponent;
                    return true;
                }
            }else if( oldX < newX && player.kierunek == 1){
                if(board[oldX+1][oldY+1] == player.opponent.red){
                    this.kill = true;
                    this.killx = oldX+1;
                    this.killy = oldY+1;
                    System.out.println("kill = true, 2 if");
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
                board[newX][newY] = board[oldX][oldY];
                board[oldX][oldY] = 0;
                output.println("MOVE"+oldX+oldY+newX+newY);
                opponent.output.println("MOVE"+oldX+oldY+newX+newY);
                System.out.println("move");
            }
            if(kill){
                board[killx][killy] = 0;
                output.println("KILL"+killx+killy);
                opponent.output.println("KILL"+killx+killy);
                kill = false;
            }
        }
    }
}
