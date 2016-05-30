package tfg.accelbikeapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import java.util.ArrayList;
import tfg.accelbikeapp.GUI.Fragments.ConfigFragment;
import tfg.accelbikeapp.GUI.Fragments.MapaFragment;
import tfg.accelbikeapp.GUI.Fragments.TabFragment;

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
                mapa.setPolilinea(((Transfer) data).getPolylineOption());
                mapa.setSesion(((Transfer) data).getSesion());
                mapa.setDistancia(((Transfer) data).getDistancia());
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

            case GATT_DESCONECTADO:
                ConfigFragment fragment3 = new ConfigFragment(){
                    @Override
                    public void onAttach(Context context) {
                        super.onAttach(context);
                        Toast.makeText(context, "Conectalo al Bluetooth", Toast.LENGTH_LONG).show();
                    }
                };
                FragmentLoader.getInstancia().cargar(fragment3, "CONFIG_FRAGMENT");

                break;

            case LIST_SESSIONS:
                TabFragment tb = (TabFragment)FragmentLoader.getInstancia().getFragmentById("TAB_FRAGMENT");

                for (String sesion : (ArrayList<String>) data)
                    tb.actualizarSesiones(sesion);

                break;
        }
    }
}