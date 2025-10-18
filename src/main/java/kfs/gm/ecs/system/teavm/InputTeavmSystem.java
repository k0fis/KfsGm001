package kfs.gm.ecs.system.teavm;

import kfs.gm.ecs.Entity;
import kfs.gm.ecs.World;
import kfs.gm.ecs.comp.Movement;
import kfs.gm.ecs.system.ISystem;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.KeyboardEvent;

public class InputTeavmSystem implements ISystem {

    private final World world;
    private final Entity player;
    private final String []cmds;

    public InputTeavmSystem(World world, Entity player, String cmds) {
        this.world = world;
        this.player = player;
        this.cmds = new String[] {
                cmds.substring(0,1),cmds.substring(1,2),cmds.substring(2,3),cmds.substring(3,4)
        };
    }

    @Override
    public void init() {
        Window.current().addEventListener("keydown", (EventListener<KeyboardEvent>) e -> {
            Movement movement = world.getComponent(player, Movement.class);
            String key = e.getKey();
            if (key.equals(cmds[0])) {
              movement.up = true;
            }
            if (key.equals(cmds[1])) {
                movement.down = true;
            }
            if (key.equals(cmds[2])) {
                movement.left = true;
            }
            if (key.equals(cmds[3])) {
                movement.right = true;
            }
            if ("q".equals(key)) {
                 //world.setStopTheWorld(true);
            }
        });

        Window.current().addEventListener("keyup", (EventListener<KeyboardEvent>) e -> {
            Movement movement = world.getComponent(player, Movement.class);
            String key = e.getKey();
            if (key.equals(cmds[0])) {
                movement.up = false;
            }
            if (key.equals(cmds[1])) {
                movement.down = false;
            }
            if (key.equals(cmds[2])) {
                movement.left = false;
            }
            if (key.equals(cmds[3])) {
                movement.right = false;
            }
        });
    }

}
