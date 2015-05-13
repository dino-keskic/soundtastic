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
        assertTrue(folder.exists());
    }

 /*   public void testMidiValuesToMidiStruct()
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

        /*
        === print noteMap to Android Studio console
        for(int i = 0; i < noteMap.size(); i++)
        {
            Log.d("noteOutPut:", noteMap.get(i).getKey().toString() + " " +
                    noteMap.get(i).getValue().toString());
        }
        ===*/

/*
        Project testProject = new Project("testProject", midiValues.getBeatsPerMinute());
        Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

        int currentTicks = 0;
        for(int i = 0; i < noteMap.size(); i++)
        {
            NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
            NoteEvent note_begin = new NoteEvent(noteName, true);
            firstTrack.addNoteEvent(currentTicks, note_begin);
            currentTicks += midiValues.getNoteLength(noteMap.get(i).getValue()) * testProject.getBeatsPerMinute() * 8;

            Log.d("NOTELENGTH", Double.toString(midiValues.getNoteLength(noteMap.get(i).getValue())));
            Log.d("CURRENTTICKS", Integer.toString(currentTicks));

            NoteEvent note_end = new NoteEvent(noteName, false);
            firstTrack.addNoteEvent(currentTicks, note_end);
        }

        testProject.addTrack("first", firstTrack);

        ProjectToMidiConverter converter = new ProjectToMidiConverter();

        try{
            converter.writeProjectAsMidi(testProject, file);
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());



    }*/

    public void testMidiConversion()
    {
        WavConverter converter = new WavConverter();
        MidiValues midiValues1= converter.convertToMidi("TestFile4.wav");

        List<AbstractMap.SimpleEntry<Integer,Integer>> noteMap = midiValues1.generateNoteMap();

        //=== print noteMap to Android Studio console
        //for(int i = 0; i < noteMap.size(); i++)
        //{
        //    Log.d("noteOutPut:", noteMap.get(i).getKey().toString() + " " +
        //            noteMap.get(i).getValue().toString());
        //}


        Project testProject = new Project("testProject", midiValues1.getBeatsPerMinute());
        Track firstTrack = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

        int currentTicks = 0;
        for(int i = 0; i < noteMap.size(); i++)
        {
            NoteName noteName = NoteName.getNoteNameFromMidiValue(noteMap.get(i).getKey());
            NoteEvent note_begin = new NoteEvent(noteName, true);
            firstTrack.addNoteEvent(currentTicks, note_begin);
            currentTicks += midiValues1.getNoteLength(noteMap.get(i).getValue()) * testProject.getBeatsPerMinute() * 8;

            Log.d("NOTELENGTH", Double.toString(midiValues1.getNoteLength(noteMap.get(i).getValue())));
            Log.d("CURRENTTICKS", Integer.toString(currentTicks));

            NoteEvent note_end = new NoteEvent(noteName, false);
            firstTrack.addNoteEvent(currentTicks, note_end);
        }

        testProject.addTrack("first", firstTrack);

        ProjectToMidiConverter procConverter = new ProjectToMidiConverter();

        try{
            procConverter.writeProjectAsMidi(testProject, file);
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());
        //assertTrue(true);
    }

}