package com.example.calib_oae;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

public class SendChirpAsyncTask extends AsyncTask<Void, Void, Void> {
    Activity av;
    String ts;
    Button startb,stopb;
    short[] pulse;

    public SendChirpAsyncTask(Activity activity, String ts, Button startb, Button stopb, short[] pulse) {
        this.av = activity;
        this.ts = ts;
        this.startb=startb;
        this.stopb=stopb;
        this.pulse = pulse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        startb.setEnabled(false);
        stopb.setEnabled(true);
    }

    public static short[] continuouspulse(double length, double startfreq, double endfreq, int Sampling_Rate, double gap, int chirps_required, double scale) {
        Log.e("logme", length + "," + gap + "," + chirps_required + "," + Sampling_Rate);
        Log.e("logme", (int) ((length + gap) * chirps_required * Sampling_Rate) + "");

        if (length > 1) {
            length = length / 1000;
        }
        if (gap > 1) {
            gap = gap / 1000;
        }

        short[] signal = new short[(int) ((length + gap) * chirps_required * Sampling_Rate)];
        try {
            short[] signal1;
//            if (length == 1)  {
//                signal1 = new short[1];
//                signal1[0] = (short)(32767 * scale);
//            }
            int index = 0;
            for (int i = 0; i < chirps_required; i++) {
                signal1 = Chirp.generateChirpSpeaker(startfreq, endfreq, length, Sampling_Rate, 0);
                for (int j = 0; j < signal1.length; j++) {
                    signal[index++] = signal1[j];
                }
                for (int j = 0; j < gap * Sampling_Rate; j++) {
                    signal[index++] = 0;
                }
            }
        }
        catch (Exception e) {
//            Utils.log(e.getMessage());
        }

        return signal;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.e("asdf","postexec");
        startb.setEnabled(true);
        stopb.setEnabled(false);

//        if (Constants._OfflineRecorder != null) {
//            Constants._OfflineRecorder.halt();
//            FileOperations.writetofile(av, Constants._OfflineRecorder.samples, ts); // Write the microphone recording to a file.
//        }

        Constants.sp1=null;
        Constants._OfflineRecorder = null;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("asdf","bg");

        if (Constants.chirp) {
            Constants._OfflineRecorder = new OfflineRecorder(av, Constants.SamplingRate,
                    Constants.rec_len * Constants.SamplingRate, ts);
            Constants._OfflineRecorder.start();

            int chirps_required = (int) (Constants.rec_len / (Constants.chirp_len + Constants.gap_len));

            pulse = continuouspulse(
                    Constants.chirp_len, Constants.fstart, Constants.fend,
                    48000, Constants.gap_len, chirps_required,
                    Constants.scale1);
            FileOperations.writetofile(av, pulse, "input-" + ts);
        }
        else {
            Constants._OfflineRecorder = new OfflineRecorder(av, Constants.SamplingRate,
                    pulse.length, ts);
            Constants._OfflineRecorder.start();
        }

        try {
            Thread.sleep((long)(Constants.init_sleep*1000));
        }
        catch(Exception e){
            Log.e("asdf",e.getMessage());
        }

        Constants.sp1 = new AudioSpeaker(av, pulse, 48000);

        Log.e("asdf","play sound");
        if (Constants.transmit) {
            Constants.sp1.play(Constants.scale2);
        }

        try {
            if (Constants.chirp) {
                Thread.sleep((long) (Constants.rec_len * 1000));
            }
            else {
                Log.e("asdf","sleep for "+(pulse.length/Constants.SamplingRate));
                Thread.sleep((pulse.length/Constants.SamplingRate)*1000);
            }
        }
        catch(Exception e){
            Log.e("asdf",e.getMessage());
        }

        return null;
    }
}
