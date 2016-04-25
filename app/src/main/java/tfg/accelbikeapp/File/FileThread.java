package tfg.accelbikeapp.File;

import android.content.Context;
import android.os.Looper;

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
    private ArrayList<Short> VectorEjes;
    private ArrayList<Double> vectorCoord;
    private Date fecha;
    private Context ctx;

    public FileThread(Context context){
        super();
        this.ctx = context;
        bluThread = new BluetoothThread();
        locThread = new CoordenadasThread(this.ctx);
        filemanager = new FileManager(this.ctx);
    }

    public void run(){

        bluThread.start();
        locThread.start();

        fecha = new Date();

        //CoordenadasThread coordenadas = new CoordenadasThread(ctx);

        filemanager.updateSesion("sesionAlexis");

        while (!Thread.interrupted()) {

            VectorEjes = bluThread.getEjes();
            vectorCoord = locThread.getCoordenadas();

            if (!VectorEjes.isEmpty() && !vectorCoord.isEmpty()){
                /*Log.i("X", Short.toString(VectorEjes.get(0)));
                Log.i("Y", Short.toString(VectorEjes.get(1)));
                Log.i("Z", Short.toString(VectorEjes.get(2)));*/


                filemanager.guardar(Short.toString(VectorEjes.get(0)), Short.toString(VectorEjes.get(1)),
                        Short.toString(VectorEjes.get(2)),
                        Double.toString(vectorCoord.get(0)),
                        Double.toString(vectorCoord.get(1)));

            }

            try {

                Thread.sleep(1000);

            }
            catch (InterruptedException e) {

                bluThread.interrupt();
                locThread.interrupt();
                return;

            }
        }
    }
}
