package soundtastic.soundtasitc;


import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;
import soundtastic.soundtasitc.ProjectInfos;



public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //SeekBar buttonBPM;
    Button buttonAddRec1;
    Button buttonAddPiano1;
    //TextView buttonTrackTitle1;

    public static final int MIN_BPM = 60;

    public MediaPlayer mediaPlayer = null;
    public Recorder recorder = null;
    public Uri hmm = null;
    ProjectInfos infos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        infos = new ProjectInfos();
/*
 BACKUP!!!!!
        Intent mixing = new Intent(this, Record.class);
        //mixing.putExtra("key",value);
        this.startActivity(mixing);
        */

    }

    @Override
    protected void onStart() {
        super.onStart();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.settings_dialog);
        dialog.setTitle("Create New Project");
        dialog.setCanceledOnTouchOutside(false);
        Button okay = (Button) dialog.findViewById(R.id.Button01);

        final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.BPMseekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView beats = (TextView) dialog.findViewById(R.id.beats);
                beats.setText("" + (progress + MIN_BPM));
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
                infos.setBpm(seekBar.getProgress() + MIN_BPM);
                RadioGroup rg = (RadioGroup)dialog.findViewById(R.id.radioGroup);
                View radioButton = rg.findViewById(rg.getCheckedRadioButtonId());
                String project_name = ((EditText)dialog.findViewById(R.id.editText)).getText().toString();
                if(project_name.isEmpty())
                {
                    TextView errorMessage1 = (TextView) dialog.findViewById(R.id.errorMessage);
                    errorMessage1.setText("Please enter project name!\n");
                }
                else if(radioButton == null)
                {
                    TextView errorMessage1 = (TextView) dialog.findViewById(R.id.errorMessage);
                    errorMessage1.setText("Please choose time!\n");
                }
                else {
                    infos.setProjectName(project_name);
                    infos.setTimeSignature(TimeSignatures.values()[rg.indexOfChild(radioButton)]);
                    dialog.cancel();
                    newActivity(v);
                }


            }
            });
        dialog.show();
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

    }

    public void newActivity(View view) {
        Intent mixing = new Intent(this, MixingInterface.class);
        mixing.putExtra("infos", infos);
        this.startActivity(mixing);
    }

}
