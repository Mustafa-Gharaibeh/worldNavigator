package worldnavigator.model.wall.on_wall.painting;

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
public final class Painting extends OnWall {

  @JsonCreator
  private Painting(@JsonProperty("MaterialMap") Map<String, Material> map) {
    super(AcquirableImpl.createAcquirable(map), NotLockable.createNotLockable());
  }

  public Painting() {
    super(AcquirableImpl.createEmptyAcquirable(), NotLockable.createNotLockable());
  }

  public static Painting createPaintingHiddenKey(Key hiddenKey) {
    Objects.requireNonNull(hiddenKey);
    Map<String, Material> map = new HashMap<>();
    map.put(hiddenKey.name(), hiddenKey);
    return new Painting(map);
  }

  public static Painting createPaintingHasNoHiddenKey() {
    return new Painting();
  }

  @Override
  public TypeOnWall typeOnWall() {
    return TypeOnWall.PAINTING;
  }
}
