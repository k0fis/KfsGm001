package kfs.gm.ecs.system.collision;

public class RectCollider implements Collider {
    double x, y, width, height;

    public RectCollider(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public boolean collidesWith(Collider other) {
        if (other instanceof RectCollider) {
            RectCollider o = (RectCollider)other;
            return !(x + width < o.x || x > o.x + o.width ||
                    y + height < o.y || y > o.y + o.height);
        } else if (other instanceof CircleCollider) {
            CircleCollider o = (CircleCollider)other;
            return o.collidesWith(this); // reuse
        }
        return false;
    }
}

