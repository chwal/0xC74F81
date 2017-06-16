package game.entities;

import game.Main;
import game.item.Item;
import game.map.Direction;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private float x;
    private float y;
    private Direction direction;
    private List<Item> items;
    private Animation animation;

    Entity(float x, float y, SpriteSheet spriteSheet, int frameCount) {
        this.x = x;
        this.y = y;
        this.direction = Direction.EAST;
        this.items = new ArrayList<>();

        Image[] animationFrames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            animationFrames[i] = spriteSheet.getSprite(i, 0);
        }

        animation = new Animation(animationFrames, 250);
    }

    public void render(Graphics g, float displayX, float displayY) {
        //debugging
        g.drawRect((getTilePositionX() * 40) % Main.WINDOW_WIDTH, (getTilePositionY() * 40) % Main.WINDOW_HEIGHT, 40, 40);

        if(direction.equals(Direction.EAST))
            animation.draw(displayX, displayY);

        items.forEach(item -> item.render(g, displayX, displayY));
    }

    public int getTilePositionX() {
        return (((int) (x + 20) / 40));
    }

    public int getTilePositionY() {
        return (((int) (y + 20) / 40));
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
}
