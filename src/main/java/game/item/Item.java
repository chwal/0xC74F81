package game.item;

import org.newdawn.slick.Graphics;

public abstract class Item {
    private final String name;

    Item(String name) {
        this.name = name;
    }

    public abstract void render(Graphics g, float positionX, float positionY);

    public String getName() {
        return name;
    }
}
