package tfg.accelbikeapp.File;

import android.content.Context;
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
    private BluetoothThread bluetooth;
    private CoordenadasThread coordenadas;
    private ArrayList<Short> ejes;
    private ArrayList<Double> coordenadasAux;
    private Date fecha;
    private Context ctx;

    public FileThread(Context context){
        super();
        this.ctx = context;
        bluetooth = new BluetoothThread();
        coordenadas = new CoordenadasThread(this.ctx);
        filemanager = new FileManager(this.ctx);
    }

    public void run(){
        bluetooth.start();
        coordenadas.start();

        fecha = new Date();

        //CoordenadasThread coordenadas = new CoordenadasThread(ctx);

        filemanager.updateSesion("sesionAlexis");

        while (!Thread.interrupted()) {

            ejes = bluetooth.getEjes();
            coordenadasAux = coordenadas.getCoordenadas();
            if(!ejes.isEmpty() && !coordenadasAux.isEmpty()){
                /*Log.i("X", Short.toString(ejes.get(0)));
                Log.i("Y", Short.toString(ejes.get(1)));
                Log.i("Z", Short.toString(ejes.get(2)));*/


                filemanager.guardar(Short.toString(ejes.get(0)), Short.toString(ejes.get(1)),
                        Short.toString(ejes.get(2)),
                        Double.toString(coordenadasAux.get(0)),
                        Double.toString(coordenadasAux.get(1)));

                //Log.i("Coordenadas", "Hola");

                //Log.i("Coordenadas", "ME LA SUDAN");
            }

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                bluetooth.interrupt();
                coordenadas.interrupt();
            }
        }
    }
}
