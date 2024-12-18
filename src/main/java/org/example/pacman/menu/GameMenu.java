package org.example.pacman.menu;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.pacman.Application;
import org.example.pacman.User;
import org.example.pacman.app.Game;
import org.example.pacman.app.Pacman;
import org.example.pacman.ghost.*;
import org.example.pacman.map.Cell;
import org.example.pacman.map.MapGenerator;
import java.io.IOException;
import java.util.Random;

public class GameMenu implements EventHandler<KeyEvent> {
    private int BLOCK_SIZE, FRAME_RATE = 40;
    private int X_BLOCKS, Y_BLOCKS;
    private final Image favicon = new Image("file:src\\main\\resources\\favicon.png");
    double secPerFrame = ((double) 1 / FRAME_RATE);
    private static Group root;
    private static Game game;
    private static Scene scene;
    private static Stage stage;
    private AnimationTimer timer;
    private int map;
    private Cell healthBoosterCell, pointBoosterCell;
    private final int initialLives = 3;

    public void loadGame1() throws Exception{
        BLOCK_SIZE = 40;
        Ghost.moveUnit = 4;
        map = 1;
        MapGenerator.generate("src\\main\\resources\\drafts\\map1.txt");
        setUpGame();
        resetGame1(initialLives, 0);
        stage.show();
        setTimer();
        timer.start();
    }

    public void loadGame2() throws Exception {
        BLOCK_SIZE = 25;
        map = 2;
        MapGenerator.generate("src\\main\\resources\\drafts\\map2.txt");
        setUpGame();
        resetGame2(initialLives, 0);
        stage.show();
        setTimer();
        timer.start();
    }

    public void loadGame3() throws Exception {
        BLOCK_SIZE = 24;
        map = 3;
        MapGenerator.generate("src\\main\\resources\\drafts\\map3.txt");
        setUpGame();
        resetGame3(initialLives, 0);
        stage.show();
        setTimer();
        timer.start();
    }

    public void scoreBoard(MouseEvent mouseEvent) throws IOException {
        Application.loadScoreBoard();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Menu.currentUser = new User();
        Application.loadLoginMenu();
    }

    private void endGame() throws IOException {
        root.getChildren().clear();
        root = null;
        stage.close();
        game.clear();
        timer.stop();
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN)) {
            game.getPacman().changeDirection(Game.Direction.D);
        } else if (event.getCode().equals(KeyCode.UP)) {
            game.getPacman().changeDirection(Game.Direction.U);
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            game.getPacman().changeDirection(Game.Direction.R);
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            game.getPacman().changeDirection(Game.Direction.L);
        }
    }

    private void setTimer(){
        timer = new AnimationTimer() {
            private long healthBoosterUpdate;
            private long pointBoosterUpdate;
            private boolean healthBoosterOn = false;
            private boolean pointBoosterOn = false;
            private long prevTime = 0;
            @Override
            public void handle(long now) {
                long dt = now - prevTime;
                if (healthBoosterUpdate == 0) {
                    healthBoosterUpdate = now;
                }
                if (pointBoosterUpdate == 0) {
                    pointBoosterUpdate = now;
                }
                if (dt > secPerFrame * 1e9) {
                    //Health booster: every 10s
                    if(now - healthBoosterUpdate > 10 * 1e9){
                        if(!healthBoosterOn){
                            chooseCellForHealthBoost();
                            healthBoosterCell.addHealthBoost(root);
                        }
                        else{
                            healthBoosterCell.removeHealthBoost(root);
                        }
                        healthBoosterOn = !healthBoosterOn;
                        healthBoosterUpdate = now;
                    }
                    //Point booster: every 15s
                    if(now - pointBoosterUpdate > 15 * 1e9){
                        if(!pointBoosterOn){
                            chooseCellForPointBoost();
                            pointBoosterCell.addPointBooster(root);
                        }
                        else{
                            pointBoosterCell.removePointBooster(root);
                        }
                        pointBoosterOn = !pointBoosterOn;
                        pointBoosterUpdate = now;

                    }
                    prevTime = now;
                    game.moveObjects();
                    if(Game.remainingDot == 0){
                        try {
                            Menu.currentUser.addScore(map, game.getPacman().getScore());
                            endGame();
                            Application.loadWinMenu();
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (game.checkCollision()) {
                        game.getPacman().removeALife();
                        if(game.getPacman().getLives() == 0){
                            try {
                                Menu.currentUser.addScore(map, game.getPacman().getScore());
                                endGame();
                                Application.loadGameOverMenu();
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            switch (map){
                                case 1 -> resetGame1(game.getPacman().getLives(), game.getPacman().getScore());
                                case 2 -> resetGame2(game.getPacman().getLives(), game.getPacman().getScore());
                                case 3 -> resetGame3(game.getPacman().getLives(), game.getPacman().getScore());
                            }
                        }

                    }
                }
            }
        };
    }

    private void setUpGame() throws Exception {
        Application.closeWindow();
        root = new Group();
        game = new Game(BLOCK_SIZE, root);
        X_BLOCKS = game.getCol();
        Y_BLOCKS = game.getRow();
        scene = new Scene(root, BLOCK_SIZE * X_BLOCKS, BLOCK_SIZE * Y_BLOCKS + 50, Color.BLACK);
        stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(favicon);
        stage.setTitle("Game");
        stage.setScene(scene);
        scene.setOnKeyPressed(this);
        game.drawMap();
    }

    private void resetGame1(int lives, int storedScore){
        game.reset();
        game.setPacman(new Pacman(8 * BLOCK_SIZE, 6 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS, lives, storedScore));
        game.addShadow(new Shadow(BLOCK_SIZE, 10 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(15 * BLOCK_SIZE, 7 * BLOCK_SIZE, Game.Direction.D, root, BLOCK_SIZE));
    }

    private void resetGame2(int lives, int storedScore){
        game.reset();
        game.setPacman(new Pacman(25 * BLOCK_SIZE, 6 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS, lives, storedScore));
        game.addShadow(new Shadow(11 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(33 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(40 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addPinky(new Pinky(41 * BLOCK_SIZE, 12 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));
    }

    private void resetGame3(int lives, int storedScore){
        game.reset();
        game.setPacman(new Pacman(13 * BLOCK_SIZE, 22 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS, lives, storedScore));
        game.addShadow(new Shadow(9 * BLOCK_SIZE, 9 * BLOCK_SIZE, Game.Direction.D, root, BLOCK_SIZE));
        game.addShadow(new Shadow(18 * BLOCK_SIZE, 9 * BLOCK_SIZE, Game.Direction.L, root, BLOCK_SIZE));
        game.addClyde(new Clyde(13 * BLOCK_SIZE, 10 * BLOCK_SIZE, Game.Direction.R, root, BLOCK_SIZE));
        game.addClyde(new Clyde(14 * BLOCK_SIZE, 10 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(26 * BLOCK_SIZE, 28 * BLOCK_SIZE, Game.Direction.L, root, BLOCK_SIZE));
        game.addPinky(new Pinky(2 * BLOCK_SIZE, 11 * BLOCK_SIZE, Game.Direction.R, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));
        game.addInky(new Inky(BLOCK_SIZE, BLOCK_SIZE, Game.Direction.D, root, BLOCK_SIZE));
        game.addInky(new Inky(26 * BLOCK_SIZE, BLOCK_SIZE, Game.Direction.L, root, BLOCK_SIZE));
    }

    private void chooseCellForHealthBoost(){
        Random random = new Random();
        int randomIndex = random.nextInt(game.getNonWallCells().size());
        healthBoosterCell = game.getNonWallCells().get(randomIndex);
    }

    private void chooseCellForPointBoost(){
        Random random = new Random();
        int randomIndex = random.nextInt(game.getNonWallCells().size());
        pointBoosterCell = game.getNonWallCells().get(randomIndex);
    }
}
