package worldnavigator.model.material.item.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;

import java.util.Objects;

@Value
public final class Key implements Item {
  @JsonProperty("keyName")
  private final String keyName;

  @JsonProperty("price")
  private final Gold price;

  @JsonCreator
  private Key(@JsonProperty("keyName") String keyName, @JsonProperty("price") Gold price) {
    this.keyName = keyName;
    this.price = price;
  }

  public static Key createKey(String keyName, Gold price) {
    if (Objects.nonNull(keyName) && Objects.nonNull(price)) {
      if (price.getAmount() >= 0) {
        return new Key(keyName, price);
      } else {
        return new Key(keyName, Gold.createGold(0));
      }
    }
    return new Key(
            Objects.requireNonNull(keyName, "The key name will non null"),
            Objects.requireNonNull(price, "The price will non null"));
  }

  @Override
  public Gold price() {
    return this.price;
  }

  @Override
  public String name() {
    return this.keyName;
  }

  @Override
  public String toString() {
    return "Key{" + "KEY_NAME='" + keyName + '\'' + ", price=" + price + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Key key = (Key) o;
    return keyName.equals(key.keyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keyName);
  }
}
