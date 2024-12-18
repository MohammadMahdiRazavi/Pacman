package org.example.pacman.ghost;


import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Targets two blocks ahead of pacman
public class Pinky extends Ghost {
    private int moveCounter;
    private Cell prevCell = null;
    private final int height, width;

    public Pinky(int x, int y, Game.Direction dir, Group root, int blockSize, int height, int width) {
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        this.height = height;
        this.width = width;
        setSpeed(dir);
        imageViewSetUp();
        downImage = new Image("file:src\\main\\resources\\pinky\\down.gif");
        upImage = new Image("file:src\\main\\resources\\pinky\\up.gif");
        rightImage = new Image("file:src\\main\\resources\\pinky\\right.gif");
        leftImage = new Image("file:src\\main\\resources\\pinky\\left.gif");
        updateImage();
        root.getChildren().add(imageView);
    }

    protected Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction dir) {
        List<Game.Direction> valid = validDirections(currentCell);
        List<Game.Direction> best = new ArrayList<>();
        List<Integer> aheadCoordinates = twoBlocksAhead(xPac, yPac, dir);
        int xAheadPac = aheadCoordinates.get(0);
        int yAheadPac = aheadCoordinates.get(1);
        //either U or D
        if (xAheadPac == x) {
            if (yAheadPac < y) {
                if (valid.contains(Game.Direction.U))
                    return Game.Direction.U;
            } else {
                if (valid.contains(Game.Direction.D))
                    return Game.Direction.D;
            }
        }

        //either L or R
        if (yAheadPac == y) {
            if (xAheadPac < x) {
                if (valid.contains(Game.Direction.L))
                    return Game.Direction.L;
            } else {
                if (valid.contains(Game.Direction.R))
                    return Game.Direction.R;
            }
        }

        //either R or U
        if (xAheadPac > x && yAheadPac < y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either R or D
        if (xAheadPac > x && yAheadPac > y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or D
        if (xAheadPac < x && yAheadPac > y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or U
        if (xAheadPac < x && yAheadPac < y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        return valid.get(new Random().nextInt(valid.size()));
    }

    private ArrayList<Integer> twoBlocksAhead(int xPac, int yPac, Game.Direction direction) {
        ArrayList<Integer> result = new ArrayList<>();
        switch (direction) {
            case D -> {
                result.add(xPac);
                result.add(Math.min(yPac + blockSize * 2, height));
            }
            case U -> {
                result.add(xPac);
                result.add(Math.max(yPac - blockSize * 2, 0));
            }
            case R -> {
                result.add(Math.min(xPac + blockSize * 2, width));
                result.add(yPac);
            }
            case L -> {
                result.add(Math.max(xPac - blockSize * 2, 0));
                result.add(yPac);
            }
        }
        return result;
    }
}