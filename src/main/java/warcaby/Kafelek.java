package warcaby;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Kafelek extends Rectangle{

    enum kolorKafelka{
        JASNY,
        CIEMNY
    }

    int x, y;
    kolorKafelka jaki;

    /**
     * konstruktor dziedziczymy po klasie Rectangle i tworzymy kafelek ktory znajduje sie w miejscu x,y 
     * @param x
     * @param y
     * @param bol mowi nam czy dany kafelek w zmiennych parzystych powinien byc jasny czy ciemny
     * @param size mowi nam o rozmarze kafelka
     */
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

    /**
     * Metoda zwracajaca kolor kafelka
     * @return
     */
    public kolorKafelka jakiKolor(){
        return jaki;
    }

}
