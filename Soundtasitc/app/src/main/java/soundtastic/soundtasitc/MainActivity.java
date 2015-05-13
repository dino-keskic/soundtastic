package soundtastic.soundtasitc;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;
import soundtastic.soundtasitc.recording.ExtAudioRecorder;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ImageButton buttonPlay;
    ImageButton buttonPause;
    ImageButton buttonStop;
    ImageButton buttonForward;
    ImageButton buttonRewind;

    ImageButton buttonRec;

    ImageButton buttonMedia;

    Animation pulse = null;

    public MediaPlayer mediaPlayer = null;
    public Recorder recorder = null;
    public Uri hmm = null;

    public int bpm = 0;
    public boolean isRecording = false;

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

        buttonPlay.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonForward.setOnClickListener(this);
        buttonRewind.setOnClickListener(this);

        buttonRec.setOnClickListener(this);

        buttonMedia.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bpm == 0) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.settings_dialog);
            dialog.setTitle("Settings Dialog Box");
            Button okay = (Button) dialog.findViewById(R.id.Button01);
            final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.BPMseekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView beats = (TextView) dialog.findViewById(R.id.beats);
                    beats.setText("" + (progress + 60));
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView beats = (TextView) dialog.findViewById(R.id.beats);
                    bpm = seekBar.getProgress() + 60;
                    dialog.cancel();
                }
            });
            dialog.show();
        }
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
                PlayMIDI.play(mediaPlayer);
                break;
            case R.id.buttonStop:
                PlayMIDI.stop(mediaPlayer);
                break;
            case R.id.buttonPause:
                PlayMIDI.pause(mediaPlayer);
                break;
            case R.id.buttonForward:
                PlayMIDI.forward(mediaPlayer);
                break;
            case R.id.buttonRewind:
                PlayMIDI.rewind(mediaPlayer);
                break;
            case R.id.buttonRec:
                if(!isRecording) {
                    isRecording = true;
                    boolean deleted = recorder.deleteLastRecording();
                    hmm = Uri.parse(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
                    recorder = new Recorder(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
                    recorder.startRecording();
                    ImageView image = (ImageView)findViewById(R.id.buttonRec);
                    pulse = new AlphaAnimation(1, 0);
                    pulse.setDuration(60000/bpm);
                    pulse.setInterpolator(new LinearInterpolator());
                    pulse.setRepeatCount(Animation.INFINITE);
                    pulse.setRepeatMode(Animation.RESTART);
                    image.startAnimation(pulse);
                }
                else {
                    isRecording = false;
                    recorder.stopRecording();
                    ImageView image2 = (ImageView)findViewById(R.id.buttonRec);
                    image2.clearAnimation();
                    mediaPlayer = MediaPlayer.create(this, hmm);
                }
                break;

            case R.id.media:
                //newActivity(v);
                TextView beats_per = (TextView) findViewById(R.id.bpm);
                beats_per.setText("" + bpm);
                break;
        }
    }

    public void newActivity(View view) {
        Intent intent = new Intent(this, PlayMIDIActivity.class);
        startActivity(intent);
    }
}
