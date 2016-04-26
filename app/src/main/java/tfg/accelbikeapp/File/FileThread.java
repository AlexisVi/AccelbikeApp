package tfg.accelbikeapp.File;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import tfg.accelbikeapp.Bluetooth.BluetoothThread;
import tfg.accelbikeapp.GPS.CoordenadasThread;

/**
 * Created by alexis on 22/04/16.
 */

public class FileThread extends Thread {

    private FileManager filemanager;
    private BluetoothThread bluThread;
    private CoordenadasThread locThread;

    private Date fecha;

    private static final int TIME = 5000;

    public FileThread(Context context){

        super();
        bluThread = new BluetoothThread();
        locThread = new CoordenadasThread(context);
        filemanager = new FileManager(context);

    }

    public void run(){

        bluThread.start();
        locThread.start();

        fecha = new Date();

        filemanager.updateSesion("sesionAlexis");

        while (!Thread.interrupted()) {

            ArrayList<Short> VectorEjes = bluThread.getEjes();
            ArrayList<Double> vectorCoord = locThread.getCoordenadas();

            if (!VectorEjes.isEmpty() && !vectorCoord.isEmpty()){

                filemanager.guardar(Short.toString(VectorEjes.get(0)), Short.toString(VectorEjes.get(1)),
                        Short.toString(VectorEjes.get(2)),
                        Double.toString(vectorCoord.get(0)),
                        Double.toString(vectorCoord.get(1)));

            }

            try {

                Thread.sleep(TIME);

            }
            catch (InterruptedException e) {

                bluThread.interrupt();
                locThread.interrupt();
                break;

            }
        }

        filemanager.leer();

        return;

    }
}
