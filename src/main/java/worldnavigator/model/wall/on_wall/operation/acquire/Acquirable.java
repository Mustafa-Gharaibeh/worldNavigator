package worldnavigator.model.wall.on_wall.operation.acquire;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import worldnavigator.model.material.Material;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AcquirableImpl.class, name = "AcquirableImpl"),
        @JsonSubTypes.Type(value = UnAcquirable.class, name = "UnAcquirable")
})
public interface Acquirable {
    Map<String, Material> acquire();
}
