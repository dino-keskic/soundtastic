package soundtastic.soundtasitc;

import android.os.Environment;
import android.util.Log;

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

import soundtastic.soundtasitc.midi.MidiValues;

/**
 * Created by Dino on 29.04.2015.
 */
public class WavConverter {

    public static final int  WAV_FILE_SIZE_START_BYTE = 4;
    public static final int WAV_FILE_SIZE_END_BIT = 7;
    public static final int WAV_FILE_NUM_CHANNELS_START =22;
    public  static final int WAV_FILE_NUM_CHANNELS_END = 23;
    public  static final int WAV_FILE_SAMPLE_RATE_START = 24;
    public static final int WAV_FILE_SAMPLE_RATE_END = 27;
    public  static final int WAV_FILE_RESOLUTION_START = 34;
    public  static final int WAV_FILE_RESOLUTION_END = 35;
    public  static final int WAV_FILE_DATA_SIZE_START = 40;
    public  static final int WAV_FILE_DATA_SIZE_END = 43;


    private int beatsPerMinute  = 120;

    public int getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }



    public MidiValues convertToMidi (String filePath)
    {
        try {
            FileReader reader = new FileReader();

            byte[] waveData = reader.readFile( filePath);
            if(waveData == null)
            {
                Log.e("WAV_ERROR", "wav file not found!");
                return  null;
            }

            int fileSize = byteToInt(waveData, WAV_FILE_SIZE_START_BYTE);
            int numChannels = byteToShort(waveData, WAV_FILE_NUM_CHANNELS_START);
            int sampleRate = byteToInt(waveData, WAV_FILE_SAMPLE_RATE_START);
            int resolution = byteToShort(waveData, WAV_FILE_RESOLUTION_START);
            int dataSize = byteToInt(waveData, WAV_FILE_DATA_SIZE_START);

            int bpm = 60;
            float beatDuration = 60.0f / bpm;
            float shortestPitch = 1 / 16.0f; //should be 1/16

            float shortestPitchDuration = beatDuration * shortestPitch;

            int chunkSize = Math.round(shortestPitchDuration * sampleRate);


            int resolutionBytes = resolution / 8;
            int rawSize = (int) ((double) dataSize / (resolutionBytes * numChannels));
            double[] rawData = new double[rawSize];

            int j = 0;

            for (int i = 44; i < dataSize; i += (resolutionBytes * numChannels), j++) {
                rawData[j] = (resolutionBytes == 1)
                        ? waveData[i]
                        : byteToShort(waveData, i);
            }

            int[] midiData = new int[rawSize / chunkSize];
            //double[] freqData = new double[rawSize / chunkSize];
            int k = 0;

            MidiValues midiValues = new MidiValues((int) bpm, (int) chunkSize, (int) sampleRate);

            while (k < midiData.length) {
                double[] dataChunk = Arrays.copyOfRange(rawData, k * chunkSize, (k + 1) * chunkSize);

                int fourierChunkSize = chunkSize * 2;
                double[] fourierChunk = new double[fourierChunkSize];

                for (int i = 0; i < chunkSize; i++) {
                    fourierChunk[2 * i] = dataChunk[i];
                }

                int fourierDataSize = sampleRate;
                int offset = fourierDataSize - fourierChunkSize;
                double[] fourierData = new double[fourierDataSize];


              /*  for(int l = 0; l < fourierDataSize / fourierChunkSize; l++) {
                    for (int i = 0; i < fourierChunkSize; i++) {
                        fourierData[l * fourierChunkSize + i] = fourierChunk[i];
                    }
                }*/

                for (int i = offset; i < fourierDataSize; i++) {
                    fourierData[i] = fourierChunk[i-offset];
                }

                DoubleFFT_1D fft = new DoubleFFT_1D(chunkSize);

                fft.complexForward(fourierChunk);

                double max_fftval = -1;
                int max_i = -1;

                for (int i = 0; i < fourierChunk.length; i += 2) // we are only looking at the half of the spectrum
                {
                    //double hz = ((i / 2.0) / fourierChunk.length) * chunkSize;

                    // complex numbers -> vectors, so we compute the length of the vector, which is sqrt(realpart^2+imaginarypart^2)
                    double vlen = Math.sqrt(fourierChunk[i] * fourierChunk[i] + fourierChunk[i + 1] * fourierChunk[i + 1]);

                    if (max_fftval < vlen) {
                        // if this length is bigger than our stored biggest length
                        max_fftval = vlen;
                        max_i = i;
                    }
                }

                double  domFrequency = max_i* (sampleRate / 2.0) / chunkSize;

                // double domFrequency = ((max_i/2)/fourierChunkSize)*sampleRate;

                int midiVal = 0;

                if (domFrequency < 27.5 || domFrequency > 4186) {
                    midiVal = 0; //midiData[k]
                } else {
                    double log2 = Math.log10(domFrequency / 440) / Math.log10(2);
                    midiVal = (int) (12 * log2) + 69; //midiData[k]
                }
                midiData[k]= midiVal;
                int n = k ;
               while (n >= 0) {

                    if (midiData[n] != 0) {
                        Log.d("MIDI VALUE", "Midi valut at " + n + " :" + midiData[n]);
                        midiValues.addMidiNum(midiData[n]);
                        break;
                    }
                    n--;
                }

              // midiValues.addMidiNum(midiVal);

                k++;

            }

         /*  midiData = null;
           freqData = null;
           rawData = null;
         //  System.gc();*/


            return midiValues;
        }
        catch (Exception e)
        {
            Log.e("Exception", e.getMessage());
        }

        return null;
    }


    public int byteToInt(byte[] data, int startIndex)
    {
        int result =
                (data[startIndex+3]<<24)&0xff000000|
                        (data[startIndex+2]<<16)&0x00ff0000|
                        (data[startIndex+1]<< 8)&0x0000ff00|
                        (data[startIndex]<< 0)&0x000000ff;
        Log.d("RESULT",String.valueOf(result));
        return result;

    }

    public short byteToShort(byte[] data, int startIndex)
    {
        short ret = 0;
        ret =(short)((data[startIndex+1]<< 8)&0xff00|
                (data[startIndex]<< 0)&0x00ff);
        return ret;
    }

}
