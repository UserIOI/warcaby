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

    /*
     * Zmienne do tworzenia planszy
     */
    static boolean bicieDoTylu;
    static boolean kolorRogu;
    static int boardSize;

    /* 
     * Tu jakas funkcja do ustawiania zmiennych do gry 
     */
    public static void setRules(boolean bicie, boolean kolor, int size){
        bicieDoTylu = bicie;
        kolorRogu = kolor;
        boardSize = size;
    }

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
        out.println("MOVE" + oldX + oldY + newX + newY);
    }

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());  
        out = new PrintWriter(socket.getOutputStream(), true);
        String rules = this.getRules(in,out);
        System.out.println(rules);
        System.out.println(Integer.parseInt(rules.substring(0,2)));
        
        if(Integer.parseInt(rules.substring(2,3 )) == 0) kolorRogu = false;
        else kolorRogu = true;

        setRules(false, kolorRogu, Integer.parseInt(rules.substring(0,2)));
        startApp(this, bicieDoTylu, kolorRogu, boardSize);

    }

    public String getRules(Scanner in,PrintWriter out){
        int count = 0;
        int types;
        Scanner terminal = new Scanner(System.in);

        types = Integer.parseInt(in.nextLine());
        if(types!=0) {
            System.out.println("istnieje " + types + " tyle typów gry");
            while (count < types) {
                System.out.println(in.nextLine());
                count++;
            }
            System.out.println("Który typ gry chcesz wybrac: ");
            out.println(terminal.nextLine());
        }
        return in.nextLine();
    }

    public void startApp(Client client, boolean bicieDoTylu, boolean kolorRogu, int boardSize){
        App app = new App();
        app.mainCall(client, bicieDoTylu, kolorRogu, boardSize);
    }


    public static void main(String[] args) throws Exception{

        setRules(false, true, 8);
        Client client = new Client("localhost");
    }
}