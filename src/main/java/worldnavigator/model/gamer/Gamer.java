package worldnavigator.model.gamer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.material.Material;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;

import java.util.*;

public final class Gamer {
  private final String gamerName;
  private final Map<String, Material> materialMap;
  private final List<Direction> directions;
  private Direction currentDirection;
  private Direction oppositeDirection;

  @JsonCreator
  private Gamer(@JsonProperty String gamerName, @JsonProperty Map<String, Material> materialMap) {
    this.gamerName = gamerName;
    this.materialMap = materialMap;
    this.directions = new LinkedList<>();
    this.setDirections();
  }

  public static Gamer createGamer(String gamerName, Gold gold) {
    Objects.requireNonNull(gamerName);
    Objects.requireNonNull(gold);
    Map<String, Material> map = new HashMap<>();
    map.put(gold.name(), gold);
    return new Gamer(gamerName, map);
  }

  public static Gamer createGamer(String gamerName, Map<String, Material> materialMap) {
    return new Gamer(Objects.requireNonNull(gamerName), Objects.requireNonNull(materialMap));
  }

  public void addItem(Item item) {
    Objects.requireNonNull(item);
    this.materialMap.put(item.name().trim(), item);
  }

  public void addAmountOfGold(Integer amountOfGold) {
    ((Gold) this.materialMap.get("Gold")).setAmount(Objects.requireNonNull(amountOfGold));
  }

  public void addAmountOfGold(Gold gold) {
    Objects.requireNonNull(gold);
    ((Gold) this.materialMap.get("Gold")).setAmount(gold.getAmount());
  }

  public Optional<Item> deleteItem(String itemName) {
    return Optional.ofNullable((Item) this.materialMap.remove(itemName));
  }

  public Map<String, Material> getMaterialMap() {
    return materialMap;
  }

  public String getGamerName() {
    return gamerName;
  }

  public Direction getOppositeDirection() {
    return oppositeDirection;
  }

  private void setOppositeDirection(Direction oppositeDirection) {
    this.oppositeDirection = oppositeDirection;
  }

  public List<Direction> getDirections() {
    return directions;
  }

  public Direction getCurrentDirection() {
    return currentDirection;
  }

  public void setCurrentDirection(Direction currentDirection) {
    this.currentDirection = currentDirection;

    if (currentDirection.equals(Direction.NORTH)) {
      setOppositeDirection(Direction.SOUTH);
    } else if (currentDirection.equals(Direction.SOUTH)) {
      setOppositeDirection(Direction.NORTH);
    } else if (currentDirection.equals(Direction.EAST)) {
      setOppositeDirection(Direction.WEST);
    } else {
      setOppositeDirection(Direction.EAST);
    }
  }

  private void setDirections() {
    directions.add(Direction.NORTH);
    directions.add(Direction.EAST);
    directions.add(Direction.SOUTH);
    directions.add(Direction.WEST);
    this.setCurrentDirection(Direction.NORTH);
    this.setOppositeDirection(Direction.SOUTH);
  }
}
