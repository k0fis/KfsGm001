package kfs.gm.ecs.system;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.Movement;
import kfs.gm.ecs.comp.Position;

public class MovementSystem implements ISystem {

    private final World world;

    public MovementSystem(World world) {
        this.world = world;
    }

    @Override
    public void update() {
        for (Entity e : world.getEntitiesWith(Position.class, Movement.class)) {
            if (e.isActive()) {
                Position p = world.getComponent(e, Position.class);
                Movement m = world.getComponent(e, Movement.class);
                if (m.left) {
                    p.setX(p.getX() - m.speed());
                }
                if (m.right) {
                    p.setX(p.getX() + m.speed());
                }
                if (m.up) {
                    p.setY(p.getY() - m.speed());
                }
                if (m.down) {
                    p.setY(p.getY() + m.speed());
                }
            }
        }
    }
}
