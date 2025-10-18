package kfs.gm.ecs.utils;

import java.util.concurrent.ThreadLocalRandom;

public class IntRange {
    private final int min;
    private final int max;

    public IntRange(int min, int max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        this.min = min;
        this.max = max;
    }

    public int getMin() { return min; }
    public int getMax() { return max; }

    public int random() {
        return random(min, max);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    public int fit(int value) {
        return fit(value, min, max);
    }

    public static int fit(int value, int min, int max) {
        if (value < min) {
            value = min;
        }
        if (value > max) {
            value = max;
        }
        return value;
    }
}
