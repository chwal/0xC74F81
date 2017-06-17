package game.item;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Armor extends Item {
    private final Image image;

    public Armor(String name, Image image) {
        super(name);
        this.image = image;
    }

    @Override
    public void render(Graphics g, float positionX, float positionY) {
        g.drawImage(image, positionX, positionY);
    }
}
