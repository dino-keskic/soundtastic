package soundtastic.soundtasitc.recording;

import android.media.AudioFormat;
import android.media.MediaRecorder;

import java.util.TimerTask;
import java.util.Timer;
import android.text.format.Time;
import  java.io.File;

/**
 * Created by Dino on 22.04.2015.
 */
public class Recorder {

    private ExtAudioRecorder recorder;
    private String filePath;
    private boolean uncompressed = false;
    private int audioSource =  MediaRecorder.AudioSource.MIC;
    private int sampleRate = 44100;
    private Time startTime = new Time();
    private Time endTime = new Time();
    private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO ;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    public Recorder (String filePath) {
            recorder =  new  ExtAudioRecorder(    uncompressed,
               audioSource,
                sampleRate,
                channelConfig,
                audioFormat);
        this.filePath = filePath;
        recorder.setOutputFile(filePath);

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isUncompressed() {
        return uncompressed;
    }

    public void setUncompressed(boolean uncompressed) {
        this.uncompressed = uncompressed;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void recordForNSeconds(int n)
    {


       startRecording();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
               stopRecording();
            }
        };
        timer.schedule(timerTask,n*1000);


    }

    public void startRecording()
    {
        recorder.prepare();
        recorder.start();
        startTime.setToNow();
    }

    public void stopRecording()
    {
        recorder.stop();
        recorder.release();
        endTime.setToNow();
    }

    public boolean isRecording()
    {
        return recorder.getState() == ExtAudioRecorder.State.RECORDING;
    }

    public Time getDifference()
    {

    }

    public boolean deleteLastRecording()
    {
        boolean deleted = false;
        File file = new File(filePath);
        if(file.exists())
        {
            deleted = file.delete();
        }
        return deleted;
    }

}
