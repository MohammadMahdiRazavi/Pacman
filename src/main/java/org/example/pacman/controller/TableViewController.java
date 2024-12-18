package org.example.pacman.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.pacman.Application;
import org.example.pacman.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {
    @FXML
    public TableView<tableUser> tableView;
    @FXML
    public TableColumn<tableUser, String> nameColumn;
    @FXML
    public TableColumn<tableUser, Integer> map1ScoreColumn;
    @FXML
    public TableColumn<tableUser, Integer> map2ScoreColumn;
    @FXML
    public TableColumn<tableUser, Integer> map3ScoreColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        map1ScoreColumn.setCellValueFactory(cellData -> cellData.getValue().getMap1Score().asObject());
        map2ScoreColumn.setCellValueFactory(cellData -> cellData.getValue().getMap2Score().asObject());
        map3ScoreColumn.setCellValueFactory(cellData -> cellData.getValue().getMap3Score().asObject());

        List<tableUser> users = new ArrayList<>();
        for(User user : User.signedUpUsers.values()){
            user.setMaxScores();
            users.add(new tableUser(user.getUsername(), user.getMaxMap1(),
                    user.getMaxMap2(), user.getMaxMap3()));
        }
        ObservableList<tableUser> ol = FXCollections.observableArrayList(users);
        tableView.setItems(ol);
        tableView.sort();
    }

    public void handleExitButtonClicked(ActionEvent actionEvent) throws IOException {
        Application.loadMainMenu();
    }

    public static class tableUser {
        private final StringProperty name;
        private final IntegerProperty map1Score, map2Score, map3Score;

        public tableUser(String name, int map1Score, int map2Score, int map3Score) {
            this.name = new SimpleStringProperty(name);
            this.map1Score = new SimpleIntegerProperty(map1Score);
            this.map2Score = new SimpleIntegerProperty(map2Score);
            this.map3Score = new SimpleIntegerProperty(map3Score);
        }

        public StringProperty getName() {
            return name;
        }
        public IntegerProperty getMap1Score() {
            return map1Score;
        }
        public IntegerProperty getMap2Score() {
            return map2Score;
        }
        public IntegerProperty getMap3Score() {
            return map3Score;
        }
   }
}
