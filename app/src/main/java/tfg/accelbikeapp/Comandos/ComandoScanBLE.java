package tfg.accelbikeapp.Comandos;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;

import tfg.accelbikeapp.Bluetooth.BluetoothManager;
import tfg.accelbikeapp.Bluetooth.ScanListener;
import tfg.accelbikeapp.Dispatcher;
import tfg.accelbikeapp.Response;

/**
 * Created by rodry on 4/05/16.
 */
public class ComandoScanBLE implements Comando, ScanListener {

    private static final long SCAN_PERIOD = 10000;

    @Override
    public void ejecutar(Context ctx, Object datos) {

        final BluetoothManager manager = new BluetoothManager(ctx);
        if (!manager.bluetoothActivado()){

            Dispatcher.getInstancia().dispatch(Response.BLE_DESACTIVADO, null);
            return;

        }

        manager.registerScannerListener(this);
        // TODO Comprobar si esta disponible y toda la verga

        Handler mHandler = new Handler();

        manager.listarDispositivos();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                manager.pararEscaneo();
                //TODO Quitarse de los listeners registrados

            }
        }, SCAN_PERIOD); // mHandler.postDelayed
    }

    @Override
    public void onScanResult(BluetoothDevice device) {

        Dispatcher.getInstancia().dispatch(Response.BLE_SCAN_RESULT, device);

    }
}
