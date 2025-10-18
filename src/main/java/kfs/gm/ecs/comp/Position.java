package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;
import kfs.gm.ecs.utils.IntRange;

public class Position implements Component {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return (int) (31.28 * x + y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(double dx, double dy) {
        x = x + dx;
        y = y + dy;
    }

    public void randomPosition(int xMin, int xMax, int yMin, int yMax) {
        setX(IntRange.random(xMin, xMax));
        setY(IntRange.random(yMin, yMax));
    }

    public static Position createRandom(int xMin, int xMax, int yMin, int yMax) {
        return new Position(IntRange.random(xMin, xMax), IntRange.random(yMin, yMax));
    }
}
