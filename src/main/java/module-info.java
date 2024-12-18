module org.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens org.example.pacman to javafx.fxml;
    exports org.example.pacman;
    exports org.example.pacman.map;
    exports org.example.pacman.app;
    exports org.example.pacman.ghost;
    opens org.example.pacman.app to javafx.fxml;
    exports org.example.pacman.menu;
    opens org.example.pacman.menu to javafx.fxml;
    exports org.example.pacman.controller;
    opens org.example.pacman.controller to javafx.fxml;
}