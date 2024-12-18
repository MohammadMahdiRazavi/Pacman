package org.example.pacman;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import org.example.pacman.menu.Menu;
import org.example.pacman.controller.GameOverController;
import org.example.pacman.controller.TableViewController;
import org.example.pacman.controller.WinController;
import org.example.pacman.database.Connect;


public class Application extends javafx.application.Application {
    static public Parent root;
    static public Scene scene;
    static public Stage stage;
    private static double xOffset;
    private static double yOffset;
    public static boolean SFX = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        User.signedUpUsers = Connect.getUsers();
        stage = primaryStage;
        stage.setResizable(false);

        Menu.currentUser = new User();
        loadLoginMenu();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            try {
                User.saveToDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            primaryStage.close();
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void loadSignupMenu() throws IOException {
        Application.root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("signup.fxml")));
        Application.scene = new Scene(Application.root, 700, 500);
        Application.stage.setTitle("Signup");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public static void loadLoginMenu() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("login.fxml")));
        scene = new Scene(root, 700, 500);
        Application.stage.setTitle("Login");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public static void loadMainMenu() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("main.fxml")));
        scene = new Scene(root, 1100, 600);
        Application.stage.setTitle("Main Menu");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public static void loadWinMenu() throws IOException {
        SoundEffect.playWinSound();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Application.class.getResource("win.fxml")));
        root = loader.load();
        WinController controller = loader.getController();
        controller.changeLabelText(Menu.currentUser.lastScore());

        scene = new Scene(root, 400, 400);
        Application.stage.setTitle("You win!");
        Application.stage.setScene(scene);
        Application.stage.show();

        new Thread(() -> {
            try {
                Thread.sleep(4000);
                Platform.runLater(() -> {
                    try {
                        stage.close();
                        Application.loadMainMenu();
                    }
                    catch (IOException ignored) {}
                });
            }
            catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void loadGameOverMenu() throws IOException {
        SoundEffect.playGameOverSound();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Application.class.getResource("lose.fxml")));
        root = loader.load();
        GameOverController controller = loader.getController();
        controller.changeLabelText(Menu.currentUser.lastScore());

        scene = new Scene(root, 400, 400);
        Application.stage.setTitle("You lose!");
        Application.stage.setScene(scene);
        Application.stage.show();

        new Thread(() -> {
            try {
                Thread.sleep(4000);
                Platform.runLater(() -> {
                    try {
                        stage.close();
                        Application.loadMainMenu();
                    }
                    catch (IOException ignored) {}
                });
            }
            catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void loadScoreBoard() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("scoreBoard.fxml")));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            event.consume();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            event.consume();
        });
        Scene scene = new Scene(root);
        scene.getRoot().setEffect(new DropShadow(10, Color.rgb(100, 100, 100)));
        scene.setFill(Color.TRANSPARENT);

        new TableViewController();

        stage.setScene(scene);
        stage.show();
    }

    public static void closeWindow(){
        stage.close();
    }

}