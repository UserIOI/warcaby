package warcaby;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Pawn extends Circle{

    /** rozwijam klase Circle zeby stworzyc pionka i dodac jej funkcjonalnośći zwiazane z myszką
     * @see Circle
     */
    private Pane myBoard = new Pane();
    double oldX;
    double oldY;
    double mouseX,mouseY;
    /** konstruktor dziedziczymy po klasie Circle, dodajemy GridPane na ktorym tworzymy plansze i eventy zwiazane z myszka */
    public Pawn(double x, double y, double radius, Pane pane) {
        super(x, y, radius);
        myBoard = pane;
        oldX = x;
        oldY = y;
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

    public void move(int x, int y) {
        oldX = (x * 100)+25;
        oldY = (y * 100)+25;
        relocate(oldX,oldY);
    }
    public void abortMove(){
        relocate(oldX,oldY);
    }

    /** implementacja przesuwania figury gdy jest aktywna */
//    final class PawnEventHandler implements EventHandler<MouseEvent>{

//        Pawn pawn;
 //       private double x;
  //      private double y;

  //      private void doMove(MouseEvent event) {

 //           double dx = event.getX() - x;
 //           double dy = event.getY() - y;

 //           pawn.addX(dx);
 //           pawn.addY(dy);
 //           x += dx;
 //           y += dy;
  //      }


        /**kiedy myszka sie rusza wewnatrz figury to probojemy ja przesunac - jesli jest aktywna to ja przesuwamy*/
//        @Override
//        public void handle(MouseEvent event) {

//            pawn = (Pawn) event.getSource();
//            if (event.getEventType()==MouseEvent.MOUSE_DRAGGED){
//                doMove(event);
//            }

//        }
//    }
}