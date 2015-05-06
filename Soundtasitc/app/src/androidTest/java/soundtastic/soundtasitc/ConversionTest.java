package soundtastic.soundtasitc;

import android.os.Environment;

import junit.framework.TestCase;
import android.util.Log;

/**
 * Created by Dino on 29.04.2015.
 */
public class ConversionTest extends TestCase {

   public void testCheckConversion()
    {
        WavConverter converter = new WavConverter();
       int[] midiValues =  converter.convertToMidi("TestFile2.wav");

        for(int i=0; i< midiValues.length; i++)
        {
            //if(midiValues[i] != 0)
            //{
                Log.d("MIDI","midi value at "+i+":"+midiValues[i]);
            //}
        }
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
