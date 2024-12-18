package org.example.pacman.app;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import org.example.pacman.ghost.Clyde;
import org.example.pacman.ghost.Inky;
import org.example.pacman.ghost.Pinky;
import org.example.pacman.ghost.Shadow;
import org.example.pacman.map.Cell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public enum Direction {U, R, D, L, NA}

    private final int row, col, blockSize;
    private final Group root;
    private final List<List<Cell>> map = new ArrayList<>();
    private final List<Cell> nonWallCells = new ArrayList<>();
    private final List<Shadow> shadows = new ArrayList<>();
    private final List<Clyde> clydes = new ArrayList<>();
    private final List<Pinky> pinkies = new ArrayList<>();
    private final List<Inky> inkies = new ArrayList<>();
    private Pacman pacman;
    private boolean eatenHealthBooster = false, visibleHealthBooster = false;
    public static int remainingDot;

    public Game(int blockSize, Group root) throws Exception {
        this.blockSize = blockSize;
        this.root = root;
        remainingDot = 0;
        Point2D tmp = populateMap();
        row = (int) tmp.getX();
        col = (int) tmp.getY();
    }

    private Point2D populateMap() throws Exception {
        File file = new File("src\\main\\resources\\map.txt");
        Scanner sc = new Scanner(file);
        int rowCounter = 0, colCounter = 0;
        while (sc.hasNextLine()) {
            colCounter = 0;
            List<Cell> tmpList = new ArrayList<>();
            String[] lineParsed = sc.nextLine().split(",");
            for (String tmpString : lineParsed) {
                Cell tmpCell = new Cell(
                        colCounter * blockSize,
                        rowCounter * blockSize,
                        Integer.parseInt(tmpString.trim()),
                        blockSize);
                tmpList.add(tmpCell);
                if(tmpCell.hasBigDot() || tmpCell.hasSmallDot()){
                    remainingDot++;
                }
                if(!tmpCell.isWall()){
                    nonWallCells.add(tmpCell);
                }
                colCounter++;
            }
            rowCounter++;
            map.add(tmpList);
        }
        return new Point2D(rowCounter, colCounter);
    }

    public List<List<Cell>> getMap() {
        return map;
    }

    public void drawMap() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map.get(i).get(j).draw(root);
            }
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void addShadow(Shadow shadow) {
        shadows.add(shadow);
    }

    public void addClyde(Clyde clyde) {
        clydes.add(clyde);
    }

    public void addPinky(Pinky pinky) {
        pinkies.add(pinky);
    }

    public void addInky(Inky inky) {
        inkies.add(inky);
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public boolean checkCollision() {
        for (Shadow shadow : shadows) {
            if (pacman.checkCollision(shadow.getImageView()))
                return true;
        }
        for (Clyde clyde : clydes) {
            if (pacman.checkCollision(clyde.getImageView()))
                return true;
        }
        for (Pinky pinky : pinkies) {
            if (pacman.checkCollision(pinky.getImageView()))
                return true;
        }
        for (Inky inky : inkies) {
            if (pacman.checkCollision(inky.getImageView()))
                return true;
        }
        return false;
    }

    public void moveObjects() {
        pacman.move(map, root);
        for (Shadow shadow : shadows) {
            shadow.move(map, pacman.getX(), pacman.getY(), null);
        }
        for (Clyde clyde : clydes) {
            clyde.move(map, 0, 0, null);
        }
        for (Pinky pinky : pinkies) {
            pinky.move(map, pacman.getX(), pacman.getY(), pacman.currentDirection());
        }
        for (Inky inky : inkies) {
            inky.move(map, 0, 0, null);
        }
    }

    public void clear(){
        map.clear();
        shadows.clear();
        clydes.clear();
        pinkies.clear();
        inkies.clear();
        pacman.removeHearts(root);
        pacman = null;
    }

    public void reset(){
        if(pacman!=null){
            pacman.removeFromScene(root);
            pacman.removeHearts(root);
        }
        for(Shadow shadow : shadows){
            shadow.removeFromScene(root);
        }
        for(Inky inky : inkies){
            inky.removeFromScene(root);
        }
        for(Pinky pinky : pinkies){
            pinky.removeFromScene(root);
        }
        for(Clyde clyde : clydes){
            clyde.removeFromScene(root);
        }
        shadows.clear();
        clydes.clear();
        pinkies.clear();
        inkies.clear();
        pacman = null;
    }

    public List<Cell> getNonWallCells(){
        return nonWallCells;
    }
}
