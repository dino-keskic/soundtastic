package soundtastic.soundtasitc;


import android.app.Activity;
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
    Button buttonAddRec1;
    Button buttonAddPiano1;
    Button buttonDeleteTrack;
    TextView buttonTrackTitle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing_interface);

        track_nr = 0;

        buttonAddRec1 = (Button) findViewById(R.id.mi_track_rec);
        buttonAddPiano1 = (Button) findViewById(R.id.mi_track_play);
        buttonTrackTitle1 = (TextView) findViewById(R.id.mi_track_title1);
        buttonDeleteTrack = (Button) findViewById(R.id.mi_track_delete);

        buttonAddRec1.setOnClickListener(this);
        buttonTrackTitle1.setOnClickListener(this);
        buttonDeleteTrack.setOnClickListener(this);

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

        Intent record = new Intent(this, Record.class);

        switch(v.getId()) {
            case R.id.mi_track_rec:
                startActivity(record);
                break;
            case R.id.mi_track_delete:
                deleteTrack();
                break;
            case R.id.mi_track_title1:
                track_nr = 1;
                break;

        }
    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    public void deleteTrack()
    {
        buttonTrackTitle1.setText("empty");
    }
}
