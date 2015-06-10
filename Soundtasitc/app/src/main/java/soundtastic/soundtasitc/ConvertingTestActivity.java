package soundtastic.soundtasitc;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

import soundtastic.soundtasitc.midi.MidiException;
import soundtastic.soundtasitc.midi.MidiValues;
import soundtastic.soundtasitc.midi.ProjectToMidiConverter;
import soundtastic.soundtasitc.note.MusicalInstrument;
import soundtastic.soundtasitc.note.MusicalKey;
import soundtastic.soundtasitc.note.NoteEvent;
import soundtastic.soundtasitc.note.NoteName;
import soundtastic.soundtasitc.note.Project;
import soundtastic.soundtasitc.note.Track;


public class ConvertingTestActivity extends ActionBarActivity implements View.OnClickListener {

    private Button convertButton;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.convertButton)
        {
            Log.d("CONVERT","Conversion started");
            File file;
            File folder = new File(Environment.getExternalStorageDirectory() + "/soundtastic");
            file = new File(folder.getAbsolutePath(), "test.midi");

            if (!folder.exists()) {
                folder.mkdir();
            }
            if(file.exists())
            {
                file.delete();
            }
            WavConverter converter = new WavConverter();
            MidiValues midiValues1= converter.convertToMidi("TestFile3.wav");

            List<AbstractMap.SimpleEntry<Integer,Integer>> noteMap = midiValues1.generateNoteMap();

        /*
        === print noteMap to Android Studio console
        for(int i = 0; i < noteMap.size(); i++)
        {
            Log.d("noteOutPut:", noteMap.get(i).getKey().toString() + " " +
                    noteMap.get(i).getValue().toString());
                    noteMap.get(i).getValue().toString());
        }
        ===*/



            Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
        Project.getInstance().setName("Test project");
            Project.getInstance().setBeatsPerMinute(60);
            int currentTicks = 0;
            for(int i = 0; i < noteMap.size(); i++)
            {
                NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
                NoteEvent note_begin = new NoteEvent(noteName, true);
                firstTrack.addNoteEvent(currentTicks, note_begin);
                currentTicks += midiValues1.getNoteLength(noteMap.get(i).getValue()) * Project.getInstance().getBeatsPerMinute() * 8;

                Log.d("NOTELENGTH", Double.toString(midiValues1.getNoteLength(noteMap.get(i).getValue())));
                Log.d("CURRENTTICKS", Integer.toString(currentTicks));

                NoteEvent note_end = new NoteEvent(noteName, false);
                firstTrack.addNoteEvent(currentTicks, note_end);
            }

            Project.getInstance().addTrack("first", firstTrack);

            ProjectToMidiConverter procConverter = new ProjectToMidiConverter();

            try{
                procConverter.writeProjectAsMidi(Project.getInstance(), file);

            }catch(IOException e){
                e.printStackTrace();
            } catch (MidiException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converting_test);
        convertButton =(Button) findViewById(R.id.convertButton);
        convertButton.setOnClickListener(ConvertingTestActivity.this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_converting_test, menu);
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
}
