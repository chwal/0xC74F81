package game.map;

import game.Main;
import game.entities.Entity;
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
    private static final int SECTION_DIMENSION = 2;
    private static final int MAP_HEIGHT = Main.WINDOW_HEIGHT * SECTION_DIMENSION;
    private static final int MAP_WIDTH = Main.WINDOW_WIDTH * SECTION_DIMENSION;
    public static final int TILE_SIZE = 40;

    private TiledMap tiledMap;

    private int mapXOffset;
    private int mapYOffset;

    private int collisionLayer;

    private Map<Item, Point> mapItems;

    private float currentSectionX;
    private float currentSectionY;

    public GameMap() throws SlickException {
        mapXOffset = 0;
        mapYOffset = 0;
        tiledMap = new TiledMap("maps/map.tmx");
        collisionLayer = tiledMap.getLayerIndex("Collision");
        mapItems = new HashMap<>();
        currentSectionX = 0;
        currentSectionY = 0;
    }

    public void render(Graphics g) {
        tiledMap.render(mapXOffset, mapYOffset, 0);

        mapItems.forEach((item, mapPosition) -> {
            float actualX = mapPosition.getX() * 40;
            float actualY = mapPosition.getY() * 40;

            if((currentSectionX + Main.WINDOW_WIDTH >= actualX && currentSectionX <= actualX) &&
                    (currentSectionY + Main.WINDOW_HEIGHT >= actualY && currentSectionY <= actualY)) {
                if(actualX > Main.WINDOW_WIDTH) {
                    actualX -= Main.WINDOW_WIDTH;
                }

                if(actualY > Main.WINDOW_HEIGHT) {
                    actualY -= Main.WINDOW_HEIGHT;
                }

                g.setColor(Color.yellow);
                g.drawRect(actualX, actualY, 40, 40);
                item.render(g, actualX, actualY);
            }
        });
    }

    public void moveEntity(Entity entity, char direction) {
        switch (direction) {
            case 'R':
                if(entity.getMapPositionX() + 1 < GameMap.MAP_WIDTH / 40 && tiledMap.getTileId(entity.getMapPositionX() + 1, entity.getMapPositionY(), collisionLayer) == 0) {
                    entity.setX(entity.getX() + GameMap.TILE_SIZE);
                    if(entity.getX() > Main.WINDOW_WIDTH - GameMap.TILE_SIZE) {
                        mapXOffset -= Main.WINDOW_WIDTH;
                        entity.setX(0);
                        currentSectionX += Main.WINDOW_WIDTH;
                    }
                    entity.setMapPositionX(entity.getMapPositionX() + 1);
                }
                break;
            case 'L':
                if(entity.getMapPositionX() - 1 >= 0 && tiledMap.getTileId(entity.getMapPositionX() - 1, entity.getMapPositionY(), collisionLayer) == 0) {
                    entity.setX(entity.getX() - GameMap.TILE_SIZE);
                    if(entity.getX() < 0) {
                        mapXOffset += Main.WINDOW_WIDTH;
                        entity.setX(Main.WINDOW_WIDTH - GameMap.TILE_SIZE);
                        currentSectionX -= Main.WINDOW_WIDTH;
                    }
                    entity.setMapPositionX(entity.getMapPositionX() - 1);
                }
                break;
            case 'U':
                if(entity.getMapPositionY() - 1 >= 0 && tiledMap.getTileId(entity.getMapPositionX(), entity.getMapPositionY() - 1, collisionLayer) == 0) {
                    entity.setY(entity.getY() - GameMap.TILE_SIZE);
                    if(entity.getY() < 0) {
                        mapYOffset += Main.WINDOW_HEIGHT;
                        entity.setY(Main.WINDOW_HEIGHT - GameMap.TILE_SIZE);
                        currentSectionY -= Main.WINDOW_HEIGHT;
                    }
                    entity.setMapPositionY(entity.getMapPositionY() - 1);
                }
                break;
            case 'D':
                if(entity.getMapPositionY() + 1 < GameMap.MAP_HEIGHT / 40 && tiledMap.getTileId(entity.getMapPositionX(), entity.getMapPositionY() + 1, collisionLayer) == 0) {
                    entity.setY(entity.getY() + GameMap.TILE_SIZE);
                    if(entity.getY() > Main.WINDOW_HEIGHT - GameMap.TILE_SIZE) {
                        mapYOffset -= Main.WINDOW_HEIGHT;
                        entity.setY(0);
                        currentSectionY += Main.WINDOW_HEIGHT;
                    }
                    entity.setMapPositionY(entity.getMapPositionY() + 1);
                }
                break;
        }

        Optional<Item> mapItem = getMapItem(entity.getMapPositionX(), entity.getMapPositionY());
        if(mapItem.isPresent()) {
            Item item = mapItem.get();
            entity.getItems().add(item);
            mapItems.remove(item);
        }
    }

    private Optional<Item> getMapItem(float mapPositionX, float mapPositionY) {
        return mapItems.entrySet().stream()
                .filter(entry -> entry.getValue().getX() == mapPositionX && entry.getValue().getY() == mapPositionY)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void addMapItem(Item item, Point position) {
        this.mapItems.put(item, position);
    }
}