package org.example.pacman.ghost;


import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.List;
import java.util.Random;

//Protects a certain area of the map
public class Inky extends Ghost{

    public Inky(int x, int y, Game.Direction dir, Group root, int blockSize){
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        setSpeed(dir);
        imageViewSetUp();
        downImage = new Image("file:src\\main\\resources\\inky\\down.gif");
        upImage = new Image("file:src\\main\\resources\\inky\\up.gif");
        rightImage = new Image("file:src\\main\\resources\\inky\\right.gif");
        leftImage = new Image("file:src\\main\\resources\\inky\\left.gif");
        root.getChildren().add(imageView);
    }

    protected Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction dir) {
        List<Game.Direction> valid = validDirections(currentCell);
        Game.Direction currentDir = currentDirection();
        Game.Direction oppositeDir = oppositeDirection(currentDir);
        if (valid.contains(oppositeDir) && valid.size() > 1) {
            valid.remove(oppositeDir);
        }
        if(valid.contains(Game.Direction.L)) {
            return Game.Direction.L;
        }
        if(valid.contains(Game.Direction.D)){
            return Game.Direction.D;
        }
        return valid.get(new Random().nextInt(valid.size()));
    }

}