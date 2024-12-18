package org.example.pacman.ghost;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;


abstract public class Ghost {
    int x, y, dx, dy, blockSize;
    public static int moveUnit = 3;
    protected ImageView imageView;
    private int moveCounter, xTurnCell, yTurnCell;
    protected Image upImage, downImage, rightImage, leftImage;

    protected void setSpeed(Game.Direction dir) {
        if (dir == Game.Direction.D) {
            dx = 0;
            dy = moveUnit;
        } else if (dir == Game.Direction.U) {
            dx = 0;
            dy = -moveUnit;
        } else if (dir == Game.Direction.L) {
            dx = -moveUnit;
            dy = 0;
        } else if (dir == Game.Direction.R) {
            dx = moveUnit;
            dy = 0;
        }
    }

    abstract Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction pacDir);
    public void move(List<List<Cell>> map, int xPac, int yPac, Game.Direction pacDir) {
        int col = (x + blockSize / 2) / blockSize;
        int row = (y + blockSize / 2) / blockSize;
        Cell currentCell = map.get(row).get(col);
        Cell prevCell = currentCell;
        x += dx;
        y += dy;
        moveCounter++;
        col = (x + blockSize / 2) / blockSize;
        row = (y + blockSize / 2) / blockSize;
        currentCell = map.get(row).get(col);

        if(!prevCell.borders().equals(currentCell.borders())){
            xTurnCell = col * blockSize;
            yTurnCell = row * blockSize;
        }
        if (moveCounter > (blockSize / moveUnit) + 7) {
            if (Math.sqrt(Math.pow(y - yTurnCell, 2) + Math.pow(x - xTurnCell, 2)) < moveUnit) {
                moveCounter = 0;
                Game.Direction tmpDir = bestDir(xPac, yPac, currentCell, pacDir);
                setSpeed(tmpDir);
                y = row * blockSize;
                x = col * blockSize;
                updateImage();
            }
        }

        if (currentCell.isTopBordered()) {
            if (currentDirection().equals(Game.Direction.U)) {
                setSpeed(bestDir(xPac, yPac, currentCell, pacDir));
                y = row * blockSize;
                updateImage();
            }
        }
        if (currentCell.isBottomBordered()) {
            if (currentDirection().equals(Game.Direction.D)) {
                setSpeed(bestDir(xPac, yPac, currentCell, pacDir));
                y = row * blockSize;
                updateImage();
            }
        }
        if (currentCell.isLeftBordered()) {
            if (currentDirection().equals(Game.Direction.L)) {
                setSpeed(bestDir(xPac, yPac, currentCell, pacDir));
                x = col * blockSize;
                updateImage();
            }
        }
        if (currentCell.isRightBordered()) {
            if (currentDirection().equals(Game.Direction.R)) {
                setSpeed(bestDir(xPac, yPac, currentCell, pacDir));
                x = col * blockSize;
            }
        }


        imageView.setX(x);
        imageView.setY(y);
    }

    protected void imageViewSetUp() {
        imageView = new ImageView();
        imageView.setX(this.x);
        imageView.setY(this.y);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(blockSize);
    }

    protected void updateImage() {
        switch (currentDirection()){
            case U -> imageView.setImage(upImage);
            case D -> imageView.setImage(downImage);
            case L -> imageView.setImage(leftImage);
            case R -> imageView.setImage(rightImage);
        }
    }

    protected List<Game.Direction> validDirections(Cell currentCell) {
        List<Game.Direction> valid = new ArrayList<>();
        if (!currentCell.isRightBordered()) {
            valid.add(Game.Direction.R);
        }
        if (!currentCell.isLeftBordered()) {
            valid.add(Game.Direction.L);
        }
        if (!currentCell.isTopBordered()) {
            valid.add(Game.Direction.U);
        }
        if (!currentCell.isBottomBordered()) {
            valid.add(Game.Direction.D);
        }
        return valid;
    }

    protected Game.Direction currentDirection() {
        if (dx > 0) {
            return Game.Direction.R;
        }
        if (dx < 0) {
            return Game.Direction.L;
        }
        if (dy > 0) {
            return Game.Direction.D;
        }
        return Game.Direction.U;
    }

    public ImageView getImageView() {
        return imageView;
    }

    protected Game.Direction oppositeDirection(Game.Direction dir) {
        if (dir == Game.Direction.U)
            return Game.Direction.D;
        if (dir == Game.Direction.D)
            return Game.Direction.U;
        if (dir == Game.Direction.L)
            return Game.Direction.R;
        return Game.Direction.L;
    }

    public void removeFromScene(Group root){
        root.getChildren().remove(imageView);
    }

}