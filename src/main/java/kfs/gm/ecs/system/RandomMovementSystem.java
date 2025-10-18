package kfs.gm.ecs.system;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.RandomMoving;
import kfs.gm.ecs.comp.Velocity;

public class RandomMovementSystem implements ISystem {

    private final World world;

    private long tick = 0;

    public RandomMovementSystem(World world) {
        this.world = world;
    }

    @Override
    public void update() {
        tick  = (tick + 1) % Integer.MAX_VALUE;
        for (Entity e : world.getEntitiesWith(RandomMoving.class, Velocity.class)) {
            RandomMoving rm = world.getComponent(e, RandomMoving.class);
            if (tick % rm.getTickChange() == 0) {
                world.getComponent(e, Velocity.class).random();
                rm.setTickChange();
            }
        }
    }
}
