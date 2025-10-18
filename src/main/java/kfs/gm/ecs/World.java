package kfs.gm.ecs;

import kfs.gm.ecs.system.ISystem;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class World {

    private boolean stopTheWorld = false;

    private long nextId = 0;

    private final Map<Class<?>, Map<Entity, Component>> components = new HashMap<>();

    private final List<ISystem> updatableSystemList = new ArrayList<>();

    public synchronized Entity createEntity() {
        return new Entity(nextId++);
    }

    public <T extends Component> void removeComponent(Entity e, T component) {
        components
                .computeIfAbsent(component.getClass(), k -> new HashMap<>())
                .remove(e, component);
    }

    public <T extends Component> void addComponent(Entity e, T component) {
        components
                .computeIfAbsent(component.getClass(), k -> new HashMap<>())
                .put(e, component);
    }

    public <T extends Component> T getComponent(Entity e, Class<T> type) {
        return type.cast(components.getOrDefault(type, Map.of()).get(e));
    }

    public List<Entity> getEntitiesWith(Class<? extends Component> c1) {
        return new ArrayList<>(components.getOrDefault(c1, Map.of()).keySet());
    }
    public List<Entity> getEntitiesWith(Class<? extends Component> c1, Class<? extends Component> c2) {
        Set<Entity> ids1 = components.getOrDefault(c1, Map.of()).keySet();
        Set<Entity> ids2 = components.getOrDefault(c2, Map.of()).keySet();

        List<Entity> result = new ArrayList<>();
        for (Entity id : ids1) {
            if (ids2.contains(id)) result.add(id);
        }
        return result;
    }

    public List<Entity> getEntitiesWith(Class<? extends Component> c1, Class<? extends Component> c2, Class<? extends Component> c3) {
        Set<Entity> ids1 = components.getOrDefault(c1, Map.of()).keySet();
        Set<Entity> ids2 = components.getOrDefault(c2, Map.of()).keySet();
        Set<Entity> ids3 = components.getOrDefault(c3, Map.of()).keySet();

        List<Entity> result = new ArrayList<>();
        for (Entity id : ids1) {
            if (ids2.contains(id) && ids3.contains(id))
                result.add(id);
        }
        return result;
    }

    public void add(ISystem updatableSystem) {
        updatableSystemList.add(updatableSystem);
    }
    public void remove(ISystem updatableSystem) {
        updatableSystemList.remove(updatableSystem);
    }
    public void initSystems() {
        updatableSystemList.forEach(ISystem::init);
    }
    public void doneSystems() {
        updatableSystemList.forEach(ISystem::done);
    }
    public void updateSystems() {
        updatableSystemList.forEach(ISystem::update);
    }

    public boolean isStopTheWorld() {
        return stopTheWorld;
    }

    public void setStopTheWorld(boolean stopTheWorld) {
        this.stopTheWorld = stopTheWorld;
    }
}
