package worldnavigator.model.wall.on_wall.operation.acquire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.material.Material;

import java.util.HashMap;
import java.util.Map;

public final class AcquirableImpl implements Acquirable {

    @JsonProperty("MaterialMap")
    private final Map<String, Material> materialMap;

    @JsonCreator
    private AcquirableImpl(@JsonProperty("MaterialMap") Map<String, Material> materialMap) {
        this.materialMap = materialMap;
    }

    public static AcquirableImpl createAcquirable(Map<String, Material> materialMap) {
        return new AcquirableImpl(materialMap);
    }

    public static AcquirableImpl createEmptyAcquirable() {
        return new AcquirableImpl(new HashMap<>());
    }

    @Override
    public Map<String, Material> acquire() {
        return this.materialMap;
    }
}
