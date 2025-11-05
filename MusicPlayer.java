import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {
    private Clip clip;

    public void playMusic() {
        try {
            File file = new File("src/music/lobby1.wav"); // path to music
            if (!file.exists()) {
                System.out.println("Music file not found!");
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            System.out.println("Music playing...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("Music stopped!");
        }
    }
}
