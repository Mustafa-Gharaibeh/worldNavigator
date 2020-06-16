package worldnavigator.controler.command.actioncommands.movecommands.forwardcommand;

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

public final class ForwardCommand implements Command {
    private final Printer printer;
    private final Maze maze;
    private final Gamer gamer;

    private ForwardCommand(Maze maze, Gamer gamer, Printer printer) {
        this.maze = maze;
        this.gamer = gamer;
        this.printer = printer;
    }

    public static ForwardCommand createForwardCommand(Maze maze, Gamer gamer, Printer printer) {
        return new ForwardCommand(
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
                } else if (this.getNextRoom().isPresent()) {
                    this.getNextRoom().ifPresent(maze::setCurrentRoom);
                    this.printer.print(String.format("You in %s", this.maze.getCurrentRoom().getRoomName()));
                }
            } else {
                this.printer.print(
                        String.format(
                                "You can not use Forward Command %s", "because the front wall has not Door"));
            }
        } else {
            this.printer.print(
                    String.format(
                            "You can not use Forward Command %s", "because the front wall has not Door"));
        }
    }

    private OnWall getOnWall() {
        return this.maze.getCurrentRoom().getWalls().get(this.gamer.getCurrentDirection()).getOnWall();
    }

    private Optional<Room> getNextRoom() {
        for (Room room : this.maze.getMazeGraph().adj(this.maze.getCurrentRoom())) {
            OnWall onWall = room.getWalls().get(this.gamer.getOppositeDirection()).getOnWall();
            if (onWall.typeOnWall().equals(TypeOnWall.DOOR)
                    && ((Door) getOnWall()).getDoorName().equals(((Door) onWall).getDoorName())) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }
}
