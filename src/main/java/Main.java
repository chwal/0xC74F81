import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends BasicGame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int TILE_SIZE = 40;
    private Player player;

    private TiledMap tiledMap;
    private int xOffset;
    private int yOffset;

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
            player.setX(player.getX() + TILE_SIZE);
            if (player.getX() > WIDTH - TILE_SIZE) {
                xOffset = xOffset - WIDTH;
                player.setX(0);
            }
        } else if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
            player.setX(player.getX() - TILE_SIZE);
            if (player.getX() < 0) {
                xOffset = xOffset + WIDTH;
                player.setX(WIDTH - TILE_SIZE);
            }
        } else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            player.setY(player.getY() + TILE_SIZE);
            if (player.getY() > HEIGHT - TILE_SIZE) {
                yOffset = yOffset - HEIGHT;
                player.setY(0);
            }
        } else if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            player.setY(player.getY() - 40);
            if (player.getY() < 0) {
                yOffset = yOffset + HEIGHT;
                player.setY(HEIGHT - TILE_SIZE);
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        tiledMap.render(xOffset, yOffset);
        g.drawImage(player, player.getX(), player.getY());
        g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 20, 20);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Main("Test"));
            appgc.setDisplayMode(WIDTH, HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setVSync(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}