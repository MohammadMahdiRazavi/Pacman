package org.example.pacman;

import org.example.pacman.database.Connect;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class User {
    private String username, password, scoreSeries;
    static public LinkedHashMap<String, User> signedUpUsers;
    private int maxMap1, maxMap2, maxMap3;

    public User() {
    }

    public User(String username, String password, String scoreSeries) {
        this.username = username;
        this.password = password;
        this.scoreSeries = scoreSeries;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getScoreSeries() {
        return scoreSeries;
    }

    public static void saveToDatabase() throws SQLException {
        Connect.clearUsersTable();
        for(User user : signedUpUsers.values()){
            Connect.insertUser(user);
        }
    }

    public void addScore(int map, int score){
        scoreSeries += map + "_" + score + ",";
    }

    public String lastScore(){
        String[] stringArray = scoreSeries.split(",");
        return stringArray[stringArray.length-1].substring(2);
    }

    public void setMaxScores(){
        String[] stringArray = scoreSeries.split(",");
        for (String str : stringArray) {
            switch (str.charAt(0)){
                case '1'-> maxMap1 = Math.max(maxMap1, Integer.parseInt(str.substring(2)));
                case '2'-> maxMap2 = Math.max(maxMap2, Integer.parseInt(str.substring(2)));
                case '3'-> maxMap3 = Math.max(maxMap3, Integer.parseInt(str.substring(2)));
            }
        }
    }

    public int getMaxMap1() {
        return maxMap1;
    }

    public int getMaxMap2() {
        return maxMap2;
    }

    public int getMaxMap3() {
        return maxMap3;
    }
}
