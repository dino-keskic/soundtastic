package soundtastic.soundtasitc.recording;

import android.os.Environment;
import android.util.Log;

import junit.framework.TestCase;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;

public class RecorderTest extends TestCase {

    private String filePath = Environment.getExternalStorageDirectory()+"/sampleRecording.wav";
    private long duration = 0;
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

    public void testRecordingDuration()
    {
        duration = 0;

        PropertyChangeListener pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                Log.d("propertyname", "name:" + event.getPropertyName());
                duration = (long)event.getNewValue();
            }
        };

        Recorder recorder =  new Recorder(filePath);
        recorder.addRecordingTimeChangeListener(pcl);
         Random r = new Random();
        int testDur = r.nextInt(20)+1;
        recorder.recordForNSeconds(testDur);
        while(recorder.isRecording()) {}
        File file = new File(filePath);

        assertEquals(testDur, duration);
    }

}