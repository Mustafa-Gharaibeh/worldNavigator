package worldnavigator.model.maze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.room.Room;

import java.util.Objects;

public final class Maze {

    @JsonProperty("mazeName")
    private final String mazeName;

    @JsonProperty("mazeGraph")
    private final MazeGraph mazeGraph;

    @JsonProperty("initialGamerGold")
    private final Gold initialGamerGold;

    @JsonProperty("playingTimeMin")
    private final Integer playingTimeMin;

    @JsonIgnore
    private Room currentRoom;

    @JsonCreator
    private Maze(
            @JsonProperty("mazeName") String mazeName,
            @JsonProperty("initialGamerGold") Gold initialGamerGold,
            @JsonProperty("playingTimeMin") Integer playingTimeMin,
            @JsonProperty("mazeGraph") MazeGraph mazeGraph) {
        this.mazeName = mazeName;
        this.mazeGraph = mazeGraph;
        this.currentRoom = mazeGraph.getFirstKey();
        this.initialGamerGold = initialGamerGold;
        this.playingTimeMin = playingTimeMin;
    }

    public static Maze createMaze(
            String mazeName, Gold initialGamerGold, Integer playingTimeMin, MazeGraph mazeGraph) {
        return new Maze(
                Objects.requireNonNull(mazeName),
                Objects.requireNonNull(initialGamerGold),
                Objects.requireNonNull(playingTimeMin),
                Objects.requireNonNull(mazeGraph));
    }

    public Integer getPlayingTimeMin() {
        return playingTimeMin;
    }

    public Gold getInitialGamerGold() {
        return initialGamerGold;
    }

    public MazeGraph getMazeGraph() {
        return mazeGraph;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getMazeName() {
        return mazeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        return Objects.equals(mazeName, maze.mazeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mazeName);
    }
}
