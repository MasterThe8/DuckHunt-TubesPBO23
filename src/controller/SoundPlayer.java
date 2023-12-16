package src.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {
    private String audioPath;

    public void SoundPlayer(){
        playSound(audioPath);
    }

    public static void playSound(String filePath) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                File audioFile = new File(filePath);
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(audioFile));
                clip.start();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }
}

