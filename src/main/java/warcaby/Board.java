package warcaby;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

public class Board {
    
    /* 
     * width - szerokosc planszy jako ilosc pol
     * bol - jesli true to rog jest ciemny, jesli false to rog jest jasny
    */
    public BorderPane createBoard(int width, Boolean bol){
        BorderPane root = new BorderPane();
        root.setPrefSize(width * 100, width * 100);

        ToolBar toolbar = new ToolBar( new Button("Poddaj sie"), new Button("Remis"));
        // Button Poddajbutton = new Button("Poddaj sie");
        // Button remisButton = new Button("Remis");

        root.setBottom(toolbar);

        //GridPane grid = new GridPane();

        Pane pane = new Pane();
        root.setCenter(pane);
        Pawn pawn = new Pawn(0,0,20,pane);


        for( int i = 0; i <  width; i++){
            for( int j = 0; j < width; j++){
                Kafelek kafelek = new Kafelek(i, j, bol);
                //grid.add(kafelek, i, j);
                pane.getChildren().add(kafelek);

            }
        }
        pane.getChildren().add(pawn);
        return root;
    }
}
