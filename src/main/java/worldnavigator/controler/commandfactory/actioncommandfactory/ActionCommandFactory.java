package worldnavigator.controler.commandfactory.actioncommandfactory;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.controler.command.actioncommands.Action;
import worldnavigator.controler.command.actioncommands.movecommands.backwardcommand.BackwardCommand;
import worldnavigator.controler.command.actioncommands.movecommands.forwardcommand.ForwardCommand;
import worldnavigator.controler.command.actioncommands.orentationcommands.leftcommand.LeftCommand;
import worldnavigator.controler.command.actioncommands.orentationcommands.rightcommand.RightCommand;
import worldnavigator.controler.command.incorrectcommand.IncorrectCommand;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.maze.Maze;

public class ActionCommandFactory {
    private final Maze maze;
    private final Gamer gamer;
    private final Printer printer;

    public ActionCommandFactory(Maze maze, Gamer gamer, Printer printer) {
        this.maze = maze;
        this.gamer = gamer;
        this.printer = printer;
    }

    private Action getTypeOfAction(String arg) {
        if ("left".equals(arg)) {
            return Action.LEFT;
        } else if ("right".equals(arg)) {
            return Action.RIGHT;
        } else if ("forward".equals(arg)) {
            return Action.FORWARD;
        } else {
            return Action.BACKWARD;
        }
    }

    public Command createActionCommand(String arg) {
        Action action = this.getTypeOfAction(arg);
        if (Action.LEFT.equals(action) || Action.RIGHT.equals(action)) {
            return orientationCommand(action);
        } else if (Action.FORWARD.equals(action) || Action.BACKWARD.equals(action)) {
            return moveCommand(action);
        }
        return IncorrectCommand.createIncorrectCommand(this.printer);
    }

    private Command orientationCommand(Action action) {
        if (Action.LEFT.equals(action)) {
            return LeftCommand.createLeftCommand(this.gamer, this.printer);
        } else {
            return RightCommand.createRightCommand(this.gamer, this.printer);
        }
    }

    private Command moveCommand(Action action) {
        if (Action.FORWARD.equals(action)) {
            return ForwardCommand.createForwardCommand(this.maze, this.gamer, this.printer);
        } else {
            return BackwardCommand.createBackwardCommand(this.maze, this.gamer, this.printer);
        }
    }
}
