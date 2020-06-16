package worldnavigator.model.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class RoomLightingStatus {

    @JsonProperty("isARoomHasLights")
    private final Boolean isARoomHasLights;

    @JsonIgnore
    private Boolean isARoomLit;

    @JsonCreator
    private RoomLightingStatus(@JsonProperty("isARoomHasLights") boolean isARoomHasLights) {
        this.isARoomLit = Boolean.FALSE;
        this.isARoomHasLights = isARoomHasLights;
    }

    public static RoomLightingStatus createRoomLightingStatus(Boolean isARoomHasLights) {
        return new RoomLightingStatus(isARoomHasLights);
    }

    @JsonIgnore
    public boolean isARoomLit() {
        return isARoomLit;
    }

    @JsonIgnore
    public boolean isARoomDark() {
        return !isARoomLit;
    }

    public void switchLights(boolean isARoomLit) {
        this.isARoomLit = isARoomLit;
    }

    @JsonIgnore
    public boolean isARoomHasLights() {
        return isARoomHasLights;
    }
}
