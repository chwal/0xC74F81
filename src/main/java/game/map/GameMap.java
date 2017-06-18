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

import java.util.*;

public class GameMap {
    private static final int SECTION_DIMENSION_X = 2;
    private static final int SECTION_DIMENSION_Y = 4;
    private static final int MAP_HEIGHT = Main.WINDOW_HEIGHT * SECTION_DIMENSION_Y;
    private static final int MAP_WIDTH = Main.WINDOW_WIDTH * SECTION_DIMENSION_X;
    private static final int TILE_SIZE = 40;

    private TiledMap tiledMap;

    private int mapXOffset;
    private int mapYOffset;

    private int staticCollisionLayer;
    private int blockedTilesLayer;

    private Map<Point, Item> mapItems;
    private Map<Point, GameObject> mapObjects;
    private List<Entity> gameEntities;

    private float currentSectionX;
    private float currentSectionY;

    public GameMap() throws SlickException {
        mapXOffset = 0;
        mapYOffset = 0;
        gameEntities = new ArrayList<>();
        tiledMap = new TiledMap("maps/map.tmx");
        staticCollisionLayer = tiledMap.getLayerIndex("StaticCollisionLayer");
        blockedTilesLayer = tiledMap.getLayerIndex("BlockedTilesLayer");
        mapItems = new HashMap<>();
        mapObjects = new HashMap<>();
        currentSectionX = 0;
        currentSectionY = 0;
    }

    public void update(int delta) {
        gameEntities.forEach(gameEntity -> gameEntity.update(delta, this));
    }

    public void render(Graphics g) {
        tiledMap.render(mapXOffset, mapYOffset);

        renderGameEntities(g);
        renderMapObjects();
        renderMapItems(g);
    }

    private void renderMapItems(Graphics g) {
        Color temp = g.getColor();
        g.setColor(Color.yellow);

        mapItems.forEach((tilePosition, item) -> {
            float actualX = tilePosition.getX() * 40;
            float actualY = tilePosition.getY() * 40;

            if(isCurrentlyVisible(actualX, actualY)) {
                actualX %= Main.WINDOW_WIDTH;
                actualY %= Main.WINDOW_HEIGHT;

                g.drawRect(actualX, actualY, 40, 40);
                item.render(g, actualX, actualY, false);
            }
        });

        g.setColor(temp);
    }

    private void renderMapObjects() {
        mapObjects.forEach((tilePosition, mapObject) -> {
            float actualX = tilePosition.getX() * 40;
            float actualY = tilePosition.getY() * 40;

            if(isCurrentlyVisible(actualX, actualY)) {
                actualX %= Main.WINDOW_WIDTH;
                actualY %= Main.WINDOW_HEIGHT;
                mapObject.getImage().draw(actualX, actualY);
            }
        });
    }

    private void renderGameEntities(Graphics g) {
        gameEntities.forEach(gameEntity -> {
            float x = gameEntity.getX();
            float y = gameEntity.getY();

            if(isCurrentlyVisible(x, y)) {
                x %= Main.WINDOW_WIDTH;
                y %= Main.WINDOW_HEIGHT;
                gameEntity.render(g, x, y);
            }
        });
    }

    private boolean isCurrentlyVisible(float x, float y) {
        return (currentSectionX + Main.WINDOW_WIDTH > x && currentSectionX <= x) && (currentSectionY + Main.WINDOW_HEIGHT > y && currentSectionY <= y);
    }

    public void moveEntity(Entity entity, Direction direction, int delta) {
        float oldXPos = entity.getX();
        float oldYPos = entity.getY();
        float newXPos = oldXPos;
        float newYPos = oldYPos;

        if(direction.equals(Direction.EAST) || direction.equals(Direction.WEST))
            entity.setFacing(direction);

        entity.setMoving(true);

        float distanceCovered = (entity.getVelocity() / 1000) * delta;
        switch (direction) {
            case NORTH:
                newYPos -= distanceCovered;
                break;
            case EAST:
                newXPos += distanceCovered;
                break;
            case SOUTH:
                newYPos += distanceCovered;
                break;
            case WEST:
                newXPos -= distanceCovered;
                break;
        }

        //map restriction
        //TODO: Fix bug: switching sections upwards and downwards when there's collision
        if((newXPos >= 0 && newXPos <= MAP_WIDTH - TILE_SIZE) && (newYPos >= 0 && newYPos <= MAP_HEIGHT - TILE_SIZE)) {
            //collision detection
            if(!isColliding(newXPos, newYPos)) {
                entity.setX(newXPos);
                entity.setY(newYPos);

                if(oldXPos < newXPos && (newXPos + 40) % Main.WINDOW_WIDTH < oldXPos % Main.WINDOW_WIDTH && !isColliding(newXPos + GameMap.TILE_SIZE, newYPos)) {
                    mapXOffset -= Main.WINDOW_WIDTH;
                    entity.setX(entity.getX() + GameMap.TILE_SIZE);
                    currentSectionX += Main.WINDOW_WIDTH;
                } else if(oldXPos > newXPos && newXPos % Main.WINDOW_WIDTH > oldXPos % Main.WINDOW_WIDTH && !isColliding(newXPos - GameMap.TILE_SIZE, newYPos)) {
                    mapXOffset += Main.WINDOW_WIDTH;
                    entity.setX(entity.getX() - GameMap.TILE_SIZE);
                    currentSectionX -= Main.WINDOW_WIDTH;
                } else if(oldYPos < newYPos && (newYPos + 40) % Main.WINDOW_HEIGHT < oldYPos % Main.WINDOW_HEIGHT && !isColliding(newXPos, newYPos + GameMap.TILE_SIZE)) {
                    mapYOffset -= Main.WINDOW_HEIGHT;
                    entity.setY(entity.getY() + GameMap.TILE_SIZE);
                    currentSectionY += Main.WINDOW_HEIGHT;
                } else if(oldYPos > newYPos && (newYPos) % Main.WINDOW_HEIGHT > oldYPos % Main.WINDOW_HEIGHT && !isColliding(newXPos, newYPos - GameMap.TILE_SIZE)) {
                    mapYOffset += Main.WINDOW_HEIGHT;
                    entity.setY(entity.getY() - GameMap.TILE_SIZE);
                    currentSectionY -= Main.WINDOW_HEIGHT;
                }
            }

            //TODO: Increase performance (e.g. only check for items when actually changing layer position)
            Optional<Map.Entry<Point, Item>> optMapItem = getMapItem(entity.getTilePositionX(), entity.getTilePositionY());
            if(optMapItem.isPresent()) {
                Map.Entry<Point, Item> itemEntry = optMapItem.get();
                entity.getItems().add(itemEntry.getValue());
                mapItems.remove(itemEntry.getKey());
            }
        }
    }

    //TODO: Rework collision detection for dynamic game objects (improve intersection detection, use whole rectangle instead of 2 fixpoints)
    private boolean isColliding(float newXPos, float newYPos) {
        int tileX = (((int) (newXPos + 10) / 40));
        int tileY = (((int) (newYPos + 10) / 40));
        int tileX2 = (((int) (newXPos + 30) / 40));
        int tileY2 = (((int) (newYPos + 30) / 40));

        return isStaticCollisionTile(tileX, tileY) || isDynamicCollisionTile(tileX, tileY) || isStaticCollisionTile(tileX2, tileY2) || isDynamicCollisionTile(tileX2, tileY2);
    }

    //TODO: Increase performance (make use of HashMap hashcode implementation - no iteration required)
    public boolean isDynamicCollisionTile(int xPos, int yPos) {
        return mapObjects.entrySet().stream()
                .filter(entry -> entry.getKey().getX() == xPos && entry.getKey().getY() == yPos)
                .filter(entry -> entry.getValue().isCollision())
                .findFirst()
                .orElse(null) != null;
    }

    public boolean isStaticCollisionTile(int tileX, int tileY) {
        return tiledMap.getTileId(tileX, tileY, staticCollisionLayer) != 0;
    }

    public boolean isBlockedTile(int tileX, int tileY) {
        return tiledMap.getTileId(tileX, tileY, blockedTilesLayer) != 0;
    }

    public void addGameEntity(Entity entity) {
        gameEntities.add(entity);
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

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
