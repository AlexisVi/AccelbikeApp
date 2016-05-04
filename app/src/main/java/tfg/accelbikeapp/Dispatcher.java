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
                FragmentLoader.getInstancia().cargar(mapa, "MAPA_FRAGMENT");
                mapa.setPolilinea((PolylineOptions) data);
                break;

            case BLE_DESACTIVADO:
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                ConfigFragment fragment =
                        (ConfigFragment)FragmentLoader.getInstancia().getFragmentById("CONFIG_FRAGMENT");

                if (fragment != null){

                    fragment.startActivityForResult(turnBTon, REQUEST_ENABLE_BT);

                }

                break;

            case BLE_SCAN_RESULT:
                ConfigFragment fragment2 =
                        (ConfigFragment)FragmentLoader.getInstancia().getFragmentById("CONFIG_FRAGMENT");

                if (fragment2 != null){

                    fragment2.addDevice((BluetoothDevice) data);

                }

                break;

        }
    }

}
