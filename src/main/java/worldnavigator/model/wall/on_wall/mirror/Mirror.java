package worldnavigator.model.wall.on_wall.mirror;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import worldnavigator.model.material.Material;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.NotLockable;
import worldnavigator.model.wall.on_wall.operation.acquire.AcquirableImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Value
public final class Mirror extends OnWall {

    @JsonCreator
    private Mirror(@JsonProperty("MaterialMap") Map<String, Material> map) {
        super(AcquirableImpl.createAcquirable(map), NotLockable.createNotLockable());
    }

    @JsonCreator
    private Mirror() {
        super(AcquirableImpl.createEmptyAcquirable(), NotLockable.createNotLockable());
    }

    public static Mirror createMirrorHasHiddenKey(Key item) {
        Objects.requireNonNull(item);
        Map<String, Material> map = new HashMap<>();
        map.put(item.name(), item);
        return new Mirror(map);
    }

    public static Mirror createMirrorHasNoHiddenKey() {
        return new Mirror();
    }

    @Override
    public TypeOnWall typeOnWall() {
        return TypeOnWall.MIRROR;
    }
}
