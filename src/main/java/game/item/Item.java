package game.item;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Item {
    private final String name;
    private final Image image;
    private final Image flippedImage;
    private boolean isVisual = false;

    Item(String name, Image image) {
        this.name = name;
        this.image = image;
        this.flippedImage = image.getFlippedCopy(true, false);
    }

    Item(String name, Image image, boolean isVisual) {
        this.name = name;
        this.image = image;
        this.flippedImage = image.getFlippedCopy(true, false);
        this.isVisual = isVisual;
    }

    public void render(Graphics g, float positionX, float positionY, boolean flipped) {
        if(flipped)
            g.drawImage(flippedImage, positionX, positionY);
        else
            g.drawImage(image, positionX, positionY);
    }
    public String getName() {
        return name;
    }
}
