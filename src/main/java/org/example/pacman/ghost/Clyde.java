package org.example.pacman.ghost;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.pacman.app.Game;
import org.example.pacman.map.*;

import java.util.List;
import java.util.Random;

//Moves randomly
public class Clyde extends Ghost {

    public Clyde(int x, int y, Game.Direction dir, Group root, int blockSize) {
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        setSpeed(dir);
        imageViewSetUp();
        downImage = new Image("file:src\\main\\resources\\clyde\\down.gif");
        upImage = new Image("file:src\\main\\resources\\clyde\\up.gif");
        rightImage = new Image("file:src\\main\\resources\\clyde\\right.gif");
        leftImage = new Image("file:src\\main\\resources\\clyde\\left.gif");
        updateImage();
        root.getChildren().add(imageView);
    }

    protected Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction dir) {
        List<Game.Direction> valid = validDirections(currentCell);
        Game.Direction currentDir = currentDirection();
        Game.Direction oppositeDir = oppositeDirection(currentDir);
        if (valid.contains(oppositeDir) && valid.size() > 1) {
            valid.remove(oppositeDir);
        }
        if (valid.contains(currentDir) && valid.size() > 1) {
            valid.remove(currentDir);
        }
        return valid.get(new Random().nextInt(valid.size()));
    }

}
