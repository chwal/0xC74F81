package game.entities;

import game.item.Item;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private Image image;

    public Player(float x, float y) throws SlickException {
        super(x,y);
        this.image = new Image("sprites/player.png");
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    void render(Graphics g) {
        g.drawImage(image, x, y);
        items.forEach(item -> item.render(g, x, y));
    }

    public Image getImage() {
        return image;
    }
}
