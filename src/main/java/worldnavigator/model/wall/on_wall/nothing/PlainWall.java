package worldnavigator.model.wall.on_wall.nothing;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.NotLockable;
import worldnavigator.model.wall.on_wall.operation.acquire.UnAcquirable;

@Value
public final class PlainWall extends OnWall {
    @JsonCreator
    private PlainWall() {
        super(UnAcquirable.createUnAcquirable(), NotLockable.createNotLockable());
    }

    public static PlainWall createPlainWall() {
        return new PlainWall();
    }

    @Override
    public TypeOnWall typeOnWall() {
        return TypeOnWall.NOTHING;
    }
}
