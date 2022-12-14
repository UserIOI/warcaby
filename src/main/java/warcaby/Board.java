package warcaby;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Board {
    
    /* 
     * width - szerokosc planszy jako ilosc pol
    */
    public BorderPane createBoard(int width){
        BorderPane root = new BorderPane();
        root.setPrefSize(width * 100, width * 100);

        GridPane grid = new GridPane();
        root.setCenter(grid);


        for( int i = 0; i <  width; i++){
            for( int j = 0; j < width; j++){
                // area 
            }
        }


        

        //ruch
        
        return root;
    }
}
