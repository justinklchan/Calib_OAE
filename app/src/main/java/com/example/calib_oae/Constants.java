package com.example.calib_oae;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Switch;

public class Constants {

    public static OfflineRecorder _OfflineRecorder;
    public static double GapDuration=.15;
    public static double ChirpDuration=.25;
    public static double MaxConstantChirps=5;
    public static int SlackTime=1;
    public static int SamplingRate=48000;
    public static AudioSpeaker sp1;
    public static EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;
    public static Switch sw1,sw2;

    public static float scale1=1.0f,scale2=1.0f,chirp_len=0.02f,gap_len=0.015f;
    public static int fstart=50,fend=20000,init_sleep=5,rec_len=60;
    public static int file_num=1;

    public static boolean transmit=true;
    public static boolean chirp=true;

    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Constants.scale1=prefs.getFloat("scale1",Constants.scale1);
        Constants.scale2=prefs.getFloat("scale2",Constants.scale2);
        Constants.fstart=prefs.getInt("fstart",Constants.fstart);
        Constants.fend=prefs.getInt("fend",Constants.fend);
        Constants.rec_len=prefs.getInt("rec_len",Constants.rec_len);
        Constants.init_sleep=prefs.getInt("init_sleep",Constants.init_sleep);
        Constants.chirp_len=prefs.getFloat("chirp_len",Constants.chirp_len);
        Constants.gap_len=prefs.getFloat("gap_len",Constants.gap_len);
        Constants.transmit=prefs.getBoolean("transmit",Constants.transmit);
        Constants.chirp=prefs.getBoolean("chirp",Constants.chirp);
        Constants.file_num=prefs.getInt("file_num",Constants.file_num);

        Constants.et1.setText(scale1+"");
        Constants.et2.setText(scale2+"");
        Constants.et3.setText(fstart+"");
        Constants.et4.setText(fend+"");
        Constants.et5.setText(rec_len+"");
        Constants.et6.setText(init_sleep+"");
        Constants.et7.setText(chirp_len+"");
        Constants.et8.setText(gap_len+"");
        Constants.et9.setText(file_num+"");
        Constants.sw1.setChecked(transmit);
        Constants.sw2.setChecked(chirp);

        Constants.et3.setEnabled(Constants.chirp);
        Constants.et4.setEnabled(Constants.chirp);
        Constants.et5.setEnabled(Constants.chirp);
        Constants.et7.setEnabled(Constants.chirp);
        Constants.et8.setEnabled(Constants.chirp);
        Constants.et9.setEnabled(!Constants.chirp);
    }
}
