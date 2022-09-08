/**
 * 
 * @author Juszczynska Julia, Duraki Etnik
 *
 */

//Julia
public class Barrier {
    private int x;
    private int y;
    final int WIDTH = 80;
    final int HEIGHT = 5;
    
    //constructor
    public Barrier (int x, int y){
        this.x = x;
        this.y = y;
    }
    //getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}