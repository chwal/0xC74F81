package game.entities;

import game.item.Item;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    public Player(float x, float y, SpriteSheet spriteSheet, int frameCount) throws SlickException {
        super(x,y, spriteSheet, frameCount);
    }
}
