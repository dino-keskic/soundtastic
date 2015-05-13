package soundtastic.soundtasitc;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;


public class Record extends ActionBarActivity implements View.OnClickListener {

    ImageButton buttonPlay;
    ImageButton buttonPause;
    ImageButton buttonStop;
    ImageButton buttonForward;
    ImageButton buttonRewind;

    ImageButton buttonRec;
    ImageButton buttonPauseRec;
    ImageButton buttonStopRec;
    ImageButton buttonDiscardRec;

    ImageButton buttonMedia;

    public MediaPlayer mediaPlayer = null;
    public Recorder recorder = null;
    public Uri hmm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hmm = Uri.parse(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
        recorder = new Recorder(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPause = (ImageButton) findViewById(R.id.buttonPause);
        buttonStop = (ImageButton) findViewById(R.id.buttonStop);
        buttonForward = (ImageButton) findViewById(R.id.buttonForward);
        buttonRewind = (ImageButton) findViewById(R.id.buttonRewind);

        buttonRec = (ImageButton) findViewById(R.id.buttonRec);

        buttonMedia = (ImageButton) findViewById(R.id.media);

        buttonPauseRec = (ImageButton) findViewById(R.id.buttonPauseRec);
        buttonPauseRec.setVisibility(View.INVISIBLE);
        buttonStopRec = (ImageButton) findViewById(R.id.buttonStopRec);
        buttonStopRec.setVisibility(View.INVISIBLE);
        buttonDiscardRec = (ImageButton) findViewById(R.id.buttonDiscardRec);
        buttonDiscardRec.setVisibility(View.INVISIBLE);

        buttonPlay.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonForward.setOnClickListener(this);
        buttonRewind.setOnClickListener(this);

        buttonRec.setOnClickListener(this);
        buttonPauseRec.setOnClickListener(this);
        buttonDiscardRec.setOnClickListener(this);
        buttonStopRec.setOnClickListener(this);

        buttonMedia.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View v) {

        ImageButton clickedButton = (ImageButton) v;

        switch(clickedButton.getId()) {
            case R.id.buttonPlay:
                mediaPlayer = MediaPlayer.create(this, hmm);
                PlayMIDI.play(mediaPlayer);
                //mediaPlayer.start();
                break;
            case R.id.buttonStop:
                PlayMIDI.stop(mediaPlayer);
                //mediaPlayer.start();
                break;
            case R.id.buttonPause:
                PlayMIDI.pause(mediaPlayer);
                //mediaPlayer.start();
                break;
            case R.id.buttonForward:
                PlayMIDI.forward(mediaPlayer);
                //mediaPlayer.start();
                break;
            case R.id.buttonRewind:
                PlayMIDI.rewind(mediaPlayer);
                //mediaPlayer.start();
                break;
            case R.id.buttonRec:
                buttonRec.setVisibility(View.INVISIBLE);
                buttonPauseRec.setVisibility(View.VISIBLE);
                buttonStopRec.setVisibility(View.VISIBLE);
                buttonDiscardRec.setVisibility(View.VISIBLE);

                boolean deleted = recorder.deleteLastRecording();
                hmm = Uri.parse(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
                recorder = new Recorder(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
                recorder.startRecording();
                break;
            case R.id.buttonDiscardRec:
                buttonRec.setVisibility(View.VISIBLE);
                buttonPauseRec.setVisibility(View.INVISIBLE);
                buttonStopRec.setVisibility(View.INVISIBLE);
                buttonDiscardRec.setVisibility(View.INVISIBLE);
                break;
            case R.id.buttonStopRec:
                buttonRec.setVisibility(View.VISIBLE);
                buttonPauseRec.setVisibility(View.INVISIBLE);
                buttonStopRec.setVisibility(View.INVISIBLE);
                buttonDiscardRec.setVisibility(View.INVISIBLE);

                recorder.stopRecording();
                break;
            case R.id.media:
                newActivity(v);
                break;
        }
    }

    public void newActivity(View view) {
        Intent intent = new Intent(this, PlayMIDIActivity.class);
        startActivity(intent);
    }
}
