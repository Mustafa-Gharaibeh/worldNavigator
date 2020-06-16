package worldnavigator.model.wall.on_wall.chest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import worldnavigator.model.material.Material;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.LockableImpl;
import worldnavigator.model.wall.on_wall.operation.acquire.AcquirableImpl;

import java.util.Map;
import java.util.Objects;

@Value
public final class Chest extends OnWall {

    @JsonCreator
    private Chest(
            @JsonProperty("lockKey") Key lockKey,
            @JsonProperty("MaterialMap") Map<String, Material> map) {
        super(AcquirableImpl.createAcquirable(map), LockableImpl.createLockable(lockKey));
    }

    public static Chest createChest(Key lockKey, Map<String, Material> map) {
        return new Chest(Objects.requireNonNull(lockKey), Objects.requireNonNull(map));
    }

    @Override
    public TypeOnWall typeOnWall() {
        return TypeOnWall.CHEST;
    }
}
