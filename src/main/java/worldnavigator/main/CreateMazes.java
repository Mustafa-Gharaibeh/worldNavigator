package worldnavigator.main;

import worldnavigator.model.dao.mazedao.MazeDAO;
import worldnavigator.model.dao.mazedao.file.MazeDAOFileImpl;
import worldnavigator.model.gamer.Direction;
import worldnavigator.model.material.Material;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;
import worldnavigator.model.material.item.flashlight.Flashlight;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.maze.Maze;
import worldnavigator.model.maze.MazeGraph;
import worldnavigator.model.room.Room;
import worldnavigator.model.wall.Wall;
import worldnavigator.model.wall.on_wall.chest.Chest;
import worldnavigator.model.wall.on_wall.door.Door;
import worldnavigator.model.wall.on_wall.mirror.Mirror;
import worldnavigator.model.wall.on_wall.nothing.PlainWall;
import worldnavigator.model.wall.on_wall.painting.Painting;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.HashMap;
import java.util.Map;

public class CreateMazes {

    public static void main(String[] args) {
        MazeDAO mazeDAO = MazeDAOFileImpl.createMazeDAOFileImpl();
        mazeDAO.saveMaze(maze1());
        Maze maze = mazeDAO.getMazeByName("maze1").get();
        System.out.println(maze.getMazeName());
        System.out.println(maze.getCurrentRoom().getRoomLightingStatus().isARoomDark());
    }

    private static Maze maze1() {
        Key redKey = Key.createKey("red key", Gold.createGold(2));
        Key greenKey = Key.createKey("green key", Gold.createGold(1));
        Key finalKey = Key.createKey("final key", Gold.createGold(4));
        Key chestKey = Key.createKey("chest key", Gold.createGold(1));

        Wall w11 = Wall.createWall(Door.createUnLockDoor(Boolean.FALSE, "First Door"));
        Wall w12 = Wall.createWall(Painting.createPaintingHiddenKey(redKey));
        Wall w13 = Wall.createWall(PlainWall.createPlainWall());
        Wall w14 = Wall.createWall(Mirror.createMirrorHasHiddenKey(greenKey));
        Room r1 = Room.createRoomWithLights("Room1", createMapWalls(w11, w12, w13, w14));

        Wall w21 = Wall.createWall(Door.createLockDoor(finalKey, Boolean.TRUE, "Final Door"));
        Wall w22 = Wall.createWall(Door.createLockDoor(redKey, Boolean.FALSE, "Red Door"));
        Wall w23 = w11;
        Wall w24 = Wall.createWall(Door.createLockDoor(greenKey, Boolean.FALSE, "Green Door"));
        Room r2 = Room.createRoomWithLights("Room2", createMapWalls(w21, w22, w23, w24));

        Map<String, Item> itemMap = new HashMap<>();
        Item item = Flashlight.createFlashlight("flashlight", Gold.createGold(3));
        itemMap.put(item.name(), item);
        Wall w31 = Wall.createWall(Painting.createPaintingHiddenKey(chestKey));
        Wall w32 = Wall.createWall(Seller.createSeller(itemMap));
        Wall w33 = Wall.createWall(PlainWall.createPlainWall());
        Wall w34 = w22;
        Room r3 = Room.createRoomWithLights("Room3", createMapWalls(w31, w32, w33, w34));

        Map<String, Material> materialMap = new HashMap<>();
        materialMap.put(finalKey.name(), finalKey);
        Wall w41 = Wall.createWall(Chest.createChest(chestKey, materialMap));
        Wall w42 = w24;
        Wall w43 = Wall.createWall(PlainWall.createPlainWall());
        Wall w44 = Wall.createWall(PlainWall.createPlainWall());
        Room r4 = Room.createRoomWithoutLights("Room4", createMapWalls(w41, w42, w43, w44));

        MazeGraph mazeGraph = MazeGraph.createMazeGraph(r1, r1, r2, r3, r4);
        mazeGraph.addEdge(r1, r2);
        mazeGraph.addEdge(r2, r3);
        mazeGraph.addEdge(r2, r1);
        mazeGraph.addEdge(r2, r4);
        mazeGraph.addEdge(r3, r2);
        mazeGraph.addEdge(r4, r2);

        return Maze.createMaze("maze1", Gold.createGold(6), 5, mazeGraph);
    }

    private static Map<Direction, Wall> createMapWalls(Wall w1, Wall w2, Wall w3, Wall w4) {
        Map<Direction, Wall> wallMap = new HashMap<>();
        wallMap.put(Direction.NORTH, w1);
        wallMap.put(Direction.EAST, w2);
        wallMap.put(Direction.SOUTH, w3);
        wallMap.put(Direction.WEST, w4);
        return wallMap;
    }
}
