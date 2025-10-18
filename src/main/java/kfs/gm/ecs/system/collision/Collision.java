package kfs.gm.ecs.system.collision;

import kfs.gm.ecs.Entity;

public class Collision{

    private final Entity a;
    private final Entity b;
    public Collision(Entity a, Entity b) {
        this.a = a;
        this.b = b;
    }
    public Entity getA() {
        return a;
    }
    public Entity getB() {
        return b;
    }
}