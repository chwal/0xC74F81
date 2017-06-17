package game.item;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Ammo extends Item {
    private final Image image;

    public Ammo(String name, Image image) {
        super(name);
        this.image = image;
    }

    @Override
    public void render(Graphics g, float positionX, float positionY) {
        g.drawImage(image, positionX, positionY);
    }
}
