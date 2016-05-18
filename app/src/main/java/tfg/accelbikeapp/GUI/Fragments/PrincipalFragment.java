package tfg.accelbikeapp.GUI.Fragments;

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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import tfg.accelbikeapp.Bluetooth.BLEGatt;
import tfg.accelbikeapp.Bluetooth.GattObserver;
import tfg.accelbikeapp.Comandos.ComandoCargarSesion;
import tfg.accelbikeapp.Evento;
import tfg.accelbikeapp.File.FileManager;
import tfg.accelbikeapp.File.FileThread;
import tfg.accelbikeapp.MainActivity;
import tfg.accelbikeapp.R;

/**
 * Created by David on 22/02/2016.
 */
public class PrincipalFragment extends Fragment implements GattObserver {

    MainActivity activity;

    Button inicio, parar;
    Chronometer crono;
    TextView acel;

    private String sesion;

    Thread th;

    //long Time = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //RelativeLayout ll = (RelativeLayout) inflater.inflate(R.layout.principal_layout, container, false);
        View v = inflater.inflate(R.layout.principal_layout, null);
        BLEGatt.getInstancia().registerObserver(this);

        activity = (MainActivity)getActivity();

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

        sesion = "default";

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Sacamos el servicio de ubicacion para comprobar si esta activado
                LocationManager loc =
                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

                if (!loc.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    return;

                }

                Calendar calendar = Calendar.getInstance();
                String year = String.format("%04d", calendar.get(Calendar.YEAR));
                String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1); // Enero es el mes 0
                String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
                String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                String min = String.format("%02d", calendar.get(Calendar.MINUTE));
                String sec = String.format("%02d", calendar.get(Calendar.SECOND));

                sesion =  year + "_" + month + "_" + day + "_" + hour + "_" + min + "_" + sec;

                crono.start();
                inicio.setEnabled(false);
                parar.setEnabled(true);
                crono.setBase(SystemClock.elapsedRealtime());
                //startThread();
                activity.getControlador().accion(Evento.EMPEZAR_SESION, sesion);
            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inicio.setEnabled(true);
                parar.setEnabled(false);
                crono.stop();
                //Calcular COSITAS

                activity.getControlador().accion(Evento.PARAR_SESION, sesion);
                activity.getControlador().accion(Evento.CARGAR_SESION, sesion);

               // stopThread();

            }
        });
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