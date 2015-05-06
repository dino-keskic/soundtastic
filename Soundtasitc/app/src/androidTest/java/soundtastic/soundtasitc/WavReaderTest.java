package soundtastic.soundtasitc;

import android.os.Environment;
import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by Dino on 29.04.2015.
 */
public class WavReaderTest extends TestCase {

    public void testHeaderIsCorrect()
    {
            FileReader reader = new FileReader();
              byte[] bytes = reader.readFile(Environment.getExternalStorageDirectory()+"/TestFile.wav");
        if(bytes == null)
            Log.e("ERROR","bytes are null");
                String s ="";

            for(int i=0; i<4 ; i++)
            {
             s+=(char) bytes[i];

            }


       assertEquals(s,"RIFF");
    }

}
