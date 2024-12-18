package org.example.pacman.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.pacman.Application;
import org.example.pacman.User;

import java.io.IOException;

public class SignUp {
    @FXML
    private TextField usernameSignUp;
    @FXML
    private PasswordField passwordSignUp;
    @FXML
    private Label warningLabel;

    static public final String USERNAME_REGEX = "[a-zA-Z0-9_]+";
    static public final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z]).+$";

    public void signUp(ActionEvent actionEvent){
        String username = usernameSignUp.getText();
        String password = passwordSignUp.getText();
        if(User.signedUpUsers.containsKey(username)){
            warningLabel.setText("Username already exists!");
            usernameSignUp.clear();
        }
        else if(!username.matches(USERNAME_REGEX)){
            warningLabel.setText("Username should consist of letters, numbers, and underscores.");
            usernameSignUp.clear();
        }
        else if (password.length() < 8  || password.length() > 15) {
            warningLabel.setText("Password should be between 8 & 15 characters!");
            passwordSignUp.clear();
        }
        else if (!password.matches(PASSWORD_REGEX)) {
            warningLabel.setText("Weak password!");
            passwordSignUp.clear();
        }
        else if (password.replaceAll("[a-zA-Z0-9]", "").isEmpty()) {
            warningLabel.setText("No special characters!");
            passwordSignUp.clear();
        }
        else{
            User.signedUpUsers.put(username, new User(username, password, ""));
            warningLabel.setTextFill(Color.GREENYELLOW);
            warningLabel.setText("Signed up successfully!");
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> {
                        try {
                            Application.loadLoginMenu();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void back(MouseEvent event) throws IOException {
        Application.loadLoginMenu();
    }

}
