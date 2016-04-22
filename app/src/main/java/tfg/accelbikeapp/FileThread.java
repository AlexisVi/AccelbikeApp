package tfg.accelbikeapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rodry on 22/04/16.
 */
public class FileThread extends Thread {

    Context context;

    public FileThread(Context context){

        super();
        this.context = context;

    }

    public void run() {

        BluetoothThread th = new BluetoothThread();
        th.start();

        String fullpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rodry/";

        // Comprobar si existe
        File dir = new File(fullpath);
        if (!dir.exists())
            dir.mkdirs();

        while (!Thread.interrupted()){

            ArrayList<Short> ejes = th.ejes;

            if (!ejes.isEmpty()) {

                try {

                    File f = new File (fullpath, "holaAlexis");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

                    bw.write(Short.toString(ejes.get(0)) + ";" +
                            Short.toString(ejes.get(1)) + ";" +
                            Short.toString(ejes.get(2)));

                    bw.newLine();
                    bw.flush();

                    bw.close();

                }
                catch (IOException e){


                }

            }
            try{

                Thread.sleep(5000);

            }
            catch (InterruptedException e){

                Log.i("FileThread", "interrumpido");

                th.interrupt();

            }
        }
    }
}
