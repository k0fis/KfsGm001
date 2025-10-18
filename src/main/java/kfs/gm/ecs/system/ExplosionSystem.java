package kfs.gm.ecs.system;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.Explosion;

public class ExplosionSystem implements ISystem {

    private final World world;
    private final double deltaTime;

    public ExplosionSystem (World world, double deltaTime) {
        this.world = world;
        this.deltaTime = deltaTime;
    }

    @Override
    public void update() {
        for (Entity e : world.getEntitiesWith(Explosion.class)) {
            Explosion explosion = world.getComponent(e, Explosion.class);
            if (explosion.finished()) {
                e.setActive(true);
                world.removeComponent(e, explosion);
            } else {
                explosion.update(deltaTime);
            }
        }
    }
}
