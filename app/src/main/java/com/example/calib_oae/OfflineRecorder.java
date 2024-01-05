package com.example.calib_oae;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AutomaticGainControl;
import android.util.Log;

public class OfflineRecorder extends Thread {
    public boolean recording;
    int samplingfrequency;
    public short[] samples;
    public short[] temp;
    int count;
    AudioRecord rec;
    int minbuffersize;
    Activity av;
    String filename;

    public OfflineRecorder(Activity av, int samplingfrequency, int arrLength, String filename) {
        this.filename = filename;
        this.av = av;
        count = 0;
        this.samplingfrequency = samplingfrequency;

        int channels=AudioFormat.CHANNEL_IN_MONO;

        minbuffersize = AudioRecord.getMinBufferSize(
                samplingfrequency,
                channels,
                AudioFormat.ENCODING_PCM_16BIT);

        rec = new AudioRecord(
                MediaRecorder.AudioSource.DEFAULT,
                samplingfrequency, channels,
                AudioFormat.ENCODING_PCM_16BIT,
                minbuffersize);

        temp = new short[minbuffersize];
//        Utils.log("offlinerecorder length " + arrLength);
        samples = new short[arrLength];

        boolean AGC=true;
        if (AGC) {
            if (AutomaticGainControl.isAvailable()) {
                AutomaticGainControl agc = AutomaticGainControl.create(
                        rec.getAudioSessionId()
                );
                agc.setEnabled(true);
            }
        }
        else {
            if (AutomaticGainControl.isAvailable()) {
                AutomaticGainControl agc = AutomaticGainControl.create(
                        rec.getAudioSessionId()
                );
                agc.setEnabled(false);
            }
        }
    }

    public void halt() {
//        Utils.log("halt");
        if (this.rec.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            this.recording = false;
            this.rec.stop();
            this.rec.release();
            Log.e("asdf","halt");
            FileOperations.writetofile(av,samples,filename);
        }
    }

    public void run() {
        count = 0;
        int bytesread;
        rec.startRecording();
        recording = true;
        while(recording) {
            bytesread = rec.read(temp,0,minbuffersize);
            for(int i=0;i<bytesread;i++) {
                if (count >= samples.length && rec.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    rec.stop();
                    rec.release();
                    recording = false;
                    Log.e("asdf","done");
                    FileOperations.writetofile(av, samples,filename);
//                    Utils.log("finished recording");
                    break;
                } else if (count < samples.length) {
                    samples[count]=temp[i];
                    count++;
                } else {
                    break;
                }
            }
        }
//        Utils.log("end thread");
    }
}
