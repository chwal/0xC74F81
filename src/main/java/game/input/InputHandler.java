package game.input;

import game.entities.Player;
import game.map.GameMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {
    private final Player player;
    private final GameMap gameMap;

    public InputHandler(Player player, GameMap gameMap) {
        this.player = player;
        this.gameMap = gameMap;
    }

    public void handleUserInput(GameContainer gc, int delta) {
        if(gc.getInput().isKeyDown(Input.KEY_RIGHT) || gc.getInput().isKeyDown(Input.KEY_D)) {
            gameMap.moveEntity(player, 'R', delta);
        }
        if(gc.getInput().isKeyDown(Input.KEY_LEFT) || gc.getInput().isKeyDown(Input.KEY_A)) {
            gameMap.moveEntity(player, 'L', delta);
        }
        if(gc.getInput().isKeyDown(Input.KEY_DOWN) || gc.getInput().isKeyDown(Input.KEY_S)) {
            gameMap.moveEntity(player, 'D', delta);
        }
        if(gc.getInput().isKeyDown(Input.KEY_UP) || gc.getInput().isKeyDown(Input.KEY_W)) {
            gameMap.moveEntity(player, 'U', delta);
        }
    }
}
