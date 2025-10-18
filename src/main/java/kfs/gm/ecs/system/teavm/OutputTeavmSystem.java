package kfs.gm.ecs.system.teavm;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.*;
import kfs.gm.ecs.system.IOutputSystem;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;

public class OutputTeavmSystem implements IOutputSystem {

    private final World world;
    private final int border;
    private int height;
    private int width;
    private CanvasRenderingContext2D g;
    private HalloweenScene scene;

    public OutputTeavmSystem(World world) {
        this(world, 6, 400, 800);
    }

    public OutputTeavmSystem(World world, int border, int height, int width) {
        this.world = world;
        this.border = border;
        this.height = height;
        this.width = width;
    }

    @Override
    public void init() {
        HTMLDocument document = Window.current().getDocument();
        HTMLCanvasElement canvas = (HTMLCanvasElement) document.createElement("canvas");
        canvas.setWidth(width+border+border);
        canvas.setHeight(height+border+border);
        document.getBody().appendChild(canvas);

        world.add(new TouchInput(world, canvas));

        g = (CanvasRenderingContext2D) canvas.getContext("2d");
        scene = new HalloweenScene(g);

        canvas.addEventListener("resize", (EventListener<Event>) evt -> {
            width = canvas.getWidth();
            height = canvas.getHeight();
        });
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    @Override
    public void update() {
        g.setFillStyle("#3a3a3a");
        g.fillRect(0, 0, getWidth()+2.*border, getHeight()+2.*border);
        g.save();
        g.translate(border, border);

        scene.drawScene(width, height);

        for(Entity e : world.getEntitiesWith(Position.class, Renderable.class)) {
            if (e.isActive()) {
                Position p = world.getComponent(e, Position.class);
                Renderable r = world.getComponent(e, Renderable.class);
                double size2 = (r.size() + 1) / 2;
                fitPosition(p, size2);

                switch (r.type()) {
                    case pumpkin:
                        scene.drawPumpkin(p.getX(), p.getY(), r.size());
                        break;
                    case ghost:
                        scene.drawGhost(p.getX(), p.getY(), r.size());
                        break;
                    case bat:
                        scene.drawBat(p.getX(), p.getY(), r.size(), System.currentTimeMillis() / 150.0);
                        break;
                    case cat:
                        scene.drawCatAnimated(p.getX(), p.getY(), r.size(), System.currentTimeMillis() / 420.0);
                        break;
                    case spider:
                        scene.drawSpider(p.getX(), p.getY(), r.size());
                        break;
                    case pacman:
                        Movement m = world.getComponent(e, Movement.class);
                        Double angle = directionRad(m);
                        if (angle == null) {
                            angle = m.lastAngle;
                        }
                        m.lastAngle = angle;
                        drawPacman(p.getX(), p.getY(), r.size(), angle, r.color());

                        Missile missile = world.getComponent(e, Missile.class);
                        if (missile != null) {
                            drawMissile(missile.positionX, missile.positionY, missile.angle);
                        }
                        break;
                    case gem:
                        Gem gem = world.getComponent(e, Gem.class);
                        RenderGem.drawFancyDiamond(g, p.getX(), p.getY(), gem.width(), gem.height(), gem.angle());
                        gem.updateAngle();
                        break;
                }
            }
        }

        for(Entity e : world.getEntitiesWith(Explosion.class)) {
            Explosion explosion = world.getComponent(e, Explosion.class);
            explosion.particleStream().forEach(p->{
                double alpha = Math.max(0, p.life / p.maxLife);
                g.setGlobalAlpha(alpha);
                g.setFillStyle(p.color);
                g.beginPath();
                g.arc(p.x, p.y, 2, 0, 2 * Math.PI);
                g.fill();
            });
        }

        g.restore();

        for(Entity e : world.getEntitiesWith(RandomPosition.class, Position.class)) {
            world.getComponent(e, Position.class)
                    .randomPosition(20, getWidth() - 20, 20, getHeight() - 20);
            world.removeComponent(e, world.getComponent(e, RandomPosition.class));
        }
    }

    private void fitPosition(Position p, double size2) {
        if (p.getX() < border+size2) {
            p.setX(border+size2);
        }
        if (p.getX() > getWidth()-border-size2) {
            p.setX(getWidth()-border-size2);
        }
        if (p.getY() < border+size2) {
            p.setY(border+size2);
        }
        if (p.getY() > getHeight() - border - size2) {
            p.setY(getHeight() - border - size2);
        }
    }

    void drawPacman(double x, double y, double r,
                    double dir, String color) {

        double mouth = (Math.sin(System.currentTimeMillis() / 100.0) ) * 0.25 * Math.PI; // time -> clos and up

        double start = dir + mouth;
        double end = dir + (Math.PI * 2 - mouth);

        g.setFillStyle(color);
        g.beginPath();
        g.moveTo(x, y);
        g.arc(x, y, r, start, end);
        g.closePath();
        g.fill();
    }

    public static Double directionRad(Movement m) {
        if (m.right) {
            if (m.down) {
                return Math.PI / 4;
            }
            if (m.up) {
                return Math.PI / -4;
            }
            return 0.;
        }

        if (m.left) {
            if (m.up) {
                return Math.PI * 3 / -4;
            }
            if (m.down) {
                return Math.PI * 3 / 4;
            }
            return Math.PI;
        }
        if ( m.up   ) { return Math.PI/-2; }
        if ( m.down ) { return Math.PI/2; }

        return null;
    }

    int missileColor = 0;

    void drawMissile(double x, double y, double angle) {
        g.save();
        g.translate(x, y);
        g.rotate(angle);

        g.setFillStyle("white");
        // tělo rakety
        g.fillRect(-10, -5, 20, 10);

        // arrow
        g.beginPath();
        g.moveTo(10, -5);
        g.lineTo( 10,  5);
        g.lineTo( 15, 0);
        g.closePath();
        g.fill();

        missileColor = (missileColor + 1 )%3;
        // fire
        g.setFillStyle(missileColor==0?"orange":"red");
        g.beginPath();
        g.arc( -10,  0, 5, 0, Math.PI * 2);
        g.fill();

        g.setFillStyle(missileColor==0?"red":"orange");
        g.beginPath();
        g.arc( -12,  0, 5, 0, Math.PI * 2);
        g.fill();

        g.restore();
    }
}
