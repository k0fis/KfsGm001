package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;
import kfs.gm.ecs.utils.IntRange;

public class RandomMoving implements Component {

    private final IntRange tickRange;
    private int tickChange;

    public RandomMoving(int minTickRange, int maxTickRange) {
        this.tickRange = new IntRange(minTickRange, maxTickRange);
        this.tickChange = tickRange.random();
    }

    public int getTickChange() {
            return tickChange;
    }

    public void setTickChange() {
        this.tickChange = tickRange.random();
    }
    public void setTickChange(int tickChange) {
        this.tickChange = tickRange.fit(tickChange);
    }

}
