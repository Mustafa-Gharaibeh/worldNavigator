package worldnavigator.model.wall.on_wall.operation.acquire;

import com.fasterxml.jackson.annotation.JsonCreator;
import worldnavigator.model.material.Material;

import java.util.HashMap;
import java.util.Map;

public final class UnAcquirable implements Acquirable {

    @JsonCreator
    private UnAcquirable() {
    }

    public static UnAcquirable createUnAcquirable() {
        return new UnAcquirable();
    }

    @Override
    public Map<String, Material> acquire() {
        return new HashMap<>();
    }
}
