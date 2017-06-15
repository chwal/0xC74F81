package game.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Entity {
    public Player(float x, float y, SpriteSheet spriteSheet, int frameCount) throws SlickException {
        super(x,y, spriteSheet, frameCount);
    }
}
