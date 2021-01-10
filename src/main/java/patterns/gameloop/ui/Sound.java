package patterns.gameloop.ui;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles playing, stoping, and looping of sounds for the game.
 *
 * @author Tyler Thomas
 * @see
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 *
 */
public class Sound {

    private Clip clip;

    public Sound(String fileName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
            } else {
                System.err.println("Datei nicht gefunden: " + fileName);
                clip = null;
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Sound: Unsupported Audio File: " + e);
            clip = null;
        } catch (IOException e) {
            System.err.println("Sound: Input/Output Error: " + e);
            clip = null;
        } catch (LineUnavailableException e) {
            System.err.println("Sound: Line Unavailable Exception Error: " + e);
            clip = null;
        } catch (Exception e) {
            System.err.println(e);
            clip = null;
        }
    }

    
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);  // Must always rewind!
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
