package org.example.pacman.ghost;


import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Follows pacman
public class Shadow extends Ghost{
    public Shadow(int x, int y, Game.Direction dir, Group root, int blockSize){
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        setSpeed(dir);
        imageViewSetUp();
        downImage = new Image("file:src\\main\\resources\\shadow\\down.gif");
        upImage = new Image("file:src\\main\\resources\\shadow\\up.gif");
        rightImage = new Image("file:src\\main\\resources\\shadow\\right.gif");
        leftImage = new Image("file:src\\main\\resources\\shadow\\left.gif");
        updateImage();
        root.getChildren().add(imageView);
    }

    @Override
    protected Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction dir) {
        List<Game.Direction> valid = validDirections(currentCell);
        List<Game.Direction> best = new ArrayList<>();

        //either U or D
        if (xPac == x) {
            if (yPac < y) {
                if (valid.contains(Game.Direction.U))
                    return Game.Direction.U;
            } else {
                if (valid.contains(Game.Direction.D))
                    return Game.Direction.D;
            }
        }

        //either L or R
        if (yPac == y) {
            if (xPac < x) {
                if (valid.contains(Game.Direction.L))
                    return Game.Direction.L;
            } else {
                if (valid.contains(Game.Direction.R))
                    return Game.Direction.R;
            }
        }

        //either R or U
        if (xPac > x && yPac < y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either R or D
        if (xPac > x && yPac > y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or D
        if (xPac < x && yPac > y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or U
        if (xPac < x && yPac < y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        return valid.get(new Random().nextInt(valid.size()));
    }

}