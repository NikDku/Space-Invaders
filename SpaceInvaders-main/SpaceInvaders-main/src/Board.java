/**
 * @author Juszczynska Julia, Duraki Etnik
 * reference: https://www.youtube.com/watch?v=Qgorqin4LC0
 * K 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable, MouseListener
{
    //variable imges
    private Image spaceshippng = new ImageIcon("joe.png").getImage();
    private Image enemypng = new ImageIcon("mama.png").getImage();
    BufferedImage img;
    
    //variable setup
    private boolean ingame = true;
    private Dimension d;
    private int BOARD_WIDTH = 500;
    private int BOARD_HEIGHT = 500;
    
    //variable koordinate
    int x = 0;
    int tempX;
    final int yVelocity=-100;
    
    //variable animation
    private Thread animator;
    long time = System.currentTimeMillis();
    int animationDelay = 5;
    
    //objekte und arraylist
    Player p;
    Enemy e;
    Projectiles pr;
    ArrayList <Projectiles> projectiles;
    ArrayList <Enemy> enemies;
    ArrayList <Barrier> barriers;
    
    //score und life
    int score = 0;
    int life = 4;
    boolean kollision;
    
    //random integer
    int random_int;
    int max = 40;
    int min = 1;
    Random rdm = new Random();
    
    public Board()
    {   //setup
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        
        setBackground(Color.black);
        
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        p = new Player(BOARD_WIDTH/2, BOARD_HEIGHT-60, 2);
        
        projectiles = new ArrayList <Projectiles>();
        enemies = new ArrayList <Enemy>();
        barriers = new ArrayList<Barrier>();
        
        int enemyX = 30;
        int enemyY = 20;
        int barrierX = d.width/5;
        
        // Erstellung von enemies
        for (int j = 0; j<3; j++) { 
            for (int i = 0; i<9; i++) {
                e = new Enemy(enemyX, enemyY);
                enemies.add(e);     
                enemyX +=40;
            }
            enemyX = 30;
            enemyY += 40;
        }
        
        //barriers
        for(int o = 0; o<3; o++) {
            Barrier b = new Barrier(barrierX, 300);
            barriers.add(b);
            barrierX+=b.WIDTH+barrierX;
        }
        
        //animation
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
        setDoubleBuffered(true);
    }
    
    //anzeigen
    public void paint(Graphics g)
    {
        super.paint(g);
        
        //board
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        
        //player
        g.drawImage(spaceshippng, p.x, p.y-15, null);
        
        //barriers
        for(Barrier ba: barriers) {
        g.setColor(Color.white);
        g.fillRect(ba.getX(), ba.getY(), ba.WIDTH, ba.HEIGHT);
        }
        
        
        
        //player's projectile
         if(!projectiles.isEmpty()){
            for(int i = 0; i<projectiles.size(); i++) {
                Projectiles projectile = projectiles.get(i);
                
                //wenn f�llt aus board
                if (projectile.y < 0) {
                    projectiles.remove(i);
                }else {
                    g.setColor(Color.CYAN);
                    g.fillRect(projectile.x, projectile.y-60, projectile.WIDTH, projectile.HEIGHT);
                    projectile.moveUP();
                    
                    //collision detection
                    kollision = projectile.collisionDetection(enemies);
                    if(kollision) {
                        enemies.remove(projectile.getIndexOfEnemies());
                        projectiles.remove(i);
                        score++;
                    }
                    if(projectile.collisionDetectionBarrier(barriers)) {
                        projectiles.remove(i);
                    }
                }
            } 
         }
         
         //Bewegung Enemies
        if(!enemies.isEmpty() && e.borderDown(enemies) < 260 && life > 0) {
            e.movement(BOARD_WIDTH, enemies);
        }
       //victory
        
        // Player tot
        if(life <= 0) {
        	p.moveLeft = false;
        	p.moveRight = false;
        	g.setColor(Color.black);
        	g.fillRect(p.x, p.y-15, 40, 40);
        	
        }
        
        /**
         * When all enemies are dead then there will be confetti
         */
        // Victory screen
        if(enemies.isEmpty() ) {
        	Font big = new Font ("Helverica", Font.BOLD, 20);
        	g.setColor(Color.white);
            g.setFont(big);
            g.drawString("You win", d.width/2-40, d.height/2);
            for(int i = 0; i<50; i++) {
            	random_int = rdm.nextInt(BOARD_WIDTH);
            	Color clr = new Color(rdm.nextInt(255), rdm.nextInt(255), rdm.nextInt(255));
            	g.setColor(clr);
            	g.drawOval(random_int, rdm.nextInt(BOARD_HEIGHT), 5, 5);
            }
        } else {
        	//enemies
            int tmp = new Random().nextInt(enemies.size() - 1);
           
            enemies.get(tmp).setShooting(true);

            
            for(Enemy en : enemies) {
                g.drawImage(enemypng, (int)en.x, (int)en.y, null);
                
                //enemies' projectiles
                if(en.IsShooting() && life > 0) {
                	g.setColor(Color.green);
                    g.fillRect(en.getProjectile().x, en.getProjectile().y, en.getProjectile().WIDTH, 30);
                    en.shoot(d.height, barriers);
                	
    	        	//collision detection
    	            if(en.getProjectile().collisionDetection(p)) {
    	                life--;
    	            };
                }
            
            }
        	
        }
        
        
         //boundry
        if(p.x > d.width-40)
            p.moveRight = false;
        if(p.x <10)
            p.moveLeft = false;
        
        //player move
        if(p.moveRight ==true)
            p.x += p.speed;
        if(p.moveLeft == true)
            p.x -= p.speed;
        
        //fonts setup
        Font small = new Font("Helverica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        Font big = new Font ("Helverica", Font.BOLD, 20);
        
        //txt score
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Score: "+ score, 10, d.height-60);
        
        //txt life
        if(life>0)
        g.drawString("Life: "+ life, 380, d.height-60);
        else {
        g.drawString("Life: 0", 380, d.height-60);
        
        //txt game over
        g.setFont(big);
        g.drawString("GAME OVER \n Score: " + score, d.width/2-100, d.height/2-50);
        }
        if (ingame) {
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    private class TAdapter extends KeyAdapter
    {
        //wenn key released player bewegt sich nicht
        public void keyReleased(KeyEvent e)
        {
            e.getKeyCode();
            p.moveRight = false;
            p.moveLeft = false;
        }
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            //if key 39 then move right
            if(key == 39)
            {
                p.moveRight = true;
            }
            //if key 37, move left
            if(key == 37)
            {
                p.moveLeft = true;
            } 
            //if key 32 make a new projectile
            if(key == 32 && life > 0) {
                Projectiles pr = new Projectiles(p.x+18-(Projectiles.WIDTH/2), p.y);
                projectiles.add(pr);
            }
        }
    }
    public void mousePressed(MouseEvent e)
    {
        e.getX();
        e.getY();
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void run()
    {
        System.currentTimeMillis();
        while (true)
        {
        	if(!enemies.isEmpty()) {
        	random_int = rdm.nextInt(enemies.size());
        	}
            //spriteManager.upadate();
            repaint();
            try
            {
                time += animationDelay;
                Thread.sleep(Math.max(0, time -System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println(e);
            } // end catch
        } //infinite loop
    }
}