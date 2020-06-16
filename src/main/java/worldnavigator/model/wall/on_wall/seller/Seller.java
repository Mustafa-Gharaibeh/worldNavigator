package worldnavigator.model.wall.on_wall.seller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.NotLockable;
import worldnavigator.model.wall.on_wall.operation.acquire.UnAcquirable;
import worldnavigator.model.wall.on_wall.operation.seller_operation.BuyOperation;
import worldnavigator.model.wall.on_wall.operation.seller_operation.SellOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Value
public final class Seller extends OnWall implements BuyOperation, SellOperation {
  @JsonProperty("items")
  private final Map<String, Item> items;

  @JsonCreator
  private Seller(@JsonProperty("items") Map<String, Item> items) {
    super(UnAcquirable.createUnAcquirable(), NotLockable.createNotLockable());
    this.items = items;
  }

  public static Seller createSeller(Map<String, Item> items) {
    return new Seller(Objects.requireNonNull(items));
  }

  public static Seller createSellerHasNothing() {
    return new Seller(new HashMap<>());
  }

  @Override
  public Gold sell(Item item) {
    Objects.requireNonNull(item, "The item will non null");
    this.items.put(item.name(), item);
    return item.price();
  }

  @Override
  public Item buy(String name) {
    return this.items.remove(Objects.requireNonNull(name, "The name of item will non null"));
  }

  @Override
  public String toString() {
    return "Seller{" + "items=" + items + '}';
  }

  public Map<String, Item> getItems() {
    return this.items;
  }

  @Override
  public TypeOnWall typeOnWall() {
    return TypeOnWall.SELLER;
  }
}
