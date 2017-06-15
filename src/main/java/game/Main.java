package game;

import game.entities.EntityManager;
import game.entities.Player;
import game.input.InputHandler;
import game.item.Gun;
import game.item.Helmet;
import game.map.GameMap;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends BasicGame {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private EntityManager entityManager;
    private Player player;

    private GameMap gameMap;
    private InputHandler inputHandler;

    private Main(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gameMap = new GameMap();
        entityManager = new EntityManager();

        Gun gun = new Gun("gun", new Image("sprites/gun.png"));
        Helmet helmet = new Helmet("helmet", new Image("sprites/helmet.png"));
        gameMap.addMapItem(gun, new Point(22, 8));
        gameMap.addMapItem(helmet, new Point(39, 25));

        player = new Player(0, 0);
        entityManager.addGameEntity(player);
        
        inputHandler = new InputHandler(player, gameMap);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        inputHandler.handleUserInput(gc);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        gameMap.render(g);
        entityManager.render(g);

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
        }
        catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}