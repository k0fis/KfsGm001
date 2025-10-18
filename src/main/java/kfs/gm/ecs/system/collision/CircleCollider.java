package kfs.gm.ecs.system.collision;

import kfs.gm.ecs.Entity;

public class CircleCollider implements Collider {
    double x, y, radius;
    public Entity entity;

    public CircleCollider(Entity entity, double x, double y, double radius) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean contains(double px, double py) {
        double dx = px - x;
        double dy = py - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    public boolean collidesWith(Collider other) {
        if (other instanceof CircleCollider) {
            CircleCollider o = (CircleCollider) other;
            double dx = x - o.x;
            double dy = y - o.y;
            double r = radius + o.radius;
            return dx * dx + dy * dy <= r * r;
        } else if (other instanceof RectCollider) {
            RectCollider o = (RectCollider) other;
            // nejbližší bod z čtverce k tomuto kruhu
            double closestX = clamp(x, o.x, o.x + o.width);
            double closestY = clamp(y, o.y, o.y + o.height);
            double dx = x - closestX;
            double dy = y - closestY;
            return dx * dx + dy * dy <= radius * radius;
        }
        return false;
    }

    private double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}

