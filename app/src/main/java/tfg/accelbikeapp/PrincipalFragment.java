package tfg.accelbikeapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import tfg.accelbikeapp.Bluetooth.BLEGatt;
import tfg.accelbikeapp.Bluetooth.GattObserver;
import tfg.accelbikeapp.File.FileManager;
import tfg.accelbikeapp.File.FileThread;

/**
 * Created by David on 22/02/2016.
 */
public class PrincipalFragment extends Fragment implements GattObserver {

    Button inicio, parar;
    Chronometer crono;
    TextView acel;

    private FileManager fileManager;
    private Date fecha;

    Thread th;

    //long Time = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //RelativeLayout ll = (RelativeLayout) inflater.inflate(R.layout.principal_layout, container, false);
        View v = inflater.inflate(R.layout.principal_layout, null);
        BLEGatt.getInstancia().registerObserver(this);

        fileManager = new FileManager(getContext());

        initUI(v);
        return v;
    }

    public void initUI(View v){

        crono = (Chronometer) v.findViewById(R.id.cronometro);
        inicio = (Button) v.findViewById(R.id.inicio);
        parar = (Button) v.findViewById(R.id.parar);
        acel = (TextView) v.findViewById(R.id.acel);

        inicio.setEnabled(true);
        parar.setEnabled(false);
        acel.setText("info acel");

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Si no esta conectado al dispositivo no hacemos nada
                if (!BLEGatt.getInstancia().isConnected()){

                    //TODO mandar a la pantalla de configuracion?
                    /*android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id., new ConfigFragment());
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();*/
                    Toast.makeText(getContext(), "Conectate a un dispositivo antes", Toast.LENGTH_LONG).show();
                    return;
                    // Nuevoo

                }

                // Sacamos el servicio de ubicacion para comprobar si esta activado
                LocationManager loc =
                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

                if (!loc.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    return;

                }

                crono.start();
                inicio.setEnabled(false);
                parar.setEnabled(true);
                crono.setBase(SystemClock.elapsedRealtime());
                startThread();
            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inicio.setEnabled(true);
                parar.setEnabled(false);
                crono.stop();
                //Calcular COSITAS
                stopThread();

            }
        });
    }

    public void startThread(){

        //th = new BluetoothThread();
        th = new FileThread(this.getContext());
        th.start();

    }

    public void stopThread(){

        th.interrupt();
        th = null;

    }

    public void onDestroy(){

        Log.i("PrincipalFragment", "Destruido");

        super.onDestroy();
        BLEGatt.getInstancia().removeObserver(this);

    }

    public void onDataRead(final List<Short> valores){

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                acel.setText(Short.toString(valores.get(0)) + ", " +
                             Short.toString(valores.get(1)) + ", " +
                             Short.toString(valores.get(2)));

            }
        });
    }
}