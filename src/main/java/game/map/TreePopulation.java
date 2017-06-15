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
        trees = new ArrayList<>();
    }

    public void load() throws SlickException {
        this.trees.add(new GameObject(new Image("sprites/tree_0.png"), true));
        this.trees.add(new GameObject(new Image("sprites/tree_1.png"), true));
    }

    //TODO: Consider pre-existing static objects (StaticCollisionLayer), dynamic objects (avoid overlapping) and blocked tiles (BlockedTilesLayer) when spawning trees
    public void populateMap(GameMap gameMap) {
        Random posRandom = new Random();
        Random treeRandom = new Random();
        TiledMap tiledMap = gameMap.getTiledMap();

        for(int i = 0; i < 200; i++) {
            float xPos = posRandom.nextInt(tiledMap.getWidth());
            float yPos = posRandom.nextInt(tiledMap.getHeight());

            GameObject gameObject = trees.get(treeRandom.nextInt(trees.size()));
            gameMap.addGameObject(gameObject, new Point(xPos, yPos));
        }
    }
}
