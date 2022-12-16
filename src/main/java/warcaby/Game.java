package warcaby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Player currentPlayer;
    int[][] board = new int[7][7];
    public Game(){
        for(int i = 0;i<8;i++){
            for(int j = 0; j<8; j++){
                board[i][j] = 0;
                if((j<3)&&((i+j)%2==1)){
                    board[i][j] = 2;//RED PAWNS
                }
                if((j>4)&&((i+j)%2==1)){
                    board[i][j] = 1;//WHITE PAWNS
                }
            }
        }
    }
    public class Player implements Runnable{
        int red;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        public Player(Socket socket,int type) throws IOException {
            this.socket = socket;
            this.red = type;
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(),true);
            if(type == 1){
                currentPlayer = this;
            }else{
                opponent = currentPlayer;
                opponent.opponent = this;
            }
        }
        @Override
        public void run(){

        }
    }
}
