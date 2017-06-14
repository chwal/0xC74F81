package game.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Image implements Entity {
    private float x;
    private float y;

    public Player(float x, float y) throws SlickException {
        super("sprites/test.png");
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isAlive() {
        return true;
    }

    public double getHealth() {
        return 0;
    }

    public void setHealth(double health) {

    }

}
