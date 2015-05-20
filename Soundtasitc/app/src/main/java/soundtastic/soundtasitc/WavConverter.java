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


   public  MidiValues convertToMidi (String filePath)
   {
       FileReader reader = new FileReader();

       byte[] waveData = reader.readFile(Environment.getExternalStorageDirectory()+"/"+filePath);

       int fileSize = byteToInt(waveData, WAV_FILE_SIZE_START_BYTE);
       int numChannels = byteToShort(waveData, WAV_FILE_NUM_CHANNELS_START);
       int sampleRate = byteToInt(waveData, WAV_FILE_SAMPLE_RATE_START);
       int resolution = byteToShort(waveData, WAV_FILE_RESOLUTION_START);
       int dataSize =  byteToInt(waveData, WAV_FILE_DATA_SIZE_START);

       int bpm = 120;
       float beatDuration = 60.0f/bpm;
       float shortestPitch = 1/16.0f;

       float shortestPitchDuration = beatDuration*shortestPitch;

       int chunkSize = (int)(shortestPitchDuration * sampleRate);

       int resolutionBytes = resolution / 8;
       int rawSize =(int) ((double)dataSize/(resolutionBytes*numChannels));
       double[] rawData = new double[rawSize];

       int j = 0;

       for(int i = 44; i<dataSize; i+= (resolutionBytes * numChannels), j++)
       {
           rawData[j] = (resolutionBytes == 1)
                   ? waveData[i]
                   : byteToShort(waveData, i);
       }

       waveData = null;

       System.gc();

       int[] midiData = new int[rawSize / chunkSize];
       double[] freqData = new double[rawSize / chunkSize];
       int k = 0;

       MidiValues midiValues = new MidiValues((int)bpm,(int)chunkSize,(int)sampleRate);
       while(k < midiData.length)
       {
           double[] dataChunk = Arrays.copyOfRange(rawData,k*chunkSize,(k+1)*chunkSize);
           double[] fourierChunk = new double[chunkSize * 2];
           int divider = 44100 / chunkSize;

           /*for(int i=0; i< chunkSize; i++)
           {
               fourierChunk[2*i] = dataChunk[i];
           }*/

           for(int i=0; i< chunkSize; i++)
           {
               fourierChunk[2*i] = dataChunk[i];
           }

           //int fourierDataSize = divider * chunkSize *2;
           //double[] fourierData = new double[fourierDataSize];

           int offset = 44100 - chunkSize;
           int fourierDataSize = offset + chunkSize *2;
           double[] fourierData = new double[fourierDataSize];

           /*for(int i = 0; i < divider; i++)
           {
               for(int l =0; l< chunkSize*2; l++) {
                   fourierData[i*chunkSize*2+l] = fourierChunk[l];
               }
           }*/

           for(int i = offset; i < fourierDataSize; i++)
           {
                fourierData[i] = fourierChunk[i-offset];
           }

           DoubleFFT_1D fft = new DoubleFFT_1D(fourierDataSize);

           fft.complexForward(fourierData, offset);

           double max_fftval = -1;
           int max_i = -1;

           for (int i = 0; i < fourierData.length; i += 2) // we are only looking at the half of the spectrum
           {
               //double hz = ((i / 2.0) / fourierChunk.length) * chunkSize;

               // complex numbers -> vectors, so we compute the length of the vector, which is sqrt(realpart^2+imaginarypart^2)
               double vlen = Math.sqrt(fourierData[i] * fourierData[i] + fourierData[i + 1] * fourierData[i + 1]);

               if (max_fftval < vlen) {
                   // if this length is bigger than our stored biggest length
                   max_fftval = vlen;
                   max_i = i;
               }
           }

           double domFrequency = max_i * (sampleRate / 2.0) / fourierDataSize;
           //double domFrequency = ((max_i / 2.0) / fourierChunk.length) * chunkSize*2;
           freqData[k] = domFrequency;

           if(domFrequency < 27.5 || domFrequency > 4186)
           {
               midiData[k] = 0;
           }
           else
           {
               double log2 = Math.log10(domFrequency/440)/Math.log10(2);
               midiData[k] = (int)(12 * log2) + 69;
           }

           int n = k - 1;
           while(n >=0)
           {

               if( midiData[n] !=0 )
               {
                   midiValues.addMidiNum(midiData[n]);
                   break;
               }
               n--;
           }
           k++;

       }

       midiData = null;
       freqData = null;
       rawData = null;
       System.gc();

        midiValues.generateNoteMap();
       return midiValues;
   }


    public MidiValues convertToMidiNew (String filePath)
    {
        try {
            FileReader reader = new FileReader();

            byte[] waveData = reader.readFile(Environment.getExternalStorageDirectory() + "/" + filePath);

            int fileSize = byteToInt(waveData, WAV_FILE_SIZE_START_BYTE);
            int numChannels = byteToShort(waveData, WAV_FILE_NUM_CHANNELS_START);
            int sampleRate = byteToInt(waveData, WAV_FILE_SAMPLE_RATE_START);
            int resolution = byteToShort(waveData, WAV_FILE_RESOLUTION_START);
            int dataSize = byteToInt(waveData, WAV_FILE_DATA_SIZE_START);

            int bpm = 120;
            float beatDuration = 60.0f / bpm;
            float shortestPitch = 1 / 4.0f; //should be 1/16

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


                for(int l = 0; l < fourierDataSize / fourierChunkSize; l++) {
                    for (int i = 0; i < fourierChunkSize; i++) {
                        fourierData[l * fourierChunkSize + i] = fourierChunk[i];
                    }
                }

                /*for (int i = offset; i < fourierDataSize; i++) {
                    fourierData[i] = fourierChunk[i-offset];
                }*/

                DoubleFFT_1D fft = new DoubleFFT_1D(fourierDataSize/2);

                fft.complexForward(fourierData);

                double max_fftval = -1;
                int max_i = -1;

                for (int i = 0; i < fourierData.length; i += 2) // we are only looking at the half of the spectrum
                {
                    //double hz = ((i / 2.0) / fourierChunk.length) * chunkSize;

                    // complex numbers -> vectors, so we compute the length of the vector, which is sqrt(realpart^2+imaginarypart^2)
                    double vlen = Math.sqrt(fourierData[i] * fourierData[i] + fourierData[i + 1] * fourierData[i + 1]);

                    if (max_fftval < vlen) {
                        // if this length is bigger than our stored biggest length
                        max_fftval = vlen;
                        max_i = i;
                    }
                }

                double domFrequency = max_i ;//* (sampleRate / 2.0) / chunkSize;

                int midiVal = 0;

                if (domFrequency < 27.5 || domFrequency > 4186) {
                    midiVal = 0; //midiData[k]
                } else {
                    double log2 = Math.log10(domFrequency / 440) / Math.log10(2);
                    midiVal = (int) (12 * log2) + 69; //midiData[k]
                }

                int n = k ;
                while (n >= 0) {

                    if (midiData[n] != 0) {
                        Log.d("MIDI VALUE", "Midi valut at " + n + " :" + midiData[n]);
                        midiValues.addMidiNum(midiData[n]);
                        break;
                    }
                    n--;
                }

                midiValues.addMidiNum(midiVal);

                k++;

            }

         /*  midiData = null;
           freqData = null;
           rawData = null;
         //  System.gc();*/

            midiValues.generateNoteMap();
            return midiValues;
        }
        catch (Exception e)
        {
            Log.e("Exception", e.getMessage());
        }

        return null;
    }

    public int convertSampleToMidi()
    {
        try {

            int frequency = 3560; // freq of our sine wave
            double lengthInSecs = 1;
            int SAMPLERATE = 8000;
            int samplesNum = (int) Math.round(lengthInSecs * SAMPLERATE);

            System.out.println("Samplesnum: " + samplesNum);

            double[] audioData = new double[samplesNum];
            int samplePos = 0;

            // http://en.wikibooks.org/wiki/Sound_Synthesis_Theory/Oscillators_and_Wavetables
            for (double phase = 0; samplePos < lengthInSecs * SAMPLERATE && samplePos < samplesNum; phase += (2 * Math.PI * frequency) / SAMPLERATE) {
                audioData[samplePos++] = Math.sin(phase)*10;

                if (phase >= 2 * Math.PI)
                    phase -= 2 * Math.PI;
            }

            // we compute the fft of the whole sine wave
            DoubleFFT_1D fft = new DoubleFFT_1D(samplesNum);

            // we need to initialize a buffer where we store our samples as complex numbers. first value is the real part, second is the imaginary.
            double[] fftData = new double[samplesNum * 2];
            for (int i = 0; i < samplesNum; i++) {
                // copying audio data to the fft data buffer, imaginary part is 0
                fftData[2 * i] = audioData[i];
                fftData[2 * i + 1] = 0;
            }

            // calculating the fft of the data, so we will have spectral power of each frequency component
            // fft resolution (number of bins) is samplesNum, because we initialized with that value
            fft.complexForward(fftData);

            // writing the values to a txt file

            int max_i = -1;
            double max_fftval = -1;
            for (int i = 0; i < fftData.length; i += 2) { // we are only looking at the half of the spectrum
                double hz = ((i / 2.0) / fftData.length) * SAMPLERATE;


                // complex numbers -> vectors, so we compute the length of the vector, which is sqrt(realpart^2+imaginarypart^2)
                double vlen = Math.sqrt(fftData[i] * fftData[i] + fftData[i + 1] * fftData[i + 1]);

                if (max_fftval < vlen) {
                    // if this length is bigger than our stored biggest length
                    max_fftval = vlen;
                    max_i = i;
                }
            }

            double dominantFreq = ((max_i/2.0) / fftData.length) * SAMPLERATE*2;
            double log2 = Math.log10(dominantFreq/440)/Math.log10(2);
           int midi = (int)(12 * log2) + 69;


            System.out.println("Dominant frequency: " + dominantFreq + "hz (output.txt line no. " + max_i + ")");
            return midi;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int byteToInt(byte[] data, int startIndex)
    {
        return (data[startIndex+3]<<24)&0xff000000|
                (data[startIndex+2]<<16)&0x00ff0000|
                (data[startIndex+1]<< 8)&0x0000ff00|
                (data[startIndex]<< 0)&0x000000ff;
    }

    public short byteToShort(byte[] data, int startIndex)
    {
        short ret = 0;
        ret =(short)((data[startIndex+1]<< 8)&0xff00|
                (data[startIndex]<< 0)&0x00ff);
       return ret;
    }

}
