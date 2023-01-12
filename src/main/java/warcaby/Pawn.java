package warcaby;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Pawn extends Circle{

    /** rozwijam klase Circle zeby stworzyc pionka i dodac jej funkcjonalnośći zwiazane z myszką
     * @see Circle
     */
    private Pane myBoard = new Pane();
    public boolean isQueen = false;
    double oldX;
    double oldY;
    double mouseX,mouseY;
    Color colorPawn;

    /** konstruktor dziedziczymy po klasie Circle */
    public Pawn(double x, double y, double radius) {
        super(x, y, radius);
        oldX = x - warcaby.Board.kafelekSize/4;
        oldY = y - warcaby.Board.kafelekSize/4;
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }


    /**  Metoda sprawdza czy najechalismy na figure */
    final public boolean isHit(double x, double y) {
        return getBoundsInLocal().contains(x,y);

    }

    /** Metoda zmienia wspolrzedna x srodka kólka */
    final public void addX(double x) {
        this.setCenterX(this.getCenterX() +x);
    }

    /** Metoda zmienia wspolrzedna y srodka kólka */
    final public void addY(double y) {
        this.setCenterY(this.getCenterY() + y);
    }

    /** Metoda zmienia dlugosc promienia kola */
    final public void addRadius(double r) {
        this.setRadius(this.getRadius() + r);
    }

    /**
     * Metoda ustawiajaca kolor pionka
     * @param color
     */
    public void setColor(Color color){
        setFill(color);
        colorPawn = color;
    }

    /**
     * Metoda odpowiadajaca za przemieszczanie pionka na miejsce x,y
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        oldX = (x * warcaby.Board.kafelekSize)+ warcaby.Board.kafelekSize/4;
        oldY = (y * warcaby.Board.kafelekSize)+ warcaby.Board.kafelekSize/4;
        relocate(oldX,oldY);
    }

    /** Metoda zatrzymujaca ruch */
    public void abortMove(){
        relocate(oldX,oldY);
    }

    /** Metoda ustawiania krolowej jako pionka */
    public void setQueen(){
        isQueen = true;
        if( colorPawn.equals(Color.valueOf("#c40003"))){ 
            System.out.println(colorPawn.toString());
            setColor(Color.valueOf("#700103"));
 
        }
        else {
            setColor(Color.valueOf("#fcd6b6"));
            System.out.println(colorPawn.toString());
        }

    }
}