package worldnavigator.model.dao.mazedao.file;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import worldnavigator.model.dao.mazedao.MazeDAO;
import worldnavigator.model.maze.Maze;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public final class MazeDAOFileImpl implements MazeDAO {
  private final ObjectMapper mapper;

  private MazeDAOFileImpl() {
    this.mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
  }

  public static MazeDAOFileImpl createMazeDAOFileImpl() {
    return new MazeDAOFileImpl();
  }

  @Override
  public void saveMaze(Maze maze) {
    Objects.requireNonNull(maze, "The maze Object is null");
    File file =
            new File(
                    String.format(
                            "C:\\Users\\user\\Desktop\\Atypon\\Assignment01\\githubdemo\\world_navigator\\src\\main\\resources\\mazeFile\\%s.json",
                            maze.getMazeName()));
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, maze);
    } catch (IOException e) {
      Logger logger = Logger.getLogger("IO Exception");
      logger.info(e.getMessage());
    }
  }

  @Override
  public Optional<Maze> getMazeByName(String mazeName) {
    File file =
            new File(
                    String.format(
                            "C:\\Users\\user\\Desktop\\Atypon\\Assignment01\\githubdemo\\world_navigator\\src\\main\\resources\\mazeFile\\%s.json",
                            mazeName));

    Optional<Maze> mazeOptional = Optional.empty();
    try {
      mazeOptional = Optional.ofNullable(mapper.readValue(file, Maze.class));
    } catch (IOException e) {
      Logger logger = Logger.getLogger("IO Exception");
      logger.info(e.getMessage());
    }
    return mazeOptional;
  }
}
