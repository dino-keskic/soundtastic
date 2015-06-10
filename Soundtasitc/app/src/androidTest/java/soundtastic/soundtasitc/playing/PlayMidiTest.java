package soundtastic.soundtasitc.playing;

import android.media.MediaPlayer;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import soundtastic.soundtasitc.R;
import soundtastic.soundtasitc.playmidi.PlayMIDI;

/**
 * Created by Dominik on 10.06.2015.
 */
public class PlayMidiTest extends AndroidTestCase {

    MediaPlayer mediaPlayer;

    public void setUp() throws Exception {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.song);
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPlayMidi() {
        PlayMIDI.play(mediaPlayer);
        Assert.assertTrue(mediaPlayer.isPlaying());
    }

    public void testStopMidi() {
        PlayMIDI.play(mediaPlayer);
        PlayMIDI.stop(mediaPlayer);
        Assert.assertTrue(mediaPlayer.getCurrentPosition() == 0);
        Assert.assertFalse(mediaPlayer.isPlaying());
    }

    public void testPauseMidi() {
        PlayMIDI.play(mediaPlayer);
        PlayMIDI.forward(mediaPlayer);
        PlayMIDI.pause(mediaPlayer);
        Assert.assertTrue(mediaPlayer.getCurrentPosition() == 5000);
        Assert.assertFalse(mediaPlayer.isPlaying());
    }

    public void testForwardMidi() {
        PlayMIDI.play(mediaPlayer);
        Assert.assertTrue(mediaPlayer.isPlaying());

        int milliseconds_before = mediaPlayer.getCurrentPosition();
        PlayMIDI.forward(mediaPlayer);
        int milliseconds_after = mediaPlayer.getCurrentPosition();
        Assert.assertTrue(milliseconds_before == milliseconds_after - 5000);
    }

    public void testRewindMidi() {
        PlayMIDI.play(mediaPlayer);
        Assert.assertTrue(mediaPlayer.isPlaying());
        PlayMIDI.forward(mediaPlayer);

        int milliseconds_before = mediaPlayer.getCurrentPosition();
        PlayMIDI.rewind(mediaPlayer);
        int milliseconds_after = mediaPlayer.getCurrentPosition();
        Assert.assertTrue(milliseconds_before == milliseconds_after + 5000);
    }
}
