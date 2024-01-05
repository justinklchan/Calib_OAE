package com.example.calib_oae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    Button startb,stopb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.textView1);
        startb=findViewById(R.id.button);
        stopb=findViewById(R.id.button2);
        Constants.et1=findViewById(R.id.editTextNumber);
        Constants.et2=findViewById(R.id.editTextNumber2);
        Constants.et3=findViewById(R.id.editTextNumber3);
        Constants.et4=findViewById(R.id.editTextNumber4);
        Constants.et5=findViewById(R.id.editTextNumber5);
        Constants.et6=findViewById(R.id.editTextNumber6);
        Constants.et7=findViewById(R.id.editTextNumber7);
        Constants.et8=findViewById(R.id.editTextNumber8);
        Constants.et9=findViewById(R.id.editTextNumber9);
        Constants.sw1=findViewById(R.id.switch1);
        Constants.sw2=findViewById(R.id.switch2);
        String[] perms = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

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
                if (ss.length()>0) {
                    editor.putInt("fstart", Integer.parseInt(ss));
                    editor.commit();
                    Constants.fstart = Integer.parseInt(ss);
                }
            }
        });

        Constants.et4.addTextChangedListener(new TextWatcher() {
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
                String ss = Constants.et4.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("fend", Integer.parseInt(ss));
                    editor.commit();
                    Constants.fend = Integer.parseInt(ss);
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

        Constants.et7.addTextChangedListener(new TextWatcher() {
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
                String ss = Constants.et7.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("chirp_len", Float.parseFloat(ss));
                    editor.commit();
                    Constants.chirp_len = Float.parseFloat(ss);
                }
            }
        });

        Constants.et8.addTextChangedListener(new TextWatcher() {
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
                String ss = Constants.et8.getText().toString();
                boolean store=true;
                try {
                    float ff=Float.parseFloat(ss);
                }
                catch(Exception e) {
                    store=false;
                }
                if (store) {
                    editor.putFloat("gap_len", Float.parseFloat(ss));
                    editor.commit();
                    Constants.gap_len = Float.parseFloat(ss);
                }
            }
        });
        Constants.et9.addTextChangedListener(new TextWatcher() {
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
                String ss = Constants.et9.getText().toString();
                if (ss.length()>0) {
                    editor.putInt("file_num", Integer.parseInt(ss));
                    editor.commit();
                    Constants.init_sleep = Integer.parseInt(ss);
                }
            }
        });

        Constants.sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("transmit", isChecked);
                editor.commit();
                Constants.transmit  = isChecked;
            }
        });
        Constants.sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(av).edit();
                editor.putBoolean("chirp", isChecked);
                editor.commit();
                Constants.chirp  = isChecked;
                Constants.et3.setEnabled(isChecked);
                Constants.et4.setEnabled(isChecked);
                Constants.et5.setEnabled(isChecked);
                Constants.et7.setEnabled(isChecked);
                Constants.et8.setEnabled(isChecked);
                Constants.et9.setEnabled(!isChecked);
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
    public void onstart(View v) {
        Log.e("asdf","onstart");
        String ts = System.currentTimeMillis() + "";
        String trim=ts.substring(ts.length()-4,ts.length());
        tv1.setText(trim);

        if (Constants.chirp) {
            task = new SendChirpAsyncTask(this, ts + ".txt", startb, stopb, null);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            short[] vals=null;
            if (Constants.file_num == 1) {
                vals=FileOperations.readrawasset(this, R.raw.oceansig1);
            }
            else if (Constants.file_num == 2) {
                vals=FileOperations.readrawasset(this, R.raw.oceansig2);
            }
            else if (Constants.file_num == 3) {
                vals=FileOperations.readrawasset(this, R.raw.oceansig3);
            }
            else if (Constants.file_num == 4) {
                vals=FileOperations.readrawasset(this, R.raw.oceansig4);
            }
            Log.e("asdf","read file "+Constants.file_num+" : "+(vals.length/Constants.SamplingRate));
            vals=scale(vals,Constants.scale1);

            task = new SendChirpAsyncTask(this, ts + ".txt", startb, stopb, vals);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
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