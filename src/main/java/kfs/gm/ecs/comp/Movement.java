package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;
import kfs.gm.ecs.utils.DoubleRange;

public class Movement implements Component {
    private final DoubleRange speedRange;
    public boolean up, down, left, right;
    private double speed;
    public double lastAngle = 0;

    public Movement(boolean up, boolean down, boolean left, boolean right, DoubleRange speedRange, double speed) {
        this.speedRange = speedRange;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.speed = speed;
    }
    public Movement(DoubleRange speedRange, double speed) {
        this(false, false, false, false, speedRange, speed);
    }

    public Movement(){
        this(new DoubleRange(0.2, 5), 2);
    }

    public double speed() {
        return speed;
    }

    public void speed(double speed) {
        this.speed = speedRange.fit(speed);
    }

    public void speedUp() {
        speed(speed+0.1);
    }
    public void speedDown() {
        speed(speed-0.1);
    }

}
