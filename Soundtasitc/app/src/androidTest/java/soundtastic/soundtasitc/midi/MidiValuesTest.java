package soundtastic.soundtasitc.midi;

import android.os.Debug;
import android.os.Environment;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

import android.provider.ContactsContract;
import android.util.Log;

import soundtastic.soundtasitc.WavConverter;
import soundtastic.soundtasitc.note.MusicalInstrument;
import soundtastic.soundtasitc.note.MusicalKey;
import soundtastic.soundtasitc.note.NoteEvent;
import soundtastic.soundtasitc.note.NoteName;
import soundtastic.soundtasitc.note.Project;
import soundtastic.soundtasitc.note.Track;
import java.io.File;

public class MidiValuesTest extends TestCase {

    private File file;

    private MidiValues midiValues;


    protected void setUp() throws IOException {

        File folder = new File(Environment.getExternalStorageDirectory() + "/soundtastic");
        file = new File(folder.getAbsolutePath(), "test.midi");

        if (!folder.exists()) {
            folder.mkdir();
        }
        if(file.exists())
        {
            file.delete();
        }
        assertTrue(folder.exists());
    }

  public void MidiValuesToMidiStruct()
    {
        midiValues = new MidiValues(60, 1300, 44100);

        int note = 66;
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 34; j++)
            {
                midiValues.addMidiNum(note);
            }
            note++;
        }

        // finished initializing

        List<AbstractMap.SimpleEntry<Integer,Integer>> noteMap = midiValues.generateNoteMap();


        Project testProject = Project.getInstance();
        testProject.setBeatsPerMinute(midiValues.getBeatsPerMinute());
        testProject.setName("testProject");

       // Project testProject = new Project("testProject", midiValues.getBeatsPerMinute());
        Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

        int currentTicks = 0;
        for(int i = 0; i < noteMap.size(); i++)
        {
            NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
            NoteEvent note_begin = new NoteEvent(noteName, true);
            firstTrack.addNoteEvent(currentTicks, note_begin);
            currentTicks += midiValues.getNoteLength(noteMap.get(i).getValue()) * Project.getInstance().getBeatsPerMinute() * 8;

            Log.d("NOTELENGTH", Double.toString(midiValues.getNoteLength(noteMap.get(i).getValue())));
            Log.d("CURRENTTICKS", Integer.toString(currentTicks));

            NoteEvent note_end = new NoteEvent(noteName, false);
            firstTrack.addNoteEvent(currentTicks, note_end);
        }

        Project.getInstance().addTrack("first", firstTrack);

        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        try{
            converter.writeProjectAsMidi(Project.getInstance(), file);
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());



    }

    public void testMidiConversion()
    {
        WavConverter converter = new WavConverter();
        MidiValues midiValues1= converter.convertToMidiNew("TestFile4.wav");

        List<AbstractMap.SimpleEntry<Integer,Integer>> noteMap = midiValues1.generateNoteMap();

        /*
        === print noteMap to Android Studio console
        for(int i = 0; i < noteMap.size(); i++)
        {
            Log.d("noteOutPut:", noteMap.get(i).getKey().toString() + " " +
                    noteMap.get(i).getValue().toString());
        }
        ===*/


        Project testProject = Project.getInstance();
        testProject.setBeatsPerMinute(midiValues1.getBeatsPerMinute());
        testProject.setName("testProject");

        Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

        int currentTicks = 0;
        for(int i = 0; i < noteMap.size(); i++)
        {
            NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
            NoteEvent note_begin = new NoteEvent(noteName, true);
            firstTrack.addNoteEvent(currentTicks, note_begin);
            currentTicks += midiValues1.getNoteLength(noteMap.get(i).getValue()) * testProject.getBeatsPerMinute() * 8;


}