package worldnavigator.main.classgame;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.controler.commandfactory.CommandFactory;
import worldnavigator.model.dao.mazedao.MazeDAO;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.maze.Maze;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.door.Door;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ClassGame {
    private static final Map<String, List<String>> commands = new HashMap<>();

    static {
        List<String> defaultList =
                List.of(
                        "left", "right", "look", "quit", "restart", "status", "switchLights", "use flashlight");
        commands.put("default", defaultList);

        List<String> doorList =
                List.of(
                        "left",
                        "right",
                        "look",
                        "quit",
                        "restart",
                        "status",
                        "switchLights",
                        "use flashlight",
                        "check door",
                        "open",
                        "use <name> key",
                        "forward");
        commands.put(TypeOnWall.DOOR.getCheckName(), doorList);

        List<String> mirrorList =
                List.of(
                        "left",
                        "right",
                        "look",
                        "quit",
                        "restart",
                        "status",
                        "switchLights",
                        "use flashlight",
                        "check mirror");
        commands.put(TypeOnWall.MIRROR.getCheckName(), mirrorList);

        List<String> paintingList =
                List.of(
                        "left",
                        "right",
                        "look",
                        "quit",
                        "restart",
                        "status",
                        "switchLights",
                        "use flashlight",
                        "check painting");
        commands.put(TypeOnWall.PAINTING.getCheckName(), paintingList);

        List<String> chestList =
                List.of(
                        "left",
                        "right",
                        "look",
                        "quit",
                        "restart",
                        "status",
                        "switchLights",
                        "use flashlight",
                        "check chest",
                        "use <name> key");
        commands.put(TypeOnWall.CHEST.getCheckName(), chestList);

        List<String> sellerList =
                List.of(
                        "left",
                        "right",
                        "look",
                        "quit",
                        "restart",
                        "status",
                        "switchLights",
                        "use flashlight",
                        "trade");
        commands.put(TypeOnWall.SELLER.getCheckName(), sellerList);

        List<String> tradeList = List.of("buy <item>", "sell <item>", "list", "finish trade");
        commands.put("trade", tradeList);
    }

    private final Maze maze;
    private final Gamer gamer;
    private final LocalTime finishTime;
    private final Printer printer;
    private final MazeDAO mazeDAO;
    private String finishingDetails;
    private Boolean isDone;
    private Boolean isTrade;

    private ClassGame(String mazeName, String gamerName, MazeDAO mazeDAO, Printer printer) {
        this.mazeDAO = mazeDAO;
        this.printer = printer;
        this.maze =
                mazeDAO
                        .getMazeByName(Objects.requireNonNull(mazeName))
                        .orElseThrow(NullPointerException::new);
        this.gamer =
                Gamer.createGamer(Objects.requireNonNull(gamerName), this.maze.getInitialGamerGold());
        this.finishTime = LocalTime.now().plusMinutes(this.maze.getPlayingTimeMin());
        this.isDone = false;
        this.isTrade = false;
    }

    public static ClassGame createClassGame(
            String mazeName, String gamerName, MazeDAO mazeDAO, Printer printer) {
        return new ClassGame(mazeName, gamerName, mazeDAO, printer);
    }

    public void inputCommand(String arg) {
        if ("quit".equalsIgnoreCase(arg)) {
            this.isDone = Boolean.TRUE;
            this.setFinishingDetails("You Lost!");
            return;
        }
        CommandFactory commandFactory =
                CommandFactory.createCommandFactory(this.maze, this.gamer, this.printer);
        Command command = commandFactory.createCommand(arg);
        command.execute();
    }

    public List<String> commandList(String arg) {
        List<String> list = commands.get("default");
        if (!this.maze.getCurrentRoom().getRoomLightingStatus().isARoomLit()) {
            return list;
        }
        setIsTrade(arg);
        boolean isSell = false;
        if (frontOnWall().typeOnWall().equals(TypeOnWall.SELLER) && !isTrade) {
            list = commands.get("seller");
            isSell = true;
        } else if (isTrade) {
            list = commands.get("trade");
            isSell = true;
        }
        if (isSell) {
            return list;
        }
        boolean isMakeOrientation = "left".equals(arg) || "right".equals(arg);
        if (!frontOnWall().typeOnWall().equals(TypeOnWall.NOTHING)
                && this.maze.getCurrentRoom().getRoomLightingStatus().isARoomLit()) {
            list = commands.get(frontOnWall().typeOnWall().getCheckName());
        }
        if (this.oppositeOnWall().typeOnWall().equals(TypeOnWall.DOOR) && !isMakeOrientation) {
            list =
                    List.of(
                            "left",
                            "right",
                            "look",
                            "quit",
                            "restart",
                            "status",
                            "switchLights",
                            "use flashlight",
                            "backward");
            if (frontOnWall().typeOnWall().equals(TypeOnWall.DOOR)) {
                list =
                        List.of(
                                "left",
                                "right",
                                "look",
                                "quit",
                                "restart",
                                "status",
                                "switchLights",
                                "use flashlight",
                                "check door",
                                "open",
                                "use <name> key",
                                "forward",
                                "backward");
            }
        }
        return list;
    }

    public boolean isDone() {
        if (this.finishTime.compareTo(LocalTime.now()) <= 0) {
            this.setFinishingDetails("Time Out!");
            return true;
        }
        if (isFinalDoorAndOpen()) {
            this.setFinishingDetails("You Win!");
            return true;
        }
        return isDone;
    }

    private boolean isFinalDoorAndOpen() {
        return frontOnWall().typeOnWall().equals(TypeOnWall.DOOR)
                && ((Door) frontOnWall()).isFinalDoor()
                && !frontOnWall().getLockable().isLock();
    }

    private boolean isTradeMode() {
        return isTrade;
    }

    private void setIsTrade(String arg) {
        if ("trade".equals(arg) && frontOnWall().typeOnWall().equals(TypeOnWall.SELLER)) {
            this.isTrade = Boolean.TRUE;
            return;
        }
        if (isTradeMode() && "finish trade".trim().equals(arg.trim())) {
            this.isTrade = Boolean.FALSE;
            return;
        }
        boolean isContain = false;
        for (String s : commands.get("trade")) {
            isContain = (s.equals(arg) || arg.contains("buy") || arg.contains("sell"));
            if (isContain) break;
        }
        isTrade = isTradeMode() && isContain;
    }

    private OnWall frontOnWall() {
        return this.maze.getCurrentRoom().getWalls().get(this.gamer.getCurrentDirection()).getOnWall();
    }

    private OnWall oppositeOnWall() {
        return this.maze.getCurrentRoom().getWalls().get(this.gamer.getOppositeDirection()).getOnWall();
    }

    private void setFinishingDetails(String finishingDetails) {
        this.finishingDetails = finishingDetails;
    }

    public void printIfFinish() {
        this.printer.print(finishingDetails);
    }
}
