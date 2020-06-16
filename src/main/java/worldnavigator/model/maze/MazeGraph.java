package worldnavigator.model.maze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.room.Room;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class MazeGraph {

    @JsonProperty("firstKey")
    private final Room firstKey;

    @JsonProperty("keys")
    private final Room[] keys;

    @JsonProperty("map")
    private final Map<String, List<Room>> stringTMap;

    @JsonProperty("Edges")
    private Integer e;

    @JsonCreator
    private MazeGraph(
            @JsonProperty("firstKey") Room firstKey,
            @JsonProperty("keys") Room[] keys,
            @JsonProperty("map") Map<String, List<Room>> stringTMap) {
        this.firstKey = firstKey;
        this.keys = keys;
        this.stringTMap = stringTMap;
    }

    private MazeGraph(Room firstKey, Room... keys) {
        this.firstKey = firstKey;
        this.keys = keys;
        this.stringTMap = new LinkedHashMap<>();
        for (Room room : keys) {
            this.stringTMap.put(room.getRoomName(), new LinkedList<>());
        }
    }

    public static MazeGraph createMazeGraph(Room f, Room... keys) {
        return new MazeGraph(f, keys);
    }

    public Room getFirstKey() {
        return firstKey;
    }

    public int numOfVertices() {
        return keys.length;
    }

    public int numOfEdges() {
        return this.e;
    }

    public void addEdge(Room v, Room w) {
        if (!stringTMap.containsKey(v.getRoomName()) && !stringTMap.containsKey(w.getRoomName())) {
            throw new IllegalArgumentException();
        }
        this.stringTMap.get(v.getRoomName()).add(w);
        this.stringTMap.get(w.getRoomName()).add(v);
        e++;
    }

    public Iterable<Room> adj(Room v) {
        return this.stringTMap.get(v.getRoomName());
    }

    public int degree(Room v) {
        return this.stringTMap.get(v.getRoomName()).size();
    }
}
