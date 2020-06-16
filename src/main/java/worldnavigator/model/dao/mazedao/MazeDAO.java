package worldnavigator.model.dao.mazedao;

import worldnavigator.model.maze.Maze;

import java.util.Optional;

public interface MazeDAO {
    void saveMaze(Maze maze);

    Optional<Maze> getMazeByName(String mazeName);
}
