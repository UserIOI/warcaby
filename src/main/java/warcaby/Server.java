package warcaby;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server {

    int[][] ustawienie = new int[7][7];
    private static ServerSocket server;

    public static void main(String[] args) throws Exception {

        try{

            ServerSocket server=new ServerSocket(6666);
            Socket socket=server.accept();//establishes connection
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            String  str=(String)dis.readUTF();
            System.out.println("message = "+str);
            server.close();
        }catch(Exception e){System.out.println(e);}
    }
}