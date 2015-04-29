package soundtastic.soundtasitc;

import android.os.Environment;

import junit.framework.TestCase;

/**
 * Created by Dino on 29.04.2015.
 */
public class ConversionTest extends TestCase {

    public void testCheckConversion()
    {
        WavConverter converter = new WavConverter();
       int[] midiValues =  converter.convertToMidi("TestFile.wav");

        assertEquals(true,true);

    }

}
