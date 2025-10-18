package kfs.gm;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.*;
import kfs.gm.ecs.system.*;
import kfs.gm.ecs.system.teavm.InputTeavmSystem;
import kfs.gm.ecs.system.teavm.OutputTeavmSystem;
import kfs.gm.ecs.utils.DoubleRange;

public class GmMain {
    private static final int sleepTime = 50;

    private final World world;
    private final IOutputSystem outputSystem;

    GmMain(){
        world = new World();
        outputSystem = new OutputTeavmSystem(world);
    }

    Position randomPosition(){
        return Position.createRandom(20, outputSystem.getWidth() - 20,
                20, outputSystem.getHeight() - 20);
    }

    void createEnemy(Renderable.Type type, DoubleRange velocityRangeX, DoubleRange velocityRangeY) {
        Entity enemy = world.createEntity();
        world.addComponent(enemy, randomPosition());
        world.addComponent(enemy, new Velocity(velocityRangeX, velocityRangeY));
        world.addComponent(enemy, new Renderable( "#FF0000", new DoubleRange(0.5, 2), 0.5, type));
        world.addComponent(enemy, new RandomMoving(15, 75));
    }

    void createGem() {
        Entity gem = world.createEntity();
        world.addComponent(gem, randomPosition());
        world.addComponent(gem, new Renderable("", new DoubleRange(14, 26), 15, Renderable.Type.gem));
        world.addComponent(gem, new Gem(1, 15, 25));
    }

    Entity createPlayer(String color) {
        Entity player1 = world.createEntity();
        world.addComponent(player1, new Movement());
        world.addComponent(player1, randomPosition());
        world.addComponent(player1, new Renderable(color, new DoubleRange(6, 20), 8, Renderable.Type.pacman));
        return player1;
    }

    DoubleRange getSymetricalRange(double val) {
        return new DoubleRange(-1*val, val);
    }

    GmMain init(){
        Entity player1 = createPlayer("#00FF00");
        //Entity player2 = createPlayer("#0000FF");

        createEnemy(Renderable.Type.pumpkin, getSymetricalRange(1.4), getSymetricalRange(1.4));
        createEnemy(Renderable.Type.ghost, getSymetricalRange(1.), getSymetricalRange(1.));
        createEnemy(Renderable.Type.bat, getSymetricalRange(2.4), getSymetricalRange(2.4));
        createEnemy(Renderable.Type.bat, getSymetricalRange(2.4), getSymetricalRange(2.4));
        createEnemy(Renderable.Type.bat, getSymetricalRange(2.4), getSymetricalRange(2.4));
        createEnemy(Renderable.Type.cat, getSymetricalRange(0.2), getSymetricalRange(0.0001));
        createEnemy(Renderable.Type.spider, getSymetricalRange(0.0001), getSymetricalRange(0.6));
        createEnemy(Renderable.Type.spider, getSymetricalRange(0.0001), getSymetricalRange(0.6));
        createGem();createGem();createGem();createGem();

        //first
        world.add(new InputTeavmSystem(world, player1, "wsad"));
        //world.add(new InputTeavmSystem(world, player2, "ikjl"));

        world.add(new MovementSystem(world));
        world.add(new RandomMovementSystem(world));
        world.add(new VelocitySystem(world));
        world.add(new CollisionSystem(world));
        world.add(new ExplosionSystem(world, sleepTime/1100.0));
        //last
        world.add(outputSystem);

        return this;
    }

    void run() throws InterruptedException {
        try {
            world.initSystems();
            while (!world.isStopTheWorld()) {
                world.updateSystems();
                Thread.sleep(sleepTime);
            }
        } finally {
            world.doneSystems();
        }
    }

    public static void main(String []aa) throws InterruptedException {
        new GmMain().init().run();
    }

}