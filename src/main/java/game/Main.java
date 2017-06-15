package game;

import game.entities.EntityManager;
import game.entities.Player;
import game.input.InputHandler;
import game.item.Gun;
import game.item.Helmet;
import game.map.GameMap;
import game.map.TreePopulation;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends BasicGame {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private EntityManager entityManager;
    private Player player;

    private GameMap gameMap;
    private InputHandler inputHandler;

    private TreePopulation treePopulation;

    private Main(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gameMap = new GameMap();
        entityManager = new EntityManager();
        treePopulation = new TreePopulation();
        treePopulation.load();
        treePopulation.populateMap(gameMap);

        Gun gun = new Gun("gun", new Image("sprites/gun.png"));
        Helmet helmet = new Helmet("helmet", new Image("sprites/helmet.png"));
        gameMap.addMapItem(gun, new Point(22, 8));
        gameMap.addMapItem(helmet, new Point(0, 40));

        player = new Player(0, 0, new SpriteSheet("sprites/player_walking.png", 40, 40), 2);
        entityManager.addGameEntity(player);

        inputHandler = new InputHandler(player, gameMap);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        inputHandler.handleUserInput(gc);
        entityManager.update(delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        gameMap.render(g);
        entityManager.render(g);

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

        //debugging
        g.setColor(Color.white);
        g.drawString("X: " + player.getX() / GameMap.TILE_SIZE + ", Y: " + player.getY() / GameMap.TILE_SIZE, 100, 10);
        g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 300, 10);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc = new AppGameContainer(new Main("Test"));
            appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setVSync(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}