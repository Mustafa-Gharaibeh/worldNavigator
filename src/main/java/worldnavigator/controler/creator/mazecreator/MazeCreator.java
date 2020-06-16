package worldnavigator.controler.creator.mazecreator;

import worldnavigator.model.dao.mazedao.MazeDAO;
import worldnavigator.model.maze.Maze;

import java.util.Objects;

public final class MazeCreator {
    private final MazeDAO mazeDAO;

    private MazeCreator(MazeDAO mazeDAO) {
        this.mazeDAO = mazeDAO;
    }

    public static MazeCreator createMazeCreator(MazeDAO mazeDAO) {
        return new MazeCreator(Objects.requireNonNull(mazeDAO));
    }

    public void saveMaze(Maze maze) {
        this.mazeDAO.saveMaze(Objects.requireNonNull(maze));
    }

    public Maze getMazeByName(String mazeName) {
        return this.mazeDAO.getMazeByName(mazeName).orElseThrow(NullPointerException::new);
    }
}
