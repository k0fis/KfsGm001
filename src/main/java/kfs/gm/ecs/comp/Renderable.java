package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;
import kfs.gm.ecs.utils.DoubleRange;

public class Renderable implements Component {

    public enum Type {
        pacman,
        gem,
        pumpkin,
        ghost,
        bat,
        cat,
        spider
    }
    private final String color;
    private final DoubleRange sizeRange;
    private final Type type;
    private double size;


    public Renderable(String color, DoubleRange sizeRange, double size, Type type) {
        this.type = type;
        this.color = color;
        this.sizeRange = sizeRange;
        this.size = sizeRange.fit(size);
    }

    public String color() {return color;}

    public double size() {return size;}

    public void size(double size) {this.size = sizeRange.fit(size); }

    public void scaleDown() {
        size(size-0.1);
    }
    public void scaleUp() {
        size(size+0.1);
    }

    public Type type() {return type; }

    public double collisionRadius() {
        switch (type) {
            case pacman:  return size / 2;
            case pumpkin: return size * 20;
            case ghost:   return size * 13;
            case bat:     return size * 8;
            case spider:  return size * 5;
            case cat:     return size * 10;
            default:      return 1;
        }

    }
}
