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

    public void handleUserInput(GameContainer gc) {
        if(gc.getInput().isKeyPressed(Input.KEY_RIGHT) || gc.getInput().isKeyPressed(Input.KEY_D)) {
            gameMap.moveEntity(player, 'R');
        } else if(gc.getInput().isKeyPressed(Input.KEY_LEFT) || gc.getInput().isKeyPressed(Input.KEY_A)) {
            gameMap.moveEntity(player, 'L');
        } else if(gc.getInput().isKeyPressed(Input.KEY_DOWN) || gc.getInput().isKeyPressed(Input.KEY_S)) {
            gameMap.moveEntity(player, 'D');
        } else if(gc.getInput().isKeyPressed(Input.KEY_UP) || gc.getInput().isKeyPressed(Input.KEY_W)) {
            gameMap.moveEntity(player, 'U');
        }
    }
}
