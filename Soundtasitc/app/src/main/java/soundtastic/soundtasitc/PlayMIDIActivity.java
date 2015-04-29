package soundtastic.soundtasitc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.media.MediaPlayer;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;


public class PlayMIDIActivity extends ActionBarActivity implements MediaController.MediaPlayerControl {

    private MediaPlayer mediaPlayer = null;
    private MusicController controller = null;
    private boolean musicBound = false;

    ListView list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = getIntent();
        setContentView(R.layout.activity_play_midi);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        musicBound = true;
        controller = new MusicController(this);
        controller.setMediaPlayer(this);
        Cursor songs = findMusicInFolder();
        //controller.setAnchorView();

        controller.setEnabled(true);

        list = (ListView) findViewById(R.id.song_list);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_midi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {
        PlayMIDI.play(mediaPlayer);
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean isPlaying() {
        if(mediaPlayer!=null&& musicBound)
            return mediaPlayer.isPlaying();
        return false;
    }

    @Override
    public int getDuration() {
        if(mediaPlayer !=null && musicBound && mediaPlayer.isPlaying())
            return mediaPlayer.getDuration();
        else return 0;
    }


    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }


    @Override
    public int getCurrentPosition() {
        if(mediaPlayer != null && musicBound && mediaPlayer.isPlaying())
        {
            return mediaPlayer.getCurrentPosition();
        }
        else
            return 0;
    }



    public Cursor findMusicInFolder() {
        Cursor cursor;
        //String selection;
        //String[] projection = {MediaStore.Audio.Media.IS_MUSIC};
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //Create query for searching media files in folder
        // selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
        // MediaStore.Audio.Media.DATA + " LIKE 'android.resource://soundtastic.soundtasitc/raw/'";
        cursor = getContentResolver().query(uri, null, null, null, null);
        return cursor;
    }
}
