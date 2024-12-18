package org.example.pacman.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.example.pacman.Application;
import org.example.pacman.User;

import java.io.IOException;

import static org.example.pacman.Application.loadSignupMenu;

public class Login {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label warningLogin;

    public void signUp(ActionEvent actionEvent) throws IOException {
        loadSignupMenu();
    }

    public void login(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if(User.signedUpUsers.containsKey(username)){
            if(User.signedUpUsers.get(username).getPassword().equals(password)){
                Menu.currentUser = User.signedUpUsers.get(username);
                warningLogin.setTextFill(Color.GREENYELLOW);
                warningLogin.setText("Logged in successfully!");
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        Platform.runLater(() -> {
                            try {
                                Application.loadMainMenu();
                            }
                            catch (IOException ignored) {}
                        });
                    }
                    catch (InterruptedException ignored) {
                    }
                }).start();
            }
            else{
                warningLogin.setText("Incorrect Password!");
                passwordInput.clear();
            }
        }
        else{
            warningLogin.setText("Incorrect Username!");
            usernameInput.clear();
            passwordInput.clear();
        }
    }
}
