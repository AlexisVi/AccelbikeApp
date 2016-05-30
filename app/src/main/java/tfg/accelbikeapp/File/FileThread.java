package tfg.accelbikeapp.File;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;

import tfg.accelbikeapp.Bluetooth.BluetoothThread;
import tfg.accelbikeapp.GPS.CoordenadasThread;

/**
 * Created by alexis on 22/04/16.
 */

public class FileThread extends Thread {

    private FileManager filemanager;
    private BluetoothThread bluThread;
    private CoordenadasThread locThread;

    private static final int TIME = 2000;

    private String fileName;

    public FileThread(Context context, String name){

        super(name);
        bluThread = new BluetoothThread();
        locThread = new CoordenadasThread(context);
        filemanager = new FileManager(context);
        fileName = name;

    }

    public void run(){

        Log.i("FileThread", "Empezando con PID: " + getId());

        bluThread.start();
        locThread.start();

        filemanager.updateSesion(fileName);

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
