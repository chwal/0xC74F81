package game.entities;

import game.item.Armor;
import game.item.Gun;
import game.item.Helmet;
import game.item.Item;
import game.map.Direction;
import game.map.GameMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    float x;
    private float y;
    float velocity;
    private List<Item> items;
    private boolean moving;
    private Direction facing;
    Animation animation;


    Entity(float x, float y, SpriteSheet spriteSheet, int frameCount, float velocity) {
        this.x = x;
        this.y = y;
        this.items = new ArrayList<>();
        this.moving = false;
        this.facing = Direction.EAST;
        this.velocity = 100.0f;

        Image[] animationFrames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            animationFrames[i] = spriteSheet.getSprite(i, 0);
        }

        animation = new Animation(animationFrames, 250);
    }

    public void render(Graphics g, float displayX, float displayY) {
        boolean flipped = facing == Direction.WEST;
        animation.getCurrentFrame().getFlippedCopy(flipped, false).draw(displayX, displayY);

        if(!moving) {
            animation.setCurrentFrame(0);
        }

        items.stream()
                .filter(item -> item instanceof Armor || item instanceof Helmet)
                .forEach(item -> item.render(g, displayX, displayY, flipped));

        items.stream()
                .filter(item -> item instanceof Gun)
                .forEach(item -> item.render(g, displayX, displayY, flipped));
    }

    public void update(int delta, GameMap gameMap) {
        if(moving) {
            animation.update(delta);
        }
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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
