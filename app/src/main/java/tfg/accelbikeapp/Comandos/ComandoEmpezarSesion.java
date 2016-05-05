package tfg.accelbikeapp.Comandos;

import android.content.Context;

import tfg.accelbikeapp.Bluetooth.BLEGatt;
import tfg.accelbikeapp.Dispatcher;
import tfg.accelbikeapp.File.FileThread;
import tfg.accelbikeapp.Response;

/**
 * Created by rodry on 4/05/16.
 */
public class ComandoEmpezarSesion implements Comando {

    @Override
    public void ejecutar(Context ctx, Object datos) {

        // Si no esta conectado al dispositivo no hacemos nada
        if (!BLEGatt.getInstancia().isConnected()){

            Dispatcher.getInstancia().dispatch(Response.GATT_DESCONECTADO, null);
            return;

        }

        FileThread thread = new FileThread(ctx, (String)datos);
        thread.start();

    }
}
