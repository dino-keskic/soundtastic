package soundtastic.soundtasitc;

import android.os.Environment;

import junit.framework.TestCase;
import android.util.Log;

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
import soundtastic.soundtasitc.recording.Recorder;

/**
 * Created by Dino on 29.04.2015.
 */

public class ConversionTest extends TestCase {
    private String filePath = Environment.getExternalStorageDirectory()+"/sampleRecording.wav";

    public void testRecordingConversion()
    {
        Recorder recorder =  new Recorder(filePath);
        recorder.recordForNSeconds(10);
        while(recorder.isRecording()) {}
        File file = new File(filePath);
        if(file.exists())
        {
          WavConverter converter = new WavConverter();
           MidiValues midiValues = converter.convertToMidi(filePath);
        }


        assertEquals(true,true);
    }




   public void testCheckConversion()
    {
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
        MidiValues midiValues =  converter.convertToMidiNew("TestFile2.wav");
        /*WavConverter converter = new WavConverter();
        MidiValues midiValues1= converter.convertToMidiNew("TestFile4.wav");*/

        List<AbstractMap.SimpleEntry<Integer,Integer>> noteMap = midiValues.generateNoteMap();

        /*
        === print noteMap to Android Studio console
        for(int i = 0; i < noteMap.size(); i++)
        {
            Log.d("noteOutPut:", noteMap.get(i).getKey().toString() + " " +
                    noteMap.get(i).getValue().toString());
                    noteMap.get(i).getValue().toString());
        }
        ===*/


        Project testProject = Project.getInstance();
        testProject.setBeatsPerMinute(midiValues.getBeatsPerMinute());
        testProject.setName("testProject");

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

        ProjectToMidiConverter procConverter = new ProjectToMidiConverter();

        try{
            procConverter.writeProjectAsMidi(testProject, file);
        }catch(IOException e){
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());

    }

    public void testSampleConversion()
    {
        WavConverter conv = new WavConverter();
        int midi = conv.convertSampleToMidi();
        Log.d("MIDI_VALUE","midi:"+midi);
        assertEquals(true,true);
    }

}
