package worldnavigator.model.wall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.wall.on_wall.OnWall;

public final class Wall {
  @JsonProperty("onWall")
  private final OnWall onWall;

  @JsonCreator
  private Wall(@JsonProperty("onWall") OnWall onWall) {
    this.onWall = onWall;
  }

  public static Wall createWall(OnWall onWall) {
    return new Wall(onWall);
  }

  public OnWall getOnWall() {
    return onWall;
  }

  @Override
  public String toString() {
    return "Wall{" + "onWall=" + onWall + '}';
  }
}
