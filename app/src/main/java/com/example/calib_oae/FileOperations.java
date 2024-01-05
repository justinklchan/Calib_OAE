package com.example.calib_oae;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class FileOperations {

    public static short[] readrawasset(Context context, int id) {
        Scanner inp = new Scanner(context.getResources().openRawResource(id));
        LinkedList<Short> ll = new LinkedList<Short>();
        while (inp.hasNextLine()) {
            ll.add((short)Float.parseFloat(inp.nextLine()));
        }
        inp.close();
        short[] ar = new short[ll.size()];
        int counter = 0;
        for (Short d : ll) {
            ar[counter++] = d;
        }
        ll.clear();
        return ar;
    }

    public static void writetofile(Activity av, short[] buff, String filename) {
        Log.e("asdf","writetofile");
        try {
            String dir = av.getExternalFilesDir(null).toString();
            writetofile(dir, buff, filename);

        } catch(Exception e) {
            Log.e("asdf",e.toString());
        }
    }

    public static void writetofile(String _ExternalFilesDir, short[] buff, String filename) {
        Log.e("asdf","writetofile2");
        try {
            String dir = _ExternalFilesDir;
            File path = new File(dir);
            if (!path.exists()) {
                path.mkdirs();
            }

            File file = new File(dir, filename);

            BufferedWriter buf = new BufferedWriter(new FileWriter(file,false));
            for (int i = 0; i < buff.length; i++) {
                buf.append(""+buff[i]);
                buf.newLine();
            }
            buf.flush();
            buf.close();
        } catch(Exception e) {
            Log.e("asdf",e.toString());
        }
    }
}
