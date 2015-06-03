package soundtastic.soundtasitc;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;

public class RecordInterface extends Activity implements View.OnClickListener,MediaPlayer.OnCompletionListener {

    ImageButton buttonPlay;
    ImageButton buttonStop;
    ImageButton buttonRec;
    ImageButton buttonSave;
    ImageButton buttonDiscard;

    Animation pulse = null;

    public MediaPlayer mediaPlayer = null;
    public Recorder recorder = null;
    public Uri buffer_file = null;

    public boolean isRecording = false;

    public void onCompletion(MediaPlayer mp)
    {
        buttonPlay.setVisibility(View.VISIBLE);
        buttonStop.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        buffer_file = Uri.parse(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
        recorder = new Recorder(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonStop = (ImageButton) findViewById(R.id.buttonStop);
        buttonRec = (ImageButton) findViewById(R.id.buttonRecord);

        buttonSave = (ImageButton) findViewById(R.id.buttonSave);
        buttonDiscard = (ImageButton) findViewById(R.id.buttonDiscard);

        buttonPlay.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonRec.setOnClickListener(this);

        buttonSave.setOnClickListener(this);
        buttonDiscard.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void checkIfSongHasFinished()
    {

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.buttonPlay:
                buttonPlay.setVisibility(View.GONE);
                buttonStop.setVisibility(View.VISIBLE);
                PlayMIDI.play(mediaPlayer);

                mediaPlayer.setOnCompletionListener(this);

                break;
            case R.id.buttonStop:
                buttonPlay.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.GONE);
                PlayMIDI.stop(mediaPlayer);
                break;
            case R.id.buttonRecord:
                if(!isRecording) {
                    isRecording = true;
                    boolean deleted = recorder.deleteLastRecording();
                    buffer_file = Uri.parse(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
                    recorder = new Recorder(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
                    recorder.startRecording();
                    ImageButton image = (ImageButton)findViewById(R.id.buttonRecord);
                    pulse = new AlphaAnimation(1, 0);
                    pulse.setDuration(60000/ProjectInfos.getInstance().getBpm());
                    pulse.setInterpolator(new LinearInterpolator());
                    pulse.setRepeatCount(Animation.INFINITE);
                    pulse.setRepeatMode(Animation.RESTART);
                    image.startAnimation(pulse);
                }
                else {
                    isRecording = false;
                    recorder.stopRecording();
                    ImageButton image2 = (ImageButton)findViewById(R.id.buttonRecord);
                    image2.clearAnimation();
                    mediaPlayer = MediaPlayer.create(this, buffer_file);
                }
                break;
            case R.id.buttonSave:
                this.finish();
                break;
            case R.id.buttonDiscard:
                this.finish();
                break;
        }
    }
    public void newActivity(View view) {
        Intent intent = new Intent(this, PlayMIDIActivity.class);
        startActivity(intent);
    }
}