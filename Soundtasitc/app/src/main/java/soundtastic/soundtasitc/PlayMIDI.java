package soundtastic.soundtasitc;

import android.media.MediaPlayer;

/**
 * Created by Dominik on 22.04.2015.
 */
public class PlayMIDI {
    public static void play(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
    public static void stop(MediaPlayer mediaPlayer) {
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
    }
    public static void pause(MediaPlayer mediaPlayer) {
        mediaPlayer.pause();
    }
    public static void forward(MediaPlayer mediaPlayer) {
        double startTime = mediaPlayer.getCurrentPosition();
        double finalTime = mediaPlayer.getDuration();
        double forwardTime = 5000;
        double time = startTime + forwardTime;

        if (time >= finalTime)
                mediaPlayer.seekTo((int)finalTime);
        else
            mediaPlayer.seekTo((int) time);

    }
    public static void rewind(MediaPlayer mediaPlayer) {
        double startTime = mediaPlayer.getCurrentPosition();
        double backwardTime = 5000;
        double time = startTime - backwardTime;

        if (time > 0)
            mediaPlayer.seekTo((int)time);
        else
            mediaPlayer.seekTo(0);
    }
}
