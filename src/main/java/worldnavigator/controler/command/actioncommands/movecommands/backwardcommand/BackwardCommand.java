package worldnavigator.controler.command.actioncommands.movecommands.backwardcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.maze.Maze;
import worldnavigator.model.room.Room;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.door.Door;

import java.util.Objects;
import java.util.Optional;

public final class BackwardCommand implements Command {
    private final Maze maze;
    private final Gamer gamer;
    private final Printer printer;

    private BackwardCommand(Maze maze, Gamer gamer, Printer printer) {
        this.maze = maze;
        this.gamer = gamer;
        this.printer = printer;
    }

    public static BackwardCommand createBackwardCommand(Maze maze, Gamer gamer, Printer printer) {
        return new BackwardCommand(
                Objects.requireNonNull(maze),
                Objects.requireNonNull(gamer),
                Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.getOnWall().typeOnWall().isLockable()) {
            if (this.getOnWall().typeOnWall().equals(TypeOnWall.DOOR)) {
                if (this.getOnWall().getLockable().isLock()) {
                    this.printer.print("The Door is Lock");
                } else if (this.getOppositeDirectionRoom().isPresent()) {
                    this.getOppositeDirectionRoom().ifPresent(this.maze::setCurrentRoom);
                    this.printer.print(String.format("You in %s", this.maze.getCurrentRoom().getRoomName()));
                }
            } else {
                this.printer.print(
                        String.format(
                                "You can not use Backward Command %s", "because the Opposite wall has not Door"));
            }
        } else {
            this.printer.print(
                    String.format(
                            "You can not use Backward Command %s", "because the Opposite wall has not Door"));
        }
    }

    private OnWall getOnWall() {
        return this.maze.getCurrentRoom().getWalls().get(this.gamer.getOppositeDirection()).getOnWall();
    }

    private Optional<Room> getOppositeDirectionRoom() {
        for (Room room : this.maze.getMazeGraph().adj(this.maze.getCurrentRoom())) {
            OnWall onWall = room.getWalls().get(this.gamer.getCurrentDirection()).getOnWall();
            if (onWall.typeOnWall().equals(TypeOnWall.DOOR)
                    && ((Door) getOnWall()).getDoorName().equals(((Door) onWall).getDoorName())) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }
}
