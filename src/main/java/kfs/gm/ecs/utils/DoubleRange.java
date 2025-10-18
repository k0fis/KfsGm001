package kfs.gm.ecs.utils;

import java.util.concurrent.ThreadLocalRandom;

public class DoubleRange {
    private final double min;
    private final double max;

    public DoubleRange(double min, double max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        this.min = min;
        this.max = max;
    }

    public double getMin() { return min; }
    public double getMax() { return max; }

    public double random() {
        if (min == max) return min;
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public boolean contains(double value) {
        return value >= min && value <= max;
    }

    public double fit(double value) {
        return fit(value, min, max);
    }

    public static double fit(double value, double min, double max) {
        if (value < min) {
            value = min;
        }
        if (value > max) {
            value = max;
        }
        return value;
    }
}
