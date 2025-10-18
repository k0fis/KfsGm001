package kfs.gm.ecs.system.collision;

public interface Collider {
    boolean collidesWith(Collider other);
    boolean contains(double x, double y);
}
