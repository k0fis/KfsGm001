package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;

import java.util.concurrent.ThreadLocalRandom;

public class Gem implements Component {

    private final double scale;
    private final int width;
    private final int height;
    private final double angleAddon;
    private double angle;

    public Gem(double scale, int width, int height) {
        this.scale = scale;
        this.width = width;
        this.height = height;
        angleAddon = ThreadLocalRandom.current().nextDouble(-0.02, 0.02);
    }

    public double scale() {
        return scale;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double angle() {
        return angle;
    }

    public void updateAngle() {
        angle+=angleAddon;
        if (angle > Math.PI * 2) angle = 0;
    }
}
