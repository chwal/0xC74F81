package game.entities;

import game.item.Item;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private Animation animation;
    private SpriteSheet spriteSheet;
    Direction direction;
    float x;
    float y;
    List<Item> items;

    Entity(float x, float y, SpriteSheet spriteSheet, int frameCount) {
        this.x = x;
        this.y = y;
        this.direction = Direction.EAST;
        this.items = new ArrayList<>();
        this.spriteSheet = spriteSheet;

        Image[] animationFrames = new Image[frameCount];
        for(int i = 0; i < frameCount; i++) {
            animationFrames[i] = spriteSheet.getSprite(i, 0);
        }

        animation = new Animation(animationFrames, 250);
    }

    public void render(Graphics g) {
        g.drawRect(getMapPositionX()*40, getMapPositionY()*40, 40,40);
        if(direction.equals(Direction.EAST))
            animation.draw(x, y);

        items.forEach(item -> item.render(g, x, y));
    }

    public void update(int delta) {
        animation.update(delta);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMapPositionX() {
        return ((int)(x+20)/40);
    }

    public int getMapPositionY() {
        return ((int)(y+20)/40);
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
