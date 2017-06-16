package game.map;

import game.Main;
import game.entities.Entity;
import game.gameobject.GameObject;
import game.item.Item;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameMap {
    private static final int SECTION_DIMENSION_X = 2;
    private static final int SECTION_DIMENSION_Y = 4;
    private static final int MAP_HEIGHT = Main.WINDOW_HEIGHT * SECTION_DIMENSION_Y;
    private static final int MAP_WIDTH = Main.WINDOW_WIDTH * SECTION_DIMENSION_X;
    public static final int TILE_SIZE = 40;

    private static final  float SPEED_IN_PX_PER_MS = (100.0f/1000);

    private TiledMap tiledMap;

    private int mapXOffset;
    private int mapYOffset;

    private int collisionLayer;

    private Map<Point, Item> mapItems;
    private Map<Point, GameObject> mapObjects;

    private float currentSectionX;
    private float currentSectionY;

    public GameMap() throws SlickException {
        mapXOffset = 0;
        mapYOffset = 0;
        tiledMap = new TiledMap("maps/map.tmx");
        collisionLayer = tiledMap.getLayerIndex("StaticCollisionLayer");
        mapItems = new HashMap<>();
        mapObjects = new HashMap<>();
        currentSectionX = 0;
        currentSectionY = 0;
    }

    public void render(Graphics g) {
        tiledMap.render(mapXOffset, mapYOffset);

        mapObjects.forEach((mapPosition, mapObject) -> {
            float actualX = mapPosition.getX() * 40;
            float actualY = mapPosition.getY() * 40;

            if((currentSectionX + Main.WINDOW_WIDTH >= actualX && currentSectionX <= actualX) &&
                    (currentSectionY + Main.WINDOW_HEIGHT >= actualY && currentSectionY <= actualY)) {
                actualX %= Main.WINDOW_WIDTH;
                actualY %= Main.WINDOW_HEIGHT;
                mapObject.getImage().draw(actualX, actualY);
            }
        });

        mapItems.forEach((mapPosition, item) -> {
            float actualX = mapPosition.getX() * 40;
            float actualY = mapPosition.getY() * 40;

            if((currentSectionX + Main.WINDOW_WIDTH >= actualX && currentSectionX <= actualX) &&
                    (currentSectionY + Main.WINDOW_HEIGHT >= actualY && currentSectionY <= actualY)) {
                actualX %= Main.WINDOW_WIDTH;
                actualY %= Main.WINDOW_HEIGHT;

                g.setColor(Color.yellow);
                g.drawRect(actualX, actualY, 40, 40);
                item.render(g, actualX, actualY);
            }
        });
    }

    //TODO: Add collision detection for dynamic map objects (trees)
    public void moveEntity(Entity entity, char direction, int delta) {
        switch (direction) {
            case 'R':
                float newXPos = entity.getX() - SPEED_IN_PX_PER_MS * delta;
                if(newXPos >= 0 && ((int)(newXPos+20)/40) >= 0 && tiledMap.getTileId(((int)(newXPos+20)/40), entity.getMapPositionY(), collisionLayer) == 0) {
                    entity.setX(newXPos);
                    entity.setX(entity.getX() + SPEED_IN_PX_PER_MS * delta);
                    if(entity.getX() > Main.WINDOW_WIDTH - GameMap.TILE_SIZE) {
                        mapXOffset -= Main.WINDOW_WIDTH;
                        entity.setX(0);
                        currentSectionX += Main.WINDOW_WIDTH;
                    }
                }
                break;
            case 'L':
                float newXPos_1 = entity.getX() - SPEED_IN_PX_PER_MS * delta;
                if(newXPos_1 >= 0 && ((int)(newXPos_1+20)/40) >= 0 && tiledMap.getTileId(((int)(newXPos_1+20)/40), entity.getMapPositionY(), collisionLayer) == 0) {
                    entity.setX(newXPos_1);
                    if(entity.getX() < 0) {
                        mapXOffset += Main.WINDOW_WIDTH;
                        entity.setX(Main.WINDOW_WIDTH - GameMap.TILE_SIZE);
                        currentSectionX -= Main.WINDOW_WIDTH;
                    }
                }
                break;
            case 'U':
                float newYPos = entity.getY() - SPEED_IN_PX_PER_MS * delta;
                if(newYPos >= 0 && ((int)(newYPos+20)/40) >= 0 && tiledMap.getTileId(((int)(newYPos+20)/40), entity.getMapPositionY() - 1, collisionLayer) == 0) {
                    entity.setY(newYPos);
                    if(entity.getY() < 0) {
                        mapYOffset += Main.WINDOW_HEIGHT;
                        entity.setY(Main.WINDOW_HEIGHT - GameMap.TILE_SIZE);
                        currentSectionY -= Main.WINDOW_HEIGHT;
                    }
                }
                break;
            case 'D':
                float newYPos_1 = entity.getY() + SPEED_IN_PX_PER_MS * delta;
                if(newYPos_1 >= 0 && ((int)(newYPos_1+20)/40) >= 0 && tiledMap.getTileId(((int)(newYPos_1+20)/40), entity.getMapPositionY() + 1, collisionLayer) == 0) {
                    entity.setY(newYPos_1);
                    if(entity.getY() > Main.WINDOW_HEIGHT - GameMap.TILE_SIZE) {
                        mapYOffset -= Main.WINDOW_HEIGHT;
                        entity.setY(0);
                        currentSectionY += Main.WINDOW_HEIGHT;
                    }
                }
                break;
        }

        Optional<Map.Entry<Point, Item>> optMapItem = getMapItem(entity.getMapPositionX(), entity.getMapPositionY());
        if(optMapItem.isPresent()) {
            Map.Entry<Point, Item> itemEntry = optMapItem.get();
            entity.getItems().add(itemEntry.getValue());
            mapItems.remove(itemEntry.getKey());
        }
    }

    private Optional<Map.Entry<Point, Item>> getMapItem(float mapPositionX, float mapPositionY) {
        return mapItems.entrySet().stream()
                .filter(entry -> entry.getKey().getX() == mapPositionX && entry.getKey().getY() == mapPositionY)
                .findFirst();
    }

    public void addGameObject(GameObject gameObject, Point position) {
        this.mapObjects.put(position, gameObject);
    }

    public void addMapItem(Item item, Point position) {
        this.mapItems.put(position, item);
    }

    public float getCurrentSectionX() {
        return currentSectionX;
    }

    public float getCurrentSectionY() {
        return currentSectionY;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
