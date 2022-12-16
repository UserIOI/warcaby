package warcaby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Player currentPlayer;
    int[][] board = new int[8][8];

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
    public synchronized boolean move(Player player, int oldX, int oldY, int newX, int newY) {
        if (player != currentPlayer) {
            return false;
        } else if (player.opponent == null) {
            return false;
        } else if ((newX + newY) % 2 == 0) {
            return false;
        } else if (board[oldX][oldY] != player.red) {
            return false;
        } else if (board[newX][newY] == board[oldX][oldY]) {
            return false;
        }
        currentPlayer = currentPlayer.opponent;
        return true;
    }

    public class Player implements Runnable {
        int red;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, int type) throws IOException {
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
                //System.out.println("hasnextline");
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVE")) {
                    //System.out.println("processmovecommand");
                    processMoveCommand(Integer.parseInt(command.substring(4, 5)), Integer.parseInt(command.substring(5, 6)), Integer.parseInt(command.substring(6, 7)), Integer.parseInt(command.substring(7, 8)));
                }
            }
        }

        private void processMoveCommand(int oldX, int oldY, int newX, int newY) {

            if (move(this, oldX, oldY, newX, newY)) {
                output.println("MOVE"+oldX+oldY+newX+newY);
                opponent.output.println("MOVE"+oldX+oldY+newX+newY);
                System.out.println("move");
            }
        }
    }
}
