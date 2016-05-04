package tfg.accelbikeapp.Comandos;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import tfg.accelbikeapp.Bluetooth.BluetoothManager;

/**
 * Created by rodry on 4/05/16.
 */
public class ComandoConectBLE implements Comando {

    @Override
    public void ejecutar(Context ctx, Object datos) {

        BluetoothManager manager = new BluetoothManager(ctx);
        manager.conectarDispositivo((BluetoothDevice)datos);

    }
}
