package soundtastic.soundtasitc;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.AbstractMap;
import java.util.List;

import soundtastic.soundtasitc.midi.MidiValues;
import soundtastic.soundtasitc.note.MusicalInstrument;
import soundtastic.soundtasitc.note.MusicalKey;
import soundtastic.soundtasitc.note.NoteEvent;
import soundtastic.soundtasitc.note.NoteName;
import soundtastic.soundtasitc.note.Project;
import soundtastic.soundtasitc.note.Track;
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
    public String trackName="track01";


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
                    recorder = new Recorder(Environment.getExternalStorageDirectory() + "/sampleRecording.wav");
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

                WavConverter converter = new WavConverter();
                MidiValues midiValues =  converter.convertToMidi(Environment.getExternalStorageDirectory()+"/sampleRecording.wav");
                trackName ="track01";

                if(midiValues != null) {
                    List<AbstractMap.SimpleEntry<Integer, Integer>> noteMap = midiValues.generateNoteMap();
                    Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

                    int currentTicks = 0;

                    for (int i = 0; i < noteMap.size(); i++) {
                        NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
                        NoteEvent note_begin = new NoteEvent(noteName, true);
                        firstTrack.addNoteEvent(currentTicks, note_begin);
                        currentTicks += midiValues.getNoteLength(noteMap.get(i).getValue()) * Project.getInstance().getBeatsPerMinute() * 8;

                        Log.d("NOTELENGTH", Double.toString(midiValues.getNoteLength(noteMap.get(i).getValue())));
                        Log.d("CURRENTTICKS", Integer.toString(currentTicks));

                        NoteEvent note_end = new NoteEvent(noteName, false);
                        firstTrack.addNoteEvent(currentTicks, note_end);
                    }



                  final EditText trackNameText = new EditText(this);
                    trackNameText.setText(trackName);

// Set the default text to a link of the Queen

                   new AlertDialog.Builder(this)
                            .setTitle("Create a new Track")
                            .setMessage("")

                            .setView(trackNameText)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    trackName = trackNameText.getText().toString();
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }).show();


                    Project.getInstance().addTrack(trackName,firstTrack);


                    RecordInterface.this.finish();
                }
               else{
                    Toast.makeText(RecordInterface.this, "Conversion failed! Wav file available?", Toast.LENGTH_LONG).show();
                }
                // Convert wav to midi and return to MixingInterface
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