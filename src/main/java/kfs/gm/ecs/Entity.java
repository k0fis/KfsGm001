package kfs.gm.ecs;

public class Entity {

    private final long id;
    private boolean active;

    public Entity(long id) {
        this.id = id;
        this.active = true;
    }

    public long id() {
        return id;
    }

    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Entity)) return false;
        return id == ((Entity)o).id;
    }
    @Override public int hashCode(){ return Long.hashCode(id); }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
