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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

//    RelativeLayout layoutTrack1;
//    ImageButton buttonTrackPlay1;
//    ImageButton buttonAddRec1;
//    TextView buttonTrackTitle1;

    TrackLayout[] trackLayouts;

//    RelativeLayout layoutTrack2;
//    ImageButton buttonTrackPlay2;
//    ImageButton buttonAddRec2;
//    TextView buttonTrackTitle2;

    ImageButton buttonAddSounds;
    RelativeLayout[] layoutTracks;
    Button buttonDeleteTrack;
    Button buttonCopyTrack;
    CheckBox enableCheckbox;
    SeekBar startAtBar;
    TextView startAtValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing_interface);

        ProjectInfos.getInstance().setSelectedTrackNr(1);

        //layoutTrack1 = (RelativeLayout) findViewById(R.id.mi_track1);
        //layoutTrack2 = (RelativeLayout) findViewById(R.id.mi_track2);

        //layoutTracks = new RelativeLayout[] {layoutTrack1, layoutTrack2};

        buttonPlayAll = (ImageButton) findViewById(R.id.mi_play_all);
        //buttonTrackPlay1 = (ImageButton) findViewById(R.id.mi_track_play1);
//        buttonTrackPlay2 = (ImageButton) findViewById(R.id.mi_track_play2);
        //buttonAddRec1 = (ImageButton) findViewById(R.id.mi_track_rec1);
//        buttonAddRec2 = (ImageButton) findViewById(R.id.mi_track_rec2);
        buttonAddSounds = (ImageButton) findViewById(R.id.mi_add_sounds);

        //buttonTrackTitle1 = (TextView) findViewById(R.id.mi_track_title1);
  //      buttonTrackTitle2 = (TextView) findViewById(R.id.mi_track_title2);
        buttonDeleteTrack = (Button) findViewById(R.id.mi_track_delete);
        buttonCopyTrack = (Button) findViewById(R.id.mi_track_copy);

        trackLayouts = new TrackLayout[4];
        for(int i=0;i<4;i++)
        {
            trackLayouts[i] = new TrackLayout();

            int resID = getResources().getIdentifier("mi_track_rec" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonAddRec = ((ImageButton) findViewById(resID));

            resID = getResources().getIdentifier("mi_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].layoutTrack = ((RelativeLayout) findViewById(resID));

            resID = getResources().getIdentifier("mi_track_title" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonTrackTitle = ((TextView) findViewById(resID));

            resID = getResources().getIdentifier("mi_track_play" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonTrackPlay = ((ImageButton) findViewById(resID));
        }

        startAtBar = (SeekBar) findViewById(R.id.mi_start_at_bar);
        startAtValue = (TextView) findViewById(R.id.mi_start_at_value);
        enableCheckbox = (CheckBox) findViewById(R.id.mi_enabled);

        enableCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
                  if(ti != null)
                      ti.setEnabled(isChecked);
              }
          }
        );
        startAtBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               startAtValue.setText(String.valueOf(progress));
                TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
                if(ti != null)
                    ti.setStartAt(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonPlayAll.setOnClickListener(this);

        for(int i=0;i<trackLayouts.length;i++) {
            trackLayouts[i].buttonTrackPlay.setOnClickListener(this);
            trackLayouts[i].buttonAddRec.setOnClickListener(this);
            trackLayouts[i].buttonTrackTitle.setOnClickListener(this);
            trackLayouts[i].layoutTrack.setOnClickListener(this);
        }
        //buttonTrackPlay1.setOnClickListener(this);

        //buttonTrackPlay2.setOnClickListener(this);
        //buttonAddRec1.setOnClickListener(this);
        //buttonAddRec2.setOnClickListener(this);
        //buttonTrackTitle1.setOnClickListener(this);
        //buttonTrackTitle2.setOnClickListener(this);
        buttonDeleteTrack.setOnClickListener(this);
        buttonCopyTrack.setOnClickListener(this);
        buttonAddSounds.setOnClickListener(this);

        //layoutTrack1.setOnClickListener(this);
        //layoutTrack2.setOnClickListener(this);
/*
        if(ProjectInfos.getInstance().getSelectedTrackNr() == 1)
        {
            layoutTrack1.setBackgroundColor(getResources().getColor(R.color.track_selected));
        }*/
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
            case R.id.mi_track_copy:
                copyTrack();
                break;
            case R.id.mi_track1:
            case R.id.mi_track_title1:
                ProjectInfos.getInstance().setSelectedTrackNr(1);
                selectCurrentTrack();
                break;
            case R.id.mi_track2:
            case R.id.mi_track_title2:
                ProjectInfos.getInstance().setSelectedTrackNr(2);
                selectCurrentTrack();
                break;
            case R.id.mi_add_sounds:
                addSounds();
                break;
        }
    }

    private void selectCurrentTrack()
    {
        int track = ProjectInfos.getInstance().getSelectedTrackNr();
        for(int i=0;i < layoutTracks.length;i++) {
            if(i + 1 == track)
                layoutTracks[i].setBackgroundColor(getResources().getColor(R.color.track_selected));
            else
                layoutTracks[i].setBackgroundColor(getResources().getColor(R.color.white));
        }

        TrackInfo ti = ProjectInfos.getInstance().getTrack(track);
        if(ti != null) {
            enableCheckbox.setChecked(ti.getEnabled());
            startAtBar.setProgress(ti.getStartAt());
            enableCheckbox.setEnabled(true);
            startAtBar.setEnabled(true);
            buttonDeleteTrack.setEnabled(true);
            buttonCopyTrack.setEnabled(true);
        }
        else {
            enableCheckbox.setEnabled(false);
            startAtBar.setEnabled(false);
            buttonDeleteTrack.setEnabled(false);
            buttonCopyTrack.setEnabled(false);
        }
    }

    // no return-button
    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    public void addTrack(String track_title) {
        TrackInfo ti = new TrackInfo();
        ti.setTrackName(track_title);
        ti.setStartAt(11);
        ProjectInfos.getInstance().addTrack(ti);
        refreshTracks();
        //buttonTrackTitle1.setText(track_title);
    }

    public void changeColor()
    {

    }

    public void deleteTrack()
    {
        ProjectInfos.getInstance().deleteTrack(ProjectInfos.getInstance().getSelectedTrackNr());
        refreshTracks();
    }

    public void copyTrack()
    {
        TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
        if(ti != null)
        {
            ti = new TrackInfo(ti);
            ProjectInfos.getInstance().addTrack(ti);
            refreshTracks();
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
                    addTrack(title.getText().toString());
                    dialog.cancel();
                }
            });
        }
    }

    private void refreshTracks() {
        for(int i=0;i<trackLayouts.length;i++) {
            TrackInfo ti = ProjectInfos.getInstance().getTrack(i+1);
            loadTrackInfo(trackLayouts[i].layoutTrack, trackLayouts[i].buttonTrackTitle, ti);
        }
        selectCurrentTrack();
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
