/**
 * @author Juszczynska Julia, Duraki Etnik
 * reference: https://www.youtube.com/watch?v=Qgorqin4LC0
 * K 
 */
import java.util.ArrayList;
public class Enemy {
        double x;
        double y;
        private Projectiles p;
        boolean right = true;
        boolean left = false;
        double speed = 0.3;
        int WIDTH = 36;
        int HEIGHT = 36;
        boolean shooting = false;
        
        //constructor
        public Enemy(int x, int y) 
        {
            this.x = x;
            this.y = y;
            this.p = new Projectiles((int)this.x+18, (int)this.y+36);
        }
        
        //getter and setters Julia
        public void setShooting(boolean shooting) {
        	this.shooting = shooting;
        }
        public int getY() {
            return (int)this.y;
        }
        //Julia
        public boolean IsShooting() {
            return this.shooting;
        }
        //Julia
        public Projectiles getProjectile() {
            return this.p;
        }
        
        //methodes Etnik
        /**
         * moves enemy left
         * @param enemies
         */
        public void moveLeft(ArrayList<Enemy> enemies){
            if(borderLeft(enemies) > 10) {
                for(Enemy en : enemies) {
                    en.x -= speed;
                 }
            } else {
                left = false; 
                right = true;
                moveDown(enemies);
                }
            }
        
        // Etnik
        /**
         * moves enemy right
         * @param maxWidth
         * @param enemies
         */
        public void moveRight(int maxWidth, ArrayList<Enemy> enemies){
            if(borderRight(enemies) < maxWidth-60) {
            for(Enemy en : enemies) {
                en.x += speed;
            }
            } else {
                right = false;
                left = true;
                moveDown(enemies);
            }
        }
        
        // Etnik
        /**
         * moves enemy down
         * @param enemies
         */
        public void moveDown(ArrayList<Enemy> enemies) {
            for(Enemy en : enemies) {
                en.y += 10;
                }
        }
        
        // Etnik
        /**
         * moves enemy down and from right to left
         * @param maxWidth
         * @param enemies
         */
        public void movement(int maxWidth, ArrayList<Enemy> enemies){
            if(right == true) {
                moveRight(maxWidth, enemies);
            }
            if(left == true) {
                moveLeft(enemies);
            }
        }
        
        //Etnik
        /**
         * Detects on the left border
         * @param enemies
         * @return
         */
        public int borderLeft(ArrayList<Enemy> enemies) {
            double tempx = 500;
            for(Enemy en : enemies) {
                if(tempx>en.x) {
                    tempx = en.x;
                }
            }
            return (int)tempx;
        }
        
        // Etnik
        /**
         * Detects on the right border
         * @param enemies
         * @return
         */
        public int borderRight(ArrayList<Enemy> enemies) {
            double tempx = 0;
            for(Enemy en : enemies) {
                if(tempx<en.x) {
                    tempx = en.x;
                }
            }
            return (int)tempx;
        }
        
        //Etnik
        /**
         * Detects on the border below
         * @param enemies
         * @return
         */
        public int borderDown(ArrayList<Enemy> enemies) {
            double tempy = 0;
            for(Enemy en : enemies) {
                if(tempy<en.y) {
                    tempy = en.y;
                }
            }
            return (int)tempy;
        }
        
        
        //Julia
        /**
         * shoots projectile
         * @param maxHeight
         * @param barriers
         */
        public void shoot(int maxHeight, ArrayList<Barrier> barriers) {
        	if(this.IsShooting()) {
        		this.p.moveDown(maxHeight, barriers, (int)this.x, (int)this.y);
        		return;
        	} 
        	if(this.p.collisionDetectionBarrier(barriers) || this.p.y>=maxHeight) {
    			this.shooting = false;
    		}
        	      	
        }
        
        //Julia
        public void projectileUpdate() {
            p.x = (int)this.x;
            p.y = (int)this.y;
        }
        
        /*private void setShootCooldown() {
        	this.shooting = false;
        	//Cooldown
        	Random rand = new Random();
        	
        	this.shooting = true;
        }*/
}