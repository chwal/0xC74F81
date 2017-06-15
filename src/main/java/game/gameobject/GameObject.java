package game.gameobject;

import org.newdawn.slick.Image;

public class GameObject {
    private final Image image;
    private final boolean collision;

    public GameObject(Image image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public Image getImage() {
        return image;
    }

    public boolean isCollision() {
        return collision;
    }
}
