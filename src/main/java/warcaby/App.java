package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class App extends Application{

    public void start(Stage stage) throws Exception{
        stage.setTitle("Warcaby");

        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
