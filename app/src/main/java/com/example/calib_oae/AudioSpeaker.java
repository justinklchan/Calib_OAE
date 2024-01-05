package com.example.calib_oae;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioSpeaker extends Thread {

    AudioTrack track1;
    Context mycontext;
    short[] samples;
    int speakerType;
    AudioManager man;

    int[] streams = new int[]{AudioManager.STREAM_MUSIC,
            AudioManager.STREAM_ACCESSIBILITY, AudioManager.STREAM_ALARM,
            AudioManager.STREAM_DTMF, AudioManager.STREAM_NOTIFICATION,
            AudioManager.STREAM_RING, AudioManager.STREAM_SYSTEM,
            AudioManager.STREAM_VOICE_CALL};

    /**
     * Setup an Android.Media.AudioTrack() with data and parameters.
     * This AudioTrack() object is later played out of the speaker.
     * @param mycontext The caller's context.
     * @param samples The data that will be played by the speaker.
     * @param samplingFreq The initial source sample rate expressed in Hz.
     * @TODO upgrade deprecated calls.
     */
    public AudioSpeaker(Context mycontext, short[] samples, int samplingFreq) {
        this.speakerType = AudioManager.STREAM_SYSTEM; // streamType – the type of the audio stream.
        this.mycontext = mycontext;
        man = (AudioManager)mycontext.getSystemService(Context.AUDIO_SERVICE);
        for (Integer i : streams) {
            man.setStreamMute(i, true);
        }
        man.setStreamMute(speakerType, false);
        man.setStreamVolume(speakerType,(int)(man.getStreamMaxVolume(speakerType)),0);

        this.samples = samples;
        track1 = new AudioTrack(speakerType,
                samplingFreq,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, // PCM 16 bit per sample. Guaranteed to be supported by devices.
                samples.length * 2,  // Each sample is 2 8-bit bytes, so the buffer length is samples * 2.
                AudioTrack.MODE_STATIC // mode – streaming or static buffer.
        );
        track1.write(samples,0,samples.length);
    }

    public void play(double vol) {

//        Utils.log("audiospeaker PLAY");
        try {
//            setVolume(1);
            track1.setVolume((float)vol);
//            track1.setStereoVolume((float) .01, (float) .01);
            track1.play();
        }catch(Exception e) {
//            Utils.log(e.getMessage());
        }
    }

    public void reset() {
        track1.stop();
        track1.reloadStaticData();
    }

    public void run() {

//        Utils.log("audiospeaker RUN");
        track1.setLoopPoints(0, samples.length, 1);
        track1.play();

    }

    public void pause() {
        track1.pause();
    }
}
