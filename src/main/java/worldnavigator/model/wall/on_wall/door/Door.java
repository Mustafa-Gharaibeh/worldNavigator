package worldnavigator.model.wall.on_wall.door;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.LockableImpl;
import worldnavigator.model.wall.on_wall.operation.acquire.UnAcquirable;

import java.util.Objects;

@Value
public final class Door extends OnWall {

    @JsonProperty("finalDoor")
    private final Boolean isFinalDoor;

    @JsonProperty("DoorName")
    private final String doorName;

    @JsonCreator
    private Door(
            @JsonProperty("lockKey") Key lockKey,
            @JsonProperty("finalDoor") Boolean isFinalDoor,
            @JsonProperty("DoorName") String doorName) {
        super(UnAcquirable.createUnAcquirable(), LockableImpl.createLockable(lockKey));
        this.isFinalDoor = isFinalDoor;
        this.doorName = doorName;
    }

    public static Door createLockDoor(Key lockKey, Boolean isFinalDoor, String doorName) {
        return new Door(
                Objects.requireNonNull(lockKey),
                Objects.requireNonNull(isFinalDoor),
                Objects.requireNonNull(doorName));
    }

    public static Door createUnLockDoor(Boolean isFinalDoor, String doorName) {
        return new Door(
                Key.createKey("Open", Gold.createGold(0)),
                Objects.requireNonNull(isFinalDoor),
                Objects.requireNonNull(doorName));
    }

    public String getDoorName() {
        return doorName;
    }

    public Boolean isFinalDoor() {
        return isFinalDoor;
    }

    @Override
    public TypeOnWall typeOnWall() {
        return TypeOnWall.DOOR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Door door = (Door) o;
        return Objects.equals(doorName, door.doorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doorName);
    }
}
