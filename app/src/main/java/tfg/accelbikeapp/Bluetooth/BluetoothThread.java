package tfg.accelbikeapp.Bluetooth;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexis on 22/04/16.
 */
public class BluetoothThread extends Thread implements GattObserver  {

    private volatile ArrayList<Short> ejes;

    public BluetoothThread(){
        super();
        ejes = new ArrayList<Short>();
        BLEGatt.getInstancia().registerObserver(this);
    }

    public ArrayList<Short> getEjes(){
        return ejes;
    }

    public void run(){
        while (!Thread.interrupted()) {

            BLEGatt.getInstancia().leer();

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                return;

            }
        }
    }

    @Override
    public void onDataRead(final List<Short> valores) {

        ejes.add(0,valores.get(0));
        ejes.add(1,valores.get(1));
        ejes.add(2,valores.get(2));

        Log.i("X", Short.toString(ejes.get(0)));
        Log.i("Y", Short.toString(ejes.get(1)));
        Log.i("Z", Short.toString(ejes.get(2)));

    }
}
