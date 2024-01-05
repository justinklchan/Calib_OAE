package com.example.calib_oae;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.HashMap;

public class Constants {

    public static OfflineRecorder _OfflineRecorder;
    public static double GapDuration=.15;
    public static double ChirpDuration=.25;
    public static double MaxConstantChirps=5;
    public static int SlackTime=1;
    public static int SamplingRate=48000;
    public static AudioSpeaker sp1;
    public static EditText et1,et2,et3,et5,et6;
    public static Spinner spinner;
    public static int samplingRate = 48000;
    public static HashMap<Integer,Integer> freqLookup;

    public static float scale1=1.0f,scale2=1.0f,volume=1.0f;
    public static int fstart=50,fend=20000,init_sleep=5,rec_len=60;
    public static int file_num=1;

    public static boolean transmit=true;
    public static boolean chirp=true;

    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Constants.scale1=prefs.getFloat("scale1",Constants.scale1);
        Constants.scale2=prefs.getFloat("scale2",Constants.scale2);
        Constants.volume=prefs.getFloat("volume",Constants.volume);
        Constants.rec_len=prefs.getInt("rec_len",Constants.rec_len);
        Constants.init_sleep=prefs.getInt("init_sleep",Constants.init_sleep);
        Constants.transmit=prefs.getBoolean("transmit",Constants.transmit);

        Constants.et1.setText(scale1+"");
        Constants.et2.setText(scale2+"");
        Constants.et3.setText(volume+"");
        Constants.et5.setText(rec_len+"");
        Constants.et6.setText(init_sleep+"");
    }
}
