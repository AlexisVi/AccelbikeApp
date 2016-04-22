package tfg.accelbikeapp;

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
import java.util.Date;
import java.util.List;

import tfg.accelbikeapp.Bluetooth.BLEGatt;
import tfg.accelbikeapp.Bluetooth.BluetoothThread;
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

                //fecha = new Date();

                //Log.i("Principal",fechaAux);
                //fileManager.updateSesion(fecha.toString());

                /*final String pepe = fileManager.leer();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        acel.setText(pepe);

                    }
                });*/

                // Si no esta conectado al dispositivo no hacemos nada
                if (!BLEGatt.getInstancia().isConnected()){

                    //TODO mandar a la pantalla de configuracion?
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
        //final String datos;
        //fileManager.updateSesion("sesionActual");
        //datos = fileManager.leer();
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                acel.setText(Short.toString(valores.get(0)) + ", " +
                             Short.toString(valores.get(1)) + ", " +
                             Short.toString(valores.get(2)));

                //acel.setText(datos);

            }
        });

        //fileManager.guardar(Short.toString(valores.get(0)), Short.toString(valores.get(1)),
             //   Short.toString(valores.get(2)));
    }
}