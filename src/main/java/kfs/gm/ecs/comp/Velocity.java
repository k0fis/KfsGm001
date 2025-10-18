package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;
import kfs.gm.ecs.utils.DoubleRange;

public class Velocity implements Component {
    private final DoubleRange rangeX;
    private final DoubleRange rangeY;
    private double dx;
    private double dy;

    public Velocity() {
        this(new DoubleRange(-2, 2), new DoubleRange(-2, 2));
    }

    public Velocity(DoubleRange rangeX, DoubleRange rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        random();
    }

    public double dx() {
        return dx;
    }
    public double dy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = rangeX.fit(dx);
    }

    public void setDy(double dy) {
        this.dy = rangeY.fit(dy);
    }

    public void random() {
        this.dx = this.rangeX.random();
        this.dy = this.rangeY.random();
    }
}
