package tfg.accelbikeapp.GUI.Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import tfg.accelbikeapp.Controlador;
import tfg.accelbikeapp.Evento;
import tfg.accelbikeapp.MainActivity;
import tfg.accelbikeapp.R;

/**
 * Created by David on 23/02/2016.
 */

public class ConfigFragment extends Fragment {

    Switch gps;
    Button ble;
    ListView lista;

    private static final long SCAN_PERIOD = 1000;

    private static final int REQUEST_ENABLE_BT = 1;

    private ArrayList<BluetoothDevice> dispositivos;
    private DispAdapter dispAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("ConfigFragment", "creado");
        View v = inflater.inflate(R.layout.config_layout, null);
        initUI(v);
        return v;

    }

    public void initUI(View v){

        gps = (Switch) v.findViewById(R.id.switch1);

        final Controlador controlador = ((MainActivity)getActivity()).getControlador();

        ble = (Button) v.findViewById(R.id.botonble);
        ble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controlador.accion(Evento.ESCANEAR_BLE, null);

            }
        });

        lista = (ListView) v.findViewById(R.id.listble);

        dispositivos = new ArrayList<>();
        dispAdapter = new DispAdapter(getContext(), dispositivos);

        lista.setAdapter(dispAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                controlador.accion(Evento.CONECTAR_BLE, dispositivos.get(position));
                //manager.conectarDispositivo(dispositivos.get(position));

            }
        });
    }

    public void addDevice(BluetoothDevice device){

        dispositivos.add(device);
        dispAdapter.notifyDataSetChanged();

    }

    public void onDestroy(){

        super.onDestroy();

        Log.i("ConfigFragment", "destruido");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode != Activity.RESULT_CANCELED){

                MainActivity activity = (MainActivity)getActivity();
                activity.getControlador().accion(Evento.ESCANEAR_BLE, null);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}