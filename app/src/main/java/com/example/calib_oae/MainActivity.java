package com.example.calib_oae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    Button startb,stopb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startb=findViewById(R.id.button);
        stopb=findViewById(R.id.button2);
        Constants.freqLookup = new HashMap<>();
        Constants.freqLookup.put(200,200);
        Constants.freqLookup.put(2016,1640);
        Constants.freqLookup.put(2953,2438);
        Constants.freqLookup.put(3985,3282);
        Constants.freqLookup.put(4969,4078);
        Constants.et1=findViewById(R.id.editTextNumber);
        Constants.et2=findViewById(R.id.editTextNumber2);
        Constants.et3=findViewById(R.id.editTextNumber3);
        Constants.et5=findViewById(R.id.editTextNumber5);
        Constants.et6=findViewById(R.id.editTextNumber6);
        Constants.spinner = findViewById(R.id.spinner1);
        String[] perms = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        // you need to have a list of data that you want the spinner to display
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("200");
        spinnerArray.add("2016");
        spinnerArray.add("2953");
        spinnerArray.add("3985");
        spinnerArray.add("4969");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Constants.spinner.setAdapter(adapter);

        ActivityCompat.requestPermissions(this,
                perms,
                1234);
        Activity av = this;
        Constants.init(this);
        Constants.et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et1.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("scale1", Float.parseFloat(ss));
                    editor.commit();
                    Constants.scale1 = Float.parseFloat(ss);
                }
            }
        });

        Constants.et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et2.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("scale2", Float.parseFloat(ss));
                    editor.commit();
                    Constants.scale2 = Float.parseFloat(ss);
                }
            }
        });

        Constants.et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et3.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("volume", Float.parseFloat(ss));
                    editor.commit();
                    Constants.volume = Float.parseFloat(ss);
                }
            }
        });

        Constants.et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et5.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("rec_len", Integer.parseInt(ss));
                    editor.commit();
                    Constants.rec_len = Integer.parseInt(ss);
                }
            }
        });

        Constants.et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                String ss = Constants.et6.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("init_sleep", Integer.parseInt(ss));
                    editor.commit();
                    Constants.init_sleep = Integer.parseInt(ss);
                    Log.e("asdf","commit "+Integer.parseInt(ss));
                }
            }
        });
    }

    public static short[] scale(short[] vals, float scale) {
        for (int i = 0; i < vals.length; i++) {
            vals[i] *= scale;
        }
        return vals;
    }

    SendChirpAsyncTask task;

    public void sendTone(int freq) {
        int f1 = Constants.freqLookup.get(freq);
        int f2 = freq;

        short[] pulse;

        float vol3a = Constants.scale1;
        float vol3b = Constants.scale2;

        pulse = SignalGenerator.sine2speaker(f1, f2,
                Constants.samplingRate,
                Constants.rec_len*Constants.samplingRate,
                vol3a,
                vol3b);

        Log.e("out","speaker "+f1+","+f2);

        float vol1 = Constants.volume;

        AudioStreamer sp = new AudioStreamer(this, pulse, Constants.samplingRate*Constants.rec_len*2,
                Constants.samplingRate, AudioManager.STREAM_SYSTEM,vol1,false);

        int micType;
//        if (Constants.AGC) {
//            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//            if (audioManager.getProperty(AudioManager.PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED) != null) {
//                micType = (MediaRecorder.AudioSource.UNPROCESSED);
//            } else {
//                micType = (MediaRecorder.AudioSource.VOICE_RECOGNITION);
//            }
//        }
//        else {
            micType = MediaRecorder.AudioSource.DEFAULT;
//        }

        try {
            sp.play(-1);
            Thread.sleep((long) (Constants.rec_len*1000));

            Log.e("asdf","stop it");
            sp.stopit();

//            while (orec.recording||
//                    orec.rec.getState()!= AudioRecord.RECORDSTATE_STOPPED||
//                    sp.track1.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
//                Thread.sleep(100);
//            }

            Log.e("asdf","STOP");

        } catch (Exception e) {
            Log.e("ex","sendtone");
            Log.e("ex",e.getMessage());
        }
    }

    public void onstart(View v) {
        sendTone(Integer.parseInt(Constants.spinner.getSelectedItem().toString()));

//        if (Constants.chirp) {
//            task = new SendChirpAsyncTask(this, ts + ".txt", startb, stopb, null);
//            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        }
//        else {
//            short[] vals=null;
//            if (Constants.file_num == 1) {
//                vals=FileOperations.readrawasset(this, R.raw.oceansig1);
//            }
//            else if (Constants.file_num == 2) {
//                vals=FileOperations.readrawasset(this, R.raw.oceansig2);
//            }
//            else if (Constants.file_num == 3) {
//                vals=FileOperations.readrawasset(this, R.raw.oceansig3);
//            }
//            else if (Constants.file_num == 4) {
//                vals=FileOperations.readrawasset(this, R.raw.oceansig4);
//            }
//            Log.e("asdf","read file "+Constants.file_num+" : "+(vals.length/Constants.SamplingRate));
//            vals=scale(vals,Constants.scale1);
//
//            task = new SendChirpAsyncTask(this, ts + ".txt", startb, stopb, vals);
//            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//        }
    }

    public void onstop(View v) {
        Log.e("asdf","onstop");
        task.cancel(true);
        if (Constants._OfflineRecorder!=null) {

            Constants._OfflineRecorder.halt();
        }
        if (Constants.sp1!=null) {
            Constants.sp1.pause();
        }
        startb.setEnabled(true);
        stopb.setEnabled(false);
    }
}