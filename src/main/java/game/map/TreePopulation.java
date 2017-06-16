package game.map;

import game.gameobject.GameObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreePopulation {
    private List<GameObject> trees;

    public TreePopulation() {
        this.trees = new ArrayList<>();
    }

    public void load() throws SlickException {
        trees.add(new GameObject(new Image("sprites/tree_0.png"), true));
        trees.add(new GameObject(new Image("sprites/tree_1.png"), true));
    }

    //TODO: Fix bug that somehow trees still spawn on 0X or 0Y (display tile coordinates)
    public void populateMap(GameMap gameMap) {
        Random posRandom = new Random();
        Random treeRandom = new Random();
        TiledMap tiledMap = gameMap.getTiledMap();

        int treeCount = 500;
        int treesPlaced = 0;
        while (treesPlaced <= treeCount) {
            int xPos = posRandom.nextInt(tiledMap.getWidth());
            int yPos = posRandom.nextInt(tiledMap.getHeight());

            if(!gameMap.isBlockedTile(xPos, yPos) && !gameMap.isStaticCollisionTile(xPos, yPos) && !gameMap.isDynamicCollisionTile(xPos, yPos)) {
                GameObject gameObject = trees.get(treeRandom.nextInt(trees.size()));
                gameMap.addGameObject(gameObject, new Point(xPos, yPos));
                treesPlaced++;
            }
        }
    }
}
