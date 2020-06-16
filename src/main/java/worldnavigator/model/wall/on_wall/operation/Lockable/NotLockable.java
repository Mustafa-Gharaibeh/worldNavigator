package worldnavigator.model.wall.on_wall.operation.Lockable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import worldnavigator.model.material.item.key.Key;

@Value
public final class NotLockable implements Lockable {

    @JsonCreator
    public NotLockable() {
    }

    public static NotLockable createNotLockable() {
        return new NotLockable();
    }

    @Override
    public void useKey(Key key) {
    }

    @JsonIgnore
    @Override
    public boolean isLock() {
        return false;
    }

    @Override
    public String status() {
        return "NotLockable";
    }
}
