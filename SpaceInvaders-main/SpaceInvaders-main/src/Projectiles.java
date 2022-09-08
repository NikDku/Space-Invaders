/**
 * @author Juszczynska Julia, Duraki Etnik
 * reference: https://www.youtube.com/watch?v=Qgorqin4LC0
 * K 
 */
import java.util.ArrayList;
public class Projectiles {
    int x;
    int y;
    static final int WIDTH = 5;
    static final int HEIGHT = 50;
    int enY;
    int enemiesIndex;
 
    public Projectiles(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
   //getters and setters
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    
    //Julia
    public int getIndexOfEnemies() {
        return this.enemiesIndex;
    }
    
    //Etnik & Julia
    /**
     * moves projectile up
     */
    public void moveUP() {      
            this.y -= 5;
    }
   
    //Julia
    /**
     * moves projectile down
     * @param maxHeight
     * @param barriers
     * @param x
     * @param y
     */
    public void moveDown(int maxHeight, ArrayList<Barrier> barriers, int x, int y) {
        if(this.y < maxHeight) {
        this.y += 1;}
        else {
        this.x = x;
        this.y = y;}
        if(this.collisionDetectionBarrier(barriers) ) {
        	this.x = x;
        	this.y = y;
        }
          
    }
    
    //Julia & Etnik
    /**
     * detects if projectile colided with enemie
     * @param enemies
     * @return
     */
    public boolean collisionDetection(ArrayList<Enemy> enemies) {
        for(int i = 0; i<enemies.size(); i++) {
            Enemy en = enemies.get(i);
            if(en.y<=this.y && this.y <= en.y + en.HEIGHT+36 && en.x <= this.x && this.x<=en.x+en.WIDTH ) {
                this.enemiesIndex = i;
                return true;
            }
        }
        return false;
    }
    
    //Julia
    /**
     * detects if projectile colided with player
     * @param p
     * @return
     */
    public boolean collisionDetection(Player p) {
        if(this.y == p.x && p.x-20 <= this.x && this.x <= p.x) {
            return true;
        }
        return false;
    }
    
    //Julia
    /**
     * detects if projectile colided with barrier
     * @param barriers
     * @return
     */
    public boolean collisionDetectionBarrier(ArrayList<Barrier> barriers) {
        for(Barrier b : barriers) {
            if(this.y == b.getY() && b.getX()+b.WIDTH >= this.x && this.x>=b.getX() ) {
                return true;
            }
        }
        return false;
    }
    
}