package soundtastic.soundtasitc;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;


public class MixingInterface extends Activity implements View.OnClickListener {

    public int track_nr;

    ImageButton buttonPlayAll;
    ImageButton buttonTrackPlay1;
    ImageButton buttonTrackPlay2;
    ImageButton buttonAddRec1;
    ImageButton buttonAddRec2;
    ImageButton buttonAddSounds;
    Button buttonDeleteTrack;

    TextView buttonTrackTitle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing_interface);

        track_nr = 0;

        buttonPlayAll = (ImageButton) findViewById(R.id.mi_play_all);
        buttonTrackPlay1 = (ImageButton) findViewById(R.id.mi_track_play1);
        buttonTrackPlay2 = (ImageButton) findViewById(R.id.mi_track_play2);
        buttonAddRec1 = (ImageButton) findViewById(R.id.mi_track_rec1);
        buttonAddRec2 = (ImageButton) findViewById(R.id.mi_track_rec2);
        buttonAddSounds = (ImageButton) findViewById(R.id.mi_add_sounds);

        buttonTrackTitle1 = (TextView) findViewById(R.id.mi_track_title1);
        buttonDeleteTrack = (Button) findViewById(R.id.mi_track_delete);

        buttonPlayAll.setOnClickListener(this);
        buttonTrackPlay1.setOnClickListener(this);
        buttonTrackPlay2.setOnClickListener(this);
        buttonAddRec1.setOnClickListener(this);
        buttonAddRec2.setOnClickListener(this);
        buttonTrackTitle1.setOnClickListener(this);
        buttonDeleteTrack.setOnClickListener(this);
        buttonAddSounds.setOnClickListener(this);

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

        /*
        Button clickedButton = (Button) v;
        */

        Intent record = new Intent(this, RecordInterface.class);

        switch(v.getId()) {
            case R.id.mi_track_rec1:
                //record.putExtra("track_nr",track_nr);
                // so abfangen int trackNr = record.getIntExtra("track_nr", 0);
                startActivity(record);
                break;
            case R.id.mi_track_rec2:
                //record.putExtra("track_nr",track_nr);
                // so abfangen int trackNr = record.getIntExtra("track_nr", 0);
                startActivity(record);
                break;
            case R.id.mi_track_delete:
                deleteTrack();
                break;
            case R.id.mi_track_title1:
                track_nr = 1;
                break;
            case R.id.mi_track_title2:
                track_nr = 2;
                break;
            case R.id.mi_add_sounds:
                addSounds();
                break;

        }
    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    public void setTrackTitle(String track_title)
    {
       buttonTrackTitle1.setText(track_title);
    }

    public void deleteTrack()
    {

    }

    public void addSounds()
    {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.add_sounds);
        dialog.setTitle("Add new track");
        dialog.show();

        final Button mic = (Button) dialog.findViewById(R.id.mi_add_sounds_mic);
        final EditText title = (EditText) dialog.findViewById(R.id.mi_add_sounds_title);

        if(mic != null)
        {
            mic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTrackTitle(title.getText().toString());
                    dialog.cancel();
                }
            });
        }
    }
}
