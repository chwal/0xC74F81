package game.input;

import game.entities.Player;
import game.map.Direction;
import game.map.GameMap;
import game.map.MiniMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {
    private final Player player;
    private final GameMap gameMap;
    private final MiniMap miniMap;

    public InputHandler(Player player, GameMap gameMap, MiniMap miniMap) {
        this.player = player;
        this.gameMap = gameMap;
        this.miniMap = miniMap;
    }

    public void handleUserInput(GameContainer gc, int delta) {
        Input input = gc.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            gameMap.moveEntity(player, Direction.EAST, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            gameMap.moveEntity(player, Direction.WEST, delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            gameMap.moveEntity(player, Direction.SOUTH, delta);
        }
        if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            gameMap.moveEntity(player, Direction.NORTH, delta);
        }

        //debugging
        if(input.isKeyDown(Input.KEY_LSHIFT)) {
            player.setVelocity(300.0f);
        } else {
            player.setVelocity(100.0f);
        }

        if(!input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_A) &&
                !input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_S) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_W)) {
            player.setMoving(false);
        }

        if(input.isKeyDown(Input.KEY_TAB)) {
            miniMap.setShowMiniMap(true);
        } else {
            miniMap.setShowMiniMap(false);
        }
    }
}
