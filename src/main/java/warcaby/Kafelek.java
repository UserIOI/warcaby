package warcaby;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Kafelek extends Rectangle{

    
    public Kafelek(int x, int y, Boolean bol){
        setWidth(100);
        setHeight(100);

        relocate(x * 100, y * 100);

        if(bol){
            if( (x + y) %2 == 0){
                setFill(Color.valueOf("#feb"));
            }
            else setFill(Color.valueOf("#582"));
        }else {
            if( (x + y) %2 != 0){
                setFill(Color.valueOf("#feb"));
            }
            else setFill(Color.valueOf("#582"));
        }
    }
}
