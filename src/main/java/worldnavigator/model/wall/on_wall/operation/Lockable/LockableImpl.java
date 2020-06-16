package worldnavigator.model.wall.on_wall.operation.Lockable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.material.item.key.Key;

import java.util.Objects;

public final class LockableImpl implements Lockable {
    @JsonProperty("lockKey")
    private final Key lockKey;

    @JsonProperty("lock")
    private Boolean lock;

    @JsonCreator
    private LockableImpl(@JsonProperty("lockKey") Key lockKey, @JsonProperty("lock") Boolean lock) {
        this.lockKey = lockKey;
        this.lock = lock;
    }

    public static LockableImpl createLockable(Key lockKey) {
        if (Objects.nonNull(lockKey) && "open".equalsIgnoreCase(lockKey.name()))
            return new LockableImpl(lockKey, Boolean.FALSE);
        else return new LockableImpl(lockKey, Boolean.TRUE);
    }

    @Override
    public void useKey(Key key) {
        if (this.lockKey.name().equalsIgnoreCase(key.name())) {
            this.lock = !this.lock;
        }
    }

    @Override
    public boolean isLock() {
        return this.lock;
    }

    @Override
    public String status() {
        if (this.isLock()) {
            return String.format("It is Locked and need %s to open", this.lockKey.name());
        }
        return "It is open";
    }
}
