package game.entities;

import game.item.Item;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private int mapPositionX;
    private int mapPositionY;
    float x;
    float y;
    List<Item> items;

    Entity(float x, float y) {
        this.x = x;
        this.y = y;
        this.mapPositionX = 0;
        this.mapPositionY = 0;
        this.items = new ArrayList<>();
    }

    public int getMapPositionX() {
        return mapPositionX;
    }

    public void setMapPositionX(int mapPositionX) {
        this.mapPositionX = mapPositionX;
    }

    public int getMapPositionY() {
        return mapPositionY;
    }

    public void setMapPositionY(int mapPositionY) {
        this.mapPositionY = mapPositionY;
    }

    public List<Item> getItems() {
        return items;
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

    abstract void render(Graphics g);
}
