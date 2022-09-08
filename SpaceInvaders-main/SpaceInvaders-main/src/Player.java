/**
 * @author Juszczynska Julia, Duraki Etnik
 * reference: https://www.youtube.com/watch?v=Qgorqin4LC0
 * K 
 */
public class Player extends Character
{
    boolean moveRight;
    boolean moveLeft;
    public Player (int x, int y, int s) 
    {
        super(x, y, s);
        moveLeft = false;
        moveRight = false;
    }
}
