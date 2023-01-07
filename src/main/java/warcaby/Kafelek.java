package warcaby;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Kafelek extends Rectangle{

    enum kolorKafelka{
        JASNY,
        CIEMNY
    }

    int x,y;
    kolorKafelka jaki;
    //Boolean haspawn = false;


    public Kafelek(int x, int y, Boolean bol, int size){
        this.x = x;
        this.y = y;

        setWidth(size);
        setHeight(size);

        relocate(x * size, y * size);

        if(bol){
            if( (x + y) %2 == 0){
                setFill(Color.valueOf("#feb"));
                jaki = kolorKafelka.JASNY;
            }
            else {
                setFill(Color.valueOf("#582"));
                jaki = kolorKafelka.CIEMNY;
            }
        }else {
            if( (x + y) %2 != 0){
                setFill(Color.valueOf("#feb"));
                jaki = kolorKafelka.JASNY;
            }
            else {
                setFill(Color.valueOf("#582"));
                jaki = kolorKafelka.CIEMNY;
            }
        }
    }

    public kolorKafelka jakiKolor(){
        return jaki;
    }

    // public Boolean hasPawn(){
    //     return haspawn;
    // }

    // public void addPawn(){
    //     this.haspawn = true;
    // }

    // public void deletePawn(){
    //     this.haspawn = false;
    // }
}
