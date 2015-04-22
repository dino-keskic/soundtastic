package soundtastic.soundtasitc.recording;

import android.os.Environment;

import junit.framework.TestCase;

import java.io.File;

public class RecorderTest extends TestCase {

    private String filePath = Environment.getExternalStorageDirectory()+"/sampleRecording.wav";
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testRecording()
    {
        Recorder recorder =  new Recorder(filePath);
        recorder.recordForNSeconds(10);
        while(recorder.isRecording()) {}
        File file = new File(filePath);
        assertTrue(file.exists());

    }


}