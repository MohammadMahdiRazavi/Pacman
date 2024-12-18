package org.example.pacman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundEffect {

    private static String winSoundPath = "src/main/resources/SFX/win.mp3";
    private static String gameOverSoundPath = "src/main/resources/SFX/game_over.mp3";
    private static String munchSoundPath = "src/main/resources/SFX/munch.mp3";
    private static String yummySoundPath = "src/main/resources/SFX/yummy.mp3";
    private static Media sound;
    private static MediaPlayer mediaPlayer;

    public static void playMunchSound() {
        if(mediaPlayer != null)
            mediaPlayer.dispose();
        sound = new Media(new File(munchSoundPath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public static void playWinSound() {
        if(mediaPlayer != null)
            mediaPlayer.dispose();
        sound = new Media(new File(winSoundPath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public static void playGameOverSound() {
        if(mediaPlayer != null)
            mediaPlayer.dispose();
        sound = new Media(new File(gameOverSoundPath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public static void playYummySound() {
        if(mediaPlayer != null)
            mediaPlayer.dispose();
        sound = new Media(new File(yummySoundPath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
