package tfg.accelbikeapp.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by rodry on 30/03/16.
 */
public class BluetoothManager {

    private Context context;
    private BluetoothAdapter bAdapter;
    private BLEScanner mScanner;

    public BluetoothManager(Context context){

        this.context = context;

        mScanner = new BLEScanner();
        final android.bluetooth.BluetoothManager bluetoothManager =
                (android.bluetooth.BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        bAdapter = bluetoothManager.getAdapter();

    }

    public void registerScannerListener(ScanListener listener){

        mScanner.registerScanListener(listener);

    }

    public boolean bluetoothDisponible(){

        return bAdapter != null;

    }

    public boolean bluetoothActivado(){

        return bAdapter.isEnabled();

    }

    public void listarDispositivos(){

        mScanner.listarDispositivos(bAdapter);

    }

    public void pararEscaneo(){

        mScanner.pararEscaneo();

    }

    public void conectarDispositivo(BluetoothDevice bd){

        BLEGatt.getInstancia().disconnect();
        BLEGatt.getInstancia().connectToDevice(context, bd);

    }

    public void destroy(){

        mScanner.destroy();
        mScanner = null;
        bAdapter = null;

    }
}
