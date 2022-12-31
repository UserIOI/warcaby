package warcaby;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {

    public static String string;
    public int oldX ,oldY, newX, newY;
    private Socket socket;
    public Scanner in = new Scanner(System.in);
    private PrintWriter out;

    private boolean biciedotylu = false;
    private int boardSize = 8;

    public String waitForServer(){
        var response = in.nextLine();
        System.out.println(response);
        return response;
    }

    public void pushToServer(int oldX, int oldY, int newX, int newY){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        //System.out.println(" z pushToServer Client " + oldX +" "+ oldY +" "+ newX +" "+ newY);
        out.println("MOVE" + oldX + oldY + newX + newY);
        //var response = in.nextLine();
        //System.out.println(response);
    }

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        
        startApp(this /* boolean biciedotylu, int boardSize */);

    }

    public void startApp(Client client /* boolean biciedotylu, int boardSize */){
        App app = new App();
        app.mainCall(client /* boolean biciedotylu, int boardSize */);
    }


    public static void main(String[] args) throws Exception{

        Client client = new Client("localhost");

        //powinien wpisywac w jaka wersje chce grac client
        //jakis scanner i potem w startApp dodajemy boolean czy moze bic do tylu czy nie i rozmiar planszy
    }
}