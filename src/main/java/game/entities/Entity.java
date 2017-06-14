package game.entities;

public interface Entity {
    public boolean isAlive();

    public double getHealth();

    public void setHealth(double health);

    public float getX();

    public float getY();

    public void setX(float x);

    public void setY(float y);
}
