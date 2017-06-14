package game;

import java.util.logging.Level;
import java.util.logging.Logger;

import game.entities.Entity;
import game.entities.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends BasicGame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int TILE_SIZE = 40;
    private Player player;

    private TiledMap tiledMap;
    private static int xOffset;
    private static int yOffset;

    public Main(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        xOffset = 0;
        yOffset = 0;
        tiledMap = new TiledMap("maps/map.tmx");
        player = new Player(0, 0);
    }

    /**
     * Gameloop
     * 1. Update
     * 2. Render
     */
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
            moveEntity(player, 'R');
        } else if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
            moveEntity(player, 'L');
        } else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            moveEntity(player, 'D');
        } else if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            moveEntity(player, 'U');
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        tiledMap.render(xOffset, yOffset);
        g.drawImage(player, player.getX(), player.getY());
        g.drawString("X: " + player.getX() / TILE_SIZE + ", Y: " + player.getY() / TILE_SIZE, 20, 20);
    }

    public static void moveEntity(Entity entity, char direction){
        switch (direction) {
            case 'R':
                entity.setX(entity.getX() + TILE_SIZE);
                if (entity.getX() > WIDTH - TILE_SIZE) {
                    xOffset = xOffset - WIDTH;
                    entity.setX(0);
                }
                break;
            case 'L':
                entity.setX(entity.getX() - TILE_SIZE);
                if (entity.getX() < 0) {
                    xOffset = xOffset + WIDTH;
                    entity.setX(WIDTH - TILE_SIZE);
                }
                break;
            case 'U':
                entity.setY(entity.getY() - 40);
                if (entity.getY() < 0) {
                    yOffset = yOffset + HEIGHT;
                    entity.setY(HEIGHT - TILE_SIZE);
                }
                break;
            case 'D':
                entity.setY(entity.getY() + TILE_SIZE);
                if (entity.getY() > HEIGHT - TILE_SIZE) {
                    yOffset = yOffset - HEIGHT;
                    entity.setY(0);
                }
                break;
            default:
        }
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Main("Test"));
            appgc.setDisplayMode(WIDTH, HEIGHT, false);
            appgc.setTargetFrameRate(30);
            appgc.setVSync(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}