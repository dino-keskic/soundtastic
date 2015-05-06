package soundtastic.soundtasitc.test;

import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import  soundtastic.soundtasitc.recording.ExtAudioRecorder;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ExtAudioRecorderTest extends TestCase{


    private ExtAudioRecorder recorder;
    private boolean timerFinished=false;
    private final String filePath = Environment.getExternalStorageDirectory()+"/sampleTestFile.wav";
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }
    public void testIfFilePresent () {

        File file = new File(filePath);
        if(file.exists())
        {
            boolean fileDeleted=file.delete();
            if(!fileDeleted)
                Log.e("FILE_DELETE","file NOT deleted");
        }


            Timer timer = new Timer();
        final ExtAudioRecorder recorder =  new  ExtAudioRecorder(    false,
                MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        recorder.setOutputFile(filePath);
        recorder.prepare();
        recorder.start();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                recorder.stop();
                recorder.release();
                timerFinished=true;
            }
        };
        timer.schedule(timerTask,10000);
        while(!timerFinished)
        {}
        boolean fileExists = false;
        file = new File(filePath);
        if(file.exists())
        {
            fileExists = true;
            boolean fileDeleted=file.delete();
            if(!fileDeleted)
                Log.e("FILE_DELETE","file NOT deleted");
        }
        assertEquals(true,fileExists);

    }

    public void testFileLength()
    {
        File file = new File(filePath);
        if(file.exists())
        {
            boolean fileDeleted=file.delete();
            if(!fileDeleted)
                Log.e("FILE_DELETE","file NOT deleted");
        }


        Timer timer = new Timer();
        final ExtAudioRecorder recorder =  new  ExtAudioRecorder(    false,
                MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        recorder.setOutputFile(filePath);
        recorder.prepare();
        recorder.start();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                recorder.stop();
                recorder.release();
                timerFinished=true;
            }
        };
        timer.schedule(timerTask,10000);
        while(!timerFinished)
        {}
        file = new File(filePath);
        int length=0;
        try {
            MediaPlayer mp = new MediaPlayer();
            FileInputStream fs;
            FileDescriptor fd;
            fs = new FileInputStream(file);
            fd = fs.getFD();
            mp.setDataSource(fd);
            mp.prepare();
           length = mp.getDuration();
            mp.release();
        }
        catch(IOException e)
        {
           Log.d("EXCEPTION",e.getMessage());
        }

        boolean fileExists = false;
        file = new File(filePath);
        if(file.exists())
        {
            fileExists = true;
            boolean fileDeleted=file.delete();
            if(!fileDeleted)
                Log.e("FILE_DELETE","file NOT deleted");
        }

         boolean durationInRange = length <= 10500 && length >= 9500;
        if(durationInRange)
            Log.d("DURATION","Duration  in range. duration:"+String.valueOf(length));
        else
        Log.d("DURATION","Duration not in range.duration:"+String.valueOf(length));
        assertEquals(true,durationInRange);
    }


}