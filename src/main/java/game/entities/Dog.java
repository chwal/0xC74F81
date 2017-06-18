package game.entities;

import game.map.Direction;
import game.map.GameMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Dog extends Entity {
    public Dog(float x, float y, SpriteSheet spriteSheet, int frameCount) throws SlickException {
        super(x, y, spriteSheet, frameCount, 100.0f);
    }

    @Override
    public void update(int delta, GameMap gameMap) {
        super.update(delta, gameMap);
        gameMap.moveEntity(this, Direction.EAST, delta);
    }
}
