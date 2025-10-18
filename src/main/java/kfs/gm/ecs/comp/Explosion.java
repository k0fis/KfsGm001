package kfs.gm.ecs.comp;

import kfs.gm.ecs.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Explosion implements Component {

    public class Particle {
        public double x, y, vx, vy, life, maxLife;
        public String color;

        public Particle(double x, double y, double vx, double vy, double life, String color) {
            this.x = x; this.y = y;
            this.vx = vx; this.vy = vy;
            this.life = this.maxLife = life;
            this.color = color;
        }

        boolean alive() { return life > 0; }
    }

    List<Particle> parts = new ArrayList<>();

    public Explosion(double x, double y, int count) {
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            double angle = rnd.nextDouble() * 2 * Math.PI;
            double speed = 1 + rnd.nextDouble() * 2;
            parts.add(new Particle(
                    x, y,
                    Math.cos(angle) * speed,
                    Math.sin(angle) * speed,
                    2.0 + rnd.nextDouble(),
                    "orange"
            ));
        }
    }

    public void update(double dt) {
        for (Particle p : parts) {
            p.x += p.vx;
            p.y += p.vy;
            p.vx *= 0.97;
            p.vy *= 0.97;
            p.life -= dt;
        }
    }

    public boolean finished() {
        return parts.stream().noneMatch(Particle::alive);
    }

    public Stream<Particle> particleStream() {
        return parts.stream();
    }
}
