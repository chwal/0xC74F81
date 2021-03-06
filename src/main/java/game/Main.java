package game;

import game.entities.Player;
import game.input.InputHandler;
import game.item.Ammo;
import game.item.Armor;
import game.item.Gun;
import game.item.Helmet;
import game.map.GameMap;
import game.map.MiniMap;
import game.map.population.TreePopulation;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends BasicGame {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static boolean debugMode = false;

    private Player player;

    private GameMap gameMap;
    private InputHandler inputHandler;

    private MiniMap miniMap;

    private Main(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gameMap = new GameMap();
        TreePopulation treePopulation = new TreePopulation();
        treePopulation.load();
        treePopulation.populateMap(gameMap);

        player = new Player(0, 0, new SpriteSheet("sprites/player_walking.png", 40, 40), 2);

        Gun gun = new Gun("gun", new Image("sprites/sub_gun.png"));
        Gun gun2 = new Gun("gun2", new Image("sprites/sniper_gun.png"));
        Helmet helmet = new Helmet("helmet", new Image("sprites/helmet.png"));
        Armor armor = new Armor("armor", new Image("sprites/armor.png"));
        Ammo ammo = new Ammo("ammo", new Image("sprites/ammo_case.png"));

        gameMap.addMapItem(gun, new Point(4, 21));
        gameMap.addMapItem(gun2, new Point(5, 21));
        gameMap.addMapItem(helmet, new Point(6, 21));
        gameMap.addMapItem(ammo, new Point(3, 22));
        gameMap.addMapItem(armor, new Point(5, 22));

        gameMap.addGameEntity(player);

        miniMap = new MiniMap(gameMap, player);
        inputHandler = new InputHandler(player, gameMap, miniMap);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        inputHandler.handleUserInput(gc, delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        gameMap.render(g);
        miniMap.render(g);

        if (debugMode) {
            g.setColor(Color.white);
            g.drawString("TileX: " + ((int) (player.getX() + 20) / 40) + ", TileY: " + ((int) (player.getY() + 20) / 40), 200, 10);
            g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 500, 10);
        }
    }

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.toLowerCase().contains("debug")) {
                debugMode = true;
            }
        }
        try {
            AppGameContainer appgc = new AppGameContainer(new Main("0xC74F81"));
            appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setVSync(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}