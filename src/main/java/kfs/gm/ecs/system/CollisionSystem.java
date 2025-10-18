package kfs.gm.ecs.system;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.*;
import kfs.gm.ecs.system.collision.CircleCollider;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem implements ISystem{

    private final World world;

    public CollisionSystem(World world) {
        this.world = world;
    }

    @Override
    public void update(){
        List<CircleCollider> objs = new ArrayList<>();

        for(Entity e : world.getEntitiesWith(Position.class, Renderable.class)) {
            if (e.isActive()) {
                Position p = world.getComponent(e, Position.class);
                Renderable r = world.getComponent(e, Renderable.class);
                objs.add(new CircleCollider(e, p.getX(), p.getY(), r.collisionRadius()));
            }
        }

        for(Entity e : world.getEntitiesWith(Position.class, Renderable.class, Movement.class)){
            if (e.isActive()) {
                Position p = world.getComponent(e, Position.class);
                Renderable r = world.getComponent(e, Renderable.class);
                Movement m = world.getComponent(e, Movement.class);
                CircleCollider player = new CircleCollider(e, p.getX(), p.getY(), r.collisionRadius());
                for (CircleCollider obj: objs) {
                    if (e == obj.entity) continue;
                    if (player.collidesWith(obj)) {
                        Gem gem = world.getComponent(obj.entity, Gem.class);
                        obj.entity.setActive(false);
                        world.addComponent(obj.entity, new RandomPosition());
                        if (gem != null) {
                            r.scaleUp();
                            m.speedUp();
                            world.addComponent(obj.entity, new Explosion(p.getX(), p.getY(), 4));
                        } else {
                            e.setActive(false);
                            r.scaleDown();
                            m.speedDown();
                            world.addComponent(e, new Explosion(p.getX(), p.getY(), 19));
                            world.addComponent(e, new RandomPosition());
                            world.addComponent(obj.entity, new Explosion(p.getX(), p.getY(), 9));
                            world.getComponent(obj.entity, Renderable.class).scaleUp();
                        }
                    }
                }
            }
        }
    }


}
