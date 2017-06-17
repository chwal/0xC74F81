package game.entities;

import game.Main;
import org.newdawn.slick.*;

public class Player extends Entity {
    public Player(float x, float y, SpriteSheet spriteSheet, int frameCount) throws SlickException {
        super(x, y, spriteSheet, frameCount, 100.0f);
    }

    @Override
    public void render(Graphics g, float displayX, float displayY) {
        super.render(g, displayX, displayY);
        //debugging
        g.drawRect((getTilePositionX() * 40) % Main.WINDOW_WIDTH, (getTilePositionY() * 40) % Main.WINDOW_HEIGHT, 40, 40);
    }
}
