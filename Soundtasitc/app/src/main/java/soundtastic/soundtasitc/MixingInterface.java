package soundtastic.soundtasitc;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import soundtastic.soundtasitc.note.Track;
import soundtastic.soundtasitc.playmidi.PlayMIDI;
import soundtastic.soundtasitc.TrackInfo;

public class MixingInterface extends Activity implements View.OnClickListener {

    public int MAX_TRACK = 4;
    int resID = 0;
    
    TrackLayout[] trackLayouts;
    MediaPlayer[] mediaPlayers;

    ImageButton buttonPlayAll;
    ImageButton buttonStopAll;
    ImageButton buttonExportMidi;
    ImageButton buttonAddSounds;
    Button buttonDeleteTrack;
    Button buttonCopyTrack;
    Button buttonRenameTrack;
    CheckBox enableCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing_interface);
        ProjectInfos.getInstance().setSelectedTrackNr(1);

        // Initialisation of all elements
        trackLayouts = new TrackLayout[MAX_TRACK];
        mediaPlayers = new MediaPlayer[MAX_TRACK];

        mediaPlayers[0] = MediaPlayer.create(this, R.raw.song);
        mediaPlayers[1] = MediaPlayer.create(this, R.raw.teddybear);
        mediaPlayers[2] = MediaPlayer.create(this, R.raw.hideaway);
        mediaPlayers[3] = MediaPlayer.create(this, R.raw.because);

        buttonPlayAll = (ImageButton) findViewById(R.id.mixint_play_all);
        buttonStopAll = (ImageButton) findViewById(R.id.mixint_stop_all);
        buttonExportMidi = (ImageButton) findViewById(R.id.mixint_export_midi);
        buttonAddSounds = (ImageButton) findViewById(R.id.mixint_add_sounds);
        buttonDeleteTrack = (Button) findViewById(R.id.mixint_track_delete);
        buttonCopyTrack = (Button) findViewById(R.id.mixint_track_copy);
        buttonRenameTrack = (Button) findViewById(R.id.mixint_track_rename);
        enableCheckbox = (CheckBox) findViewById(R.id.mixint_enabled);

        for(int i = 0;i < MAX_TRACK;i++)
        {
            trackLayouts[i] = new TrackLayout();

            resID = getResources().getIdentifier("mixint_rec_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonAddRec = ((ImageButton) findViewById(resID));

            resID = getResources().getIdentifier("mixint_layout_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].layoutTrack = ((RelativeLayout) findViewById(resID));

            resID = getResources().getIdentifier("mixint_title_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonTrackTitle = ((TextView) findViewById(resID));

            resID = getResources().getIdentifier("mixint_play_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonTrackPlay = ((ImageButton) findViewById(resID));

            resID = getResources().getIdentifier("mixint_stop_track" + Integer.toString(i+1),
                "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonTrackStop = ((ImageButton) findViewById(resID));

            resID = getResources().getIdentifier("mixint_edit_track" + Integer.toString(i+1),
                    "id", "soundtastic.soundtasitc");
            trackLayouts[i].buttonEditTrack = ((Button) findViewById(resID));
        }

        enableCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
                  if(ti != null)
                      ti.setEnabled(isChecked);
              }
          }
        );

        // ClickListener
        buttonPlayAll.setOnClickListener(this);
        buttonStopAll.setOnClickListener(this);

        buttonPlayAll.setVisibility(View.VISIBLE);
        buttonStopAll.setVisibility(View.INVISIBLE);

        for(int i=0;i< trackLayouts.length;i++) {
            trackLayouts[i].buttonTrackPlay.setOnClickListener(this);
            trackLayouts[i].buttonTrackStop.setOnClickListener(this);
            trackLayouts[i].buttonAddRec.setOnClickListener(this);
            trackLayouts[i].buttonTrackTitle.setOnClickListener(this);
            trackLayouts[i].layoutTrack.setOnClickListener(this);
            trackLayouts[i].buttonEditTrack.setOnClickListener(this);
        }
        buttonDeleteTrack.setOnClickListener(this);
        buttonCopyTrack.setOnClickListener(this);
        buttonRenameTrack.setOnClickListener(this);
        buttonAddSounds.setOnClickListener(this);

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

        Intent record = new Intent(this, RecordInterface.class);
        Intent grid_view = new Intent(this, GridView.class);
        int trackNr = 0;
        String viewName = getResources().getResourceEntryName(v.getId());

        if(viewName.startsWith("mixint_title_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_title_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            selectCurrentTrack();
            return;
        }
        if(viewName.startsWith("mixint_layout_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_layout_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            selectCurrentTrack();
            return;
        }
        if(viewName.startsWith("mixint_rec_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_rec_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            startActivity(record);
            return;
        }
        if(viewName.startsWith("mixint_play_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_play_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            trackLayouts[trackNr-1].buttonTrackPlay.setVisibility(View.INVISIBLE);
            trackLayouts[trackNr-1].buttonTrackStop.setVisibility(View.VISIBLE);

            PlayMIDI.play(mediaPlayers[trackNr-1]);
            return;
        }

        if(viewName.startsWith("mixint_stop_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_stop_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            trackLayouts[trackNr-1].buttonTrackPlay.setVisibility(View.VISIBLE);
            trackLayouts[trackNr-1].buttonTrackStop.setVisibility(View.INVISIBLE);
            buttonPlayAll.setVisibility(View.VISIBLE);
            buttonStopAll.setVisibility(View.INVISIBLE);

            PlayMIDI.stop(mediaPlayers[trackNr - 1]);
            return;
        }

        if(viewName.startsWith("mixint_edit_track")) {
            trackNr = Integer.parseInt(viewName.replace("mixint_edit_track", ""));
            ProjectInfos.getInstance().setSelectedTrackNr(trackNr);
            startActivity(grid_view);
            return;
        }

        if(viewName.startsWith("mixint_play_all")) {
            buttonStopAll.setVisibility(View.VISIBLE);
            buttonPlayAll.setVisibility(View.INVISIBLE);
            freeze();
            for(int tracknr = 0; tracknr < MAX_TRACK; tracknr++) {
                TrackInfo ti = ProjectInfos.getInstance().getTrack(tracknr + 1);
                if(ti != null && ti.getEnabled() == true) {
                    PlayMIDI.play(mediaPlayers[tracknr]);
                    trackLayouts[tracknr].buttonTrackPlay.setVisibility(View.INVISIBLE);
                    trackLayouts[tracknr].buttonTrackStop.setVisibility(View.VISIBLE);
                }
            }
            return;
        }

        if(viewName.startsWith("mixint_stop_all")) {
            buttonStopAll.setVisibility(View.INVISIBLE);
            buttonPlayAll.setVisibility(View.VISIBLE);
            unfreeze();

            for(int tracknr = 0; tracknr < MAX_TRACK; tracknr++) {
                TrackInfo ti = ProjectInfos.getInstance().getTrack(tracknr + 1);
                if(ti != null && ti.getEnabled() == true) {
                    PlayMIDI.stop(mediaPlayers[tracknr]);
                    trackLayouts[tracknr].buttonTrackPlay.setVisibility(View.VISIBLE);
                    trackLayouts[tracknr].buttonTrackStop.setVisibility(View.INVISIBLE);
                }
            }
            return;
        }

        switch(v.getId()) {
            case R.id.mixint_track_delete:
                deleteTrack();
                break;
            case R.id.mixint_track_copy:
                copyTrack();
                break;
            case R.id.mixint_track_rename:
                renameTrack();
                break;
            case R.id.mixint_add_sounds:
                createTrack();
                break;
            case R.id.mixint_export_midi:
                exportMidi();
                break;

        }

    }

    private void selectCurrentTrack() {
        int amount_of_tracks = ProjectInfos.getInstance().getAmountOfTracks();
        int track = ProjectInfos.getInstance().getSelectedTrackNr();
        for (int i = 0; i < trackLayouts.length; i++) {
            if (i + 1 == track)
                trackLayouts[i].layoutTrack.setBackgroundColor(getResources().getColor(R.color.track_selected));
            else
                trackLayouts[i].layoutTrack.setBackgroundColor(getResources().getColor(R.color.white));
        }

        TrackInfo ti = ProjectInfos.getInstance().getTrack(track);

        if(amount_of_tracks == MAX_TRACK)
        {
            buttonCopyTrack.setEnabled(false);
            buttonAddSounds.setVisibility(View.INVISIBLE);
            enableCheckbox.setChecked(ti.getEnabled());
        }

        if(amount_of_tracks < MAX_TRACK && amount_of_tracks != 0)
        {
            buttonCopyTrack.setEnabled(true);
            buttonAddSounds.setVisibility(View.VISIBLE);
            if(ti != null)
            {
                enableCheckbox.setChecked(ti.getEnabled());
            }
            enableCheckbox.setEnabled(true);
            buttonDeleteTrack.setEnabled(true);
            buttonCopyTrack.setEnabled(true);
            buttonRenameTrack.setEnabled(true);
        }

        if(amount_of_tracks == 0)
        {
            enableCheckbox.setEnabled(false);
            buttonDeleteTrack.setEnabled(false);
            buttonCopyTrack.setEnabled(false);
            buttonRenameTrack.setEnabled(false);
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
        ti.setStartAt(0);
        ProjectInfos.getInstance().addTrack(ti);
        refreshTracks();
    }

    public void editTrackName(String track_title) {
        TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
        ti.setTrackName(track_title);
        refreshTracks();
    }

    public void deleteTrack()
    {
        int trackNr = ProjectInfos.getInstance().getSelectedTrackNr();
        TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
        if(ti != null) {
            ProjectInfos.getInstance().deleteTrack(ProjectInfos.getInstance().getSelectedTrackNr());
            refreshTracks();
        }

        if(trackNr < MAX_TRACK) {
            mediaPlayers[trackNr - 1] = mediaPlayers[trackNr];
        }
    }

    public void copyTrack()
    {
        int trackNr = ProjectInfos.getInstance().getSelectedTrackNr();
        TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
        if(ti != null) {
            ti = new TrackInfo(ti);
            ProjectInfos.getInstance().addTrack(ti);
            refreshTracks();
        }

        mediaPlayers[trackNr - 1] = mediaPlayers[trackNr];

    }

    public void createTrack()
    {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.add_sounds);
        dialog.setTitle("Add new track");
        dialog.show();

        final Button mic = (Button) dialog.findViewById(R.id.mixint_add_sounds_mic);
        final EditText title = (EditText) dialog.findViewById(R.id.mixint_add_sounds_title);

        if(mic != null)
        {
            mic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(title.getText().toString().isEmpty()) {
                        addTrack(title.getHint().toString());
                    }
                    else {
                        addTrack(title.getText().toString());
                    }
                    dialog.cancel();
                }
            });
        }
    }

    private void renameTrack()
    {
        TrackInfo ti = ProjectInfos.getInstance().getSelectedTrack();
        if(ti == null)
        {
            return;
        }

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.rename_track);
        dialog.setTitle("Rename track");
        dialog.show();

        final Button mic = (Button) dialog.findViewById(R.id.mixint_rename_track_save);
        final EditText title = (EditText) dialog.findViewById(R.id.mixint_rename_track_title);
        final TextView old_title = (TextView) dialog.findViewById(R.id.mixint_rename_track_oldtitle);

        old_title.setText(ti.getTrackName().toString());

        if(mic != null)
        {
            mic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(title.getText().toString().isEmpty()) {
                        editTrackName(title.getHint().toString());
                    }
                    else {
                        editTrackName(title.getText().toString());
                    }
                    dialog.cancel();
                }
            });
        }
    }

    private void refreshTracks() {
        for(int i=0;i< trackLayouts.length;i++) {
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

    private void exportMidi()
    {
        // TODO: Export-Function
    }

    private void freeze()
    {
        enableCheckbox.setEnabled(false);
        buttonCopyTrack.setEnabled(false);
        buttonDeleteTrack.setEnabled(false);
        buttonRenameTrack.setEnabled(false);
        buttonAddSounds.setEnabled(false);
        buttonExportMidi.setEnabled(false);
        for(int tracknr = 0; tracknr < MAX_TRACK; tracknr++) {
            TrackInfo ti = ProjectInfos.getInstance().getTrack(tracknr + 1);
            if(ti != null) {
                trackLayouts[tracknr].buttonAddRec.setEnabled(false);
                trackLayouts[tracknr].buttonEditTrack.setEnabled(false);
            }
        }
    }

    private void unfreeze()
    {
        enableCheckbox.setEnabled(true);
        buttonCopyTrack.setEnabled(true);
        buttonDeleteTrack.setEnabled(true);
        buttonRenameTrack.setEnabled(true);
        buttonAddSounds.setEnabled(true);
        buttonExportMidi.setEnabled(true);
        for(int tracknr = 0; tracknr < MAX_TRACK; tracknr++) {
            TrackInfo ti = ProjectInfos.getInstance().getTrack(tracknr + 1);
            if(ti != null) {
                trackLayouts[tracknr].buttonAddRec.setEnabled(true);
                trackLayouts[tracknr].buttonEditTrack.setEnabled(true);
            }
        }
    }
}
