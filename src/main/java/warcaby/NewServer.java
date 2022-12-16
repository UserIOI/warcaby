package warcaby;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class NewServer {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Checkers Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(),1));
                pool.execute(game.new Player(listener.accept(), 2));
            }
        }
    }
}
