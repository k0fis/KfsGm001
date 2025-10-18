package kfs.gm.ecs.system;

public interface ISystem {

    default void init() {}
    default void done() {}

    default void update() {}
}
