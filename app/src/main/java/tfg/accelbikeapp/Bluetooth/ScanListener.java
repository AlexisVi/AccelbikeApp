package tfg.accelbikeapp.Bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by rodry on 4/05/16.
 */
public interface ScanListener {

    void onScanResult(BluetoothDevice device);

}
