package game.map.population;

import game.gameobject.GameObject;
import game.map.GameMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TreePopulation {
    private static final int MAX_PROBABILITY = 11;
    private List<TreeSpawn> trees;

    public TreePopulation() {
        this.trees = new ArrayList<>();
    }

    public void load() throws SlickException {
        trees.add(new TreeSpawn(new GameObject(new Image("sprites/tree_0.png"), true), SpawnChance.HIGH));
        trees.add(new TreeSpawn(new GameObject(new Image("sprites/tree_1.png"), true), SpawnChance.HIGH));
        trees.add(new TreeSpawn(new GameObject(new Image("sprites/tree_2.png"), true), SpawnChance.LOW));
        trees.add(new TreeSpawn(new GameObject(new Image("sprites/tree_3.png"), true), SpawnChance.LOW));
    }

    private GameObject getNextTree(int probability) {
        Random treeRandom = new Random();

        List<TreeSpawn> possibleTrees = trees.stream()
                .filter(tree -> probability >= tree.spawnChance.low && probability <= tree.spawnChance.high)
                .collect(Collectors.toList());

        return possibleTrees.get(treeRandom.nextInt(possibleTrees.size())).gameObject;
    }

    public void populateMap(GameMap gameMap) {
        Random positionRan = new Random();
        Random probabilityRan = new Random();
        TiledMap tiledMap = gameMap.getTiledMap();

        int treeCount = 1300;
        int treesPlaced = 0;
        while (treesPlaced <= treeCount) {
            int xPos = positionRan.nextInt(tiledMap.getWidth());
            int yPos = positionRan.nextInt(tiledMap.getHeight());

            if(!gameMap.isBlockedTile(xPos, yPos) && !gameMap.isStaticCollisionTile(xPos, yPos) && !gameMap.isDynamicCollisionTile(xPos, yPos)) {
                int probability = probabilityRan.nextInt(MAX_PROBABILITY);
                GameObject gameObject = getNextTree(probability);
                gameMap.addGameObject(gameObject, new Point(xPos, yPos));
                treesPlaced++;
            }
        }
    }

    private class TreeSpawn {
        GameObject gameObject;
        SpawnChance spawnChance;

        TreeSpawn(GameObject gameObject, SpawnChance spawnChance) {
            this.gameObject = gameObject;
            this.spawnChance = spawnChance;
        }
    }
}
