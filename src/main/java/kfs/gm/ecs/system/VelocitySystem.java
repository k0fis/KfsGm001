package kfs.gm.ecs.system;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.Position;
import kfs.gm.ecs.comp.Velocity;

public class VelocitySystem implements ISystem {

    private final World world;

    public VelocitySystem(World world) {
        this.world = world;
    }

    @Override
    public void update() {
        for (Entity e : world.getEntitiesWith(Position.class, Velocity.class)) {
            Position p = world.getComponent(e, Position.class);
            Velocity v = world.getComponent(e, Velocity.class);
            p.add( v.dx(), v.dy());
        }
    }
}
