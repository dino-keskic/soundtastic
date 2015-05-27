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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.playmidi.PlayMIDIActivity;
import soundtastic.soundtasitc.recording.Recorder;
import soundtastic.soundtasitc.ProjectInfos;


public class MixingInterface extends Activity implements View.OnClickListener {

    public int track_nr;

    ImageButton buttonPlayAll;
    ImageButton buttonTrackPlay1;
    ImageButton buttonTrackPlay2;
    ImageButton buttonAddRec1;
    ImageButton buttonAddRec2;
    ImageButton buttonAddSounds;
    RelativeLayout layoutTrack1;
    RelativeLayout layoutTrack2;
    Button buttonDeleteTrack;

    TextView buttonTrackTitle1;
    TextView buttonTrackTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing_interface);

        ProjectInfos.getInstance().setSelectedTrackNr(1);

        layoutTrack1 = (RelativeLayout) findViewById(R.id.mi_track1);
        layoutTrack2 = (RelativeLayout) findViewById(R.id.mi_track2);

        buttonPlayAll = (ImageButton) findViewById(R.id.mi_play_all);
        buttonTrackPlay1 = (ImageButton) findViewById(R.id.mi_track_play1);
        buttonTrackPlay2 = (ImageButton) findViewById(R.id.mi_track_play2);
        buttonAddRec1 = (ImageButton) findViewById(R.id.mi_track_rec1);
        buttonAddRec2 = (ImageButton) findViewById(R.id.mi_track_rec2);
        buttonAddSounds = (ImageButton) findViewById(R.id.mi_add_sounds);

        buttonTrackTitle1 = (TextView) findViewById(R.id.mi_track_title1);
        buttonTrackTitle2 = (TextView) findViewById(R.id.mi_track_title2);
        buttonDeleteTrack = (Button) findViewById(R.id.mi_track_delete);

        buttonPlayAll.setOnClickListener(this);
        buttonTrackPlay1.setOnClickListener(this);
        buttonTrackPlay2.setOnClickListener(this);
        buttonAddRec1.setOnClickListener(this);
        buttonAddRec2.setOnClickListener(this);
        buttonTrackTitle1.setOnClickListener(this);
        buttonTrackTitle2.setOnClickListener(this);
        buttonDeleteTrack.setOnClickListener(this);
        buttonAddSounds.setOnClickListener(this);

        layoutTrack1.setOnClickListener(this);
        layoutTrack2.setOnClickListener(this);

        if(ProjectInfos.getInstance().getSelectedTrackNr() == 1)
        {
            layoutTrack1.setBackgroundColor(getResources().getColor(R.color.track_selected));
        }
        refreshTracks();
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
                startActivity(record);
                break;
            case R.id.mi_track_rec2:
                startActivity(record);
                break;
            case R.id.mi_track_delete:
                deleteTrack();
                break;
            case R.id.mi_track_title1:
                ProjectInfos.getInstance().setSelectedTrackNr(1);
                layoutTrack1.setBackgroundColor(getResources().getColor(R.color.track_selected));
                layoutTrack2.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.mi_track_title2:
                ProjectInfos.getInstance().setSelectedTrackNr(2);
                layoutTrack1.setBackgroundColor(getResources().getColor(R.color.white));
                layoutTrack2.setBackgroundColor(getResources().getColor(R.color.track_selected));
                break;
            case R.id.mi_add_sounds:
                addSounds();
                break;

            case R.id.mi_track1:
                ProjectInfos.getInstance().setSelectedTrackNr(1);
                layoutTrack1.setBackgroundColor(getResources().getColor(R.color.track_selected));
                layoutTrack2.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.mi_track2:
                ProjectInfos.getInstance().setSelectedTrackNr(2);
                layoutTrack1.setBackgroundColor(getResources().getColor(R.color.white));
                layoutTrack2.setBackgroundColor(getResources().getColor(R.color.track_selected));
                break;

        }
    }

    // no return-button
    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    public void setTrackTitle(String track_title) {
        TrackInfo ti = new TrackInfo();
        ti.setTrackName(track_title);
        ProjectInfos.getInstance().addTrack(ti);
        refreshTracks();
        //buttonTrackTitle1.setText(track_title);
    }

    public void changeColor()
    {

    }

    public void deleteTrack()
    {
        switch (ProjectInfos.getInstance().getSelectedTrackNr()){
            case 1:
                layoutTrack1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                layoutTrack2.setVisibility(View.INVISIBLE);
                break;
        }
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

    private void refreshTracks() {
        TrackInfo ti = ProjectInfos.getInstance().getTrack(1);
        loadTrackInfo(layoutTrack1, buttonTrackTitle1, ti);
        ti = ProjectInfos.getInstance().getTrack(2);
        loadTrackInfo(layoutTrack2, buttonTrackTitle2, ti);
    }

    private void loadTrackInfo(RelativeLayout layout, TextView title, TrackInfo ti) {
        if(ti == null) {
            layout.setVisibility(View.INVISIBLE);
            return;
        }
        title.setText(ti.getTrackName());
        layout.setVisibility(View.VISIBLE);
    }
}
