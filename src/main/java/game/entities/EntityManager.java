package game.entities;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entity> gameEntities;

    public EntityManager() {
        this.gameEntities = new ArrayList<>();
    }

    public void addGameEntity(Entity entity) {
        this.gameEntities.add(entity);
    }

    public void render(Graphics g) {
        gameEntities.forEach(gameEntity -> gameEntity.render(g));
    }

    public void update(int delta) {
        gameEntities.forEach(gameEntity -> gameEntity.update(delta));
    }
}
