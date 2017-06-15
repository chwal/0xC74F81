package game.map;

import game.entities.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class MiniMap {
    private GameMap gameMap;
    private Player player;

    public MiniMap(GameMap gameMap, Player player) {
        this.gameMap = gameMap;
        this.player = player;
    }

    public void render(Graphics g) {
        g.scale(0.1f, 0.1f);
        TiledMap tiledMap = gameMap.getTiledMap();
        int miniMapX = 10000;
        int miniMapY = 0;
        tiledMap.render(miniMapX, miniMapY);
        g.setColor(Color.black);
        g.drawRect(miniMapX, miniMapY, (tiledMap.getTileWidth()*tiledMap.getWidth()), (tiledMap.getTileHeight()*tiledMap.getHeight()));
        g.setColor(Color.red);
        g.fillRect(miniMapX+player.getX()+gameMap.getCurrentSectionX(), miniMapY+player.getY()+gameMap.getCurrentSectionY(), 80,80);
        g.scale(1/0.1f, 1/0.1f);
    }
}
