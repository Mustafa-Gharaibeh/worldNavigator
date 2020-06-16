package worldnavigator.model.wall.on_wall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import worldnavigator.model.wall.on_wall.chest.Chest;
import worldnavigator.model.wall.on_wall.door.Door;
import worldnavigator.model.wall.on_wall.mirror.Mirror;
import worldnavigator.model.wall.on_wall.nothing.PlainWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.Lockable;
import worldnavigator.model.wall.on_wall.operation.acquire.Acquirable;
import worldnavigator.model.wall.on_wall.painting.Painting;
import worldnavigator.model.wall.on_wall.seller.Seller;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Seller.class, name = "Seller"),
        @JsonSubTypes.Type(value = Painting.class, name = "Painting"),
        @JsonSubTypes.Type(value = PlainWall.class, name = "PlainWall"),
        @JsonSubTypes.Type(value = Mirror.class, name = "Mirror"),
        @JsonSubTypes.Type(value = Door.class, name = "Door"),
        @JsonSubTypes.Type(value = Chest.class, name = "Chest")
})
public abstract class OnWall {
    @JsonProperty("acquirable")
    private final Acquirable acquirable;

    @JsonProperty("lockable")
    private final Lockable lockable;

    @JsonCreator
    protected OnWall(
            @JsonProperty("acquirable") Acquirable acquirable,
            @JsonProperty("lockable") Lockable lockable) {
        this.acquirable = acquirable;
        this.lockable = lockable;
    }

    public Acquirable getAcquirable() {
        return acquirable;
    }

    public Lockable getLockable() {
        return lockable;
    }

    public abstract TypeOnWall typeOnWall();
}
