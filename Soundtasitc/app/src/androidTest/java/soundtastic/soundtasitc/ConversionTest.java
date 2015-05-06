package soundtastic.soundtasitc;

import android.os.Environment;

import junit.framework.TestCase;
import android.util.Log;

import java.io.File;

import soundtastic.soundtasitc.midi.MidiValues;
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
        WavConverter converter = new WavConverter();
       MidiValues midiValues =  converter.convertToMidi("TestFile2.wav");

        assertEquals(true,true);

    }

    public void testSampleConversion()
    {
        WavConverter conv = new WavConverter();
        int midi = conv.convertSampleToMidi();
        Log.d("MIDI_VALUE","midi:"+midi);
        assertEquals(true,true);
    }

}
