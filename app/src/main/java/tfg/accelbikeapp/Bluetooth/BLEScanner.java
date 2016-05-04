package tfg.accelbikeapp.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodry on 30/03/16.
 */
public class BLEScanner {

    private BluetoothLeScanner scanner;
    private ArrayList<ScanListener> listeners;

    private ScanCallback mScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            //Log.i("callbackType", String.valueOf(callbackType));
            Log.i("result", result.toString());

            // Mostrar el dispositivo en la lista
            for (ScanListener listener: listeners) {

                listener.onScanResult(result.getDevice());

            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {

            for (ScanResult sr : results) {

                Log.i("ScanResult - Results", sr.toString());

                // Mostrar el dispositivo en la lista
                for (ScanListener listener: listeners) {
                    for (ScanResult sc : results) {

                        listener.onScanResult(sc.getDevice());

                    }
                }
            }
        }

        @Override
        public void onScanFailed(int errorCode) {

            String mensaje = "Fallo en el escaneo";
            // mostrarToast(mensaje);
            Log.e("Scan Failed", "Error code: " + errorCode);
        }
    };


    public BLEScanner(){

        listeners = new ArrayList<>();

    }

    public void registerScanListener(ScanListener listener){

        listeners.add(listener);

    }

    public void listarDispositivos(BluetoothAdapter adapter){

        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        List<ScanFilter> filtros = new ArrayList<>();

        scanner = adapter.getBluetoothLeScanner();

        scanner.startScan(filtros, settings, mScanCallback);

    }

    public void pararEscaneo(){

        scanner.stopScan(mScanCallback);
        Log.i("listarDispositivos", "Escaneo parado");

    }

    public void destroy(){

        if (scanner != null)
            scanner.stopScan(mScanCallback);
        scanner = null;

    }
}
