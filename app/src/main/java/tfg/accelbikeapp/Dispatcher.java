package tfg.accelbikeapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.PolylineOptions;

import tfg.accelbikeapp.GUI.Fragments.ConfigFragment;
import tfg.accelbikeapp.GUI.Fragments.MapaFragment;

/**
 * Created by rodry on 28/04/16.
 */
public class Dispatcher {

    private static Dispatcher instancia;

    private static final int REQUEST_ENABLE_BT = 1;

    private Dispatcher(){}

    public static Dispatcher getInstancia(){

        if (instancia == null)
            instancia = new Dispatcher();

        return instancia;

    }

    public void dispatch(Response resp, Object data){

        switch (resp){

            case LOAD_MAP:
                MapaFragment mapa = new MapaFragment();
                FragmentLoader.getInstancia().cargar(mapa);
                mapa.setPolilinea((PolylineOptions) data);
                break;

            case BLE_DESACTIVADO:
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // FIXME Esto es horrible, si cambia de fragment mientras aun esta buscando, va a petar
                // TODO mirar tags para cargar y ver el actual
                ConfigFragment config = (ConfigFragment)FragmentLoader.getInstancia().getCurrentFragment();
                config.startActivityForResult(turnBTon, REQUEST_ENABLE_BT);
                break;

            case BLE_SCAN_RESULT:
                // FIXME Esto es horrible, si cambia de fragment mientras aun esta buscando, va a petar
                // TODO mirar tags para cargar y ver el actual
                ConfigFragment config2 = (ConfigFragment)FragmentLoader.getInstancia().getCurrentFragment();
                config2.addDevice((BluetoothDevice)data);
                break;

        }
    }

}
