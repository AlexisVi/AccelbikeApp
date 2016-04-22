package tfg.accelbikeapp.File;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;

import tfg.accelbikeapp.Bluetooth.BluetoothThread;

/**
 * Created by alexis on 22/04/16.
 */
public class FileThread extends Thread {

    private FileManager filemanager;
    private BluetoothThread bluetooth;
    private ArrayList<Short> ejes;
    private Date fecha;

    public FileThread(Context context){
        super();
        bluetooth = new BluetoothThread();
        filemanager = new FileManager(context);
    }

    public void run(){
        bluetooth.start();

        fecha = new Date();

        filemanager.updateSesion("sesionAlexis");

        while (!Thread.interrupted()) {

            ejes = bluetooth.getEjes();
            if(!ejes.isEmpty()){
                /*Log.i("X", Short.toString(ejes.get(0)));
                Log.i("Y", Short.toString(ejes.get(1)));
                Log.i("Z", Short.toString(ejes.get(2)));*/
                filemanager.guardar(Short.toString(ejes.get(0)), Short.toString(ejes.get(1)), Short.toString(ejes.get(2)));
            }

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                bluetooth.interrupt();
            }
        }

    }
}
