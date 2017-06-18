package game.entities;

import game.Main;
import org.newdawn.slick.*;

public class Player extends Entity {
    public Player(float x, float y, SpriteSheet spriteSheet, int frameCount) throws SlickException {
        super(x, y, spriteSheet, frameCount, 90.0f);
    }

    @Override
    public void render(Graphics g, float displayX, float displayY) {
        super.render(g, displayX, displayY);

        if(Main.debugMode){
            int onTileX = getTilePositionX() * 40 % Main.WINDOW_WIDTH;
            int onTileY = getTilePositionY() * 40 % Main.WINDOW_HEIGHT;
            g.drawRect(onTileX, onTileY, 40, 40);
        }
    }
}
