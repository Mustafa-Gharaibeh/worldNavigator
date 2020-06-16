package worldnavigator.model.material.gold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.material.Material;

import java.util.Objects;

public final class Gold implements Material, Comparable<Integer> {

    @JsonProperty("amount")
    private Integer amount;

    @JsonCreator
    private Gold(Integer amount) {
        this.amount = amount;
    }

    public static Gold createGold(Integer amount) {
        if (Objects.nonNull(amount)) {
            if (amount >= 0) {
                return new Gold(amount);
            } else {
                return new Gold(0);
            }
        }
        Objects.requireNonNull(amount, "The amount of Gold will non null");
        return new Gold(0);
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = this.amount + amount;
    }

    @Override
    public String toString() {
        return "Gold{" + "amount=" + amount + '}';
    }

    @Override
    public int compareTo(Integer o) {
        return this.amount.compareTo(o);
    }

    @Override
    public String name() {
        return "Gold";
    }
}
