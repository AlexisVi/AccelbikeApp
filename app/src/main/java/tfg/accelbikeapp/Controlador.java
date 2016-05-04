package tfg.accelbikeapp;

import android.content.Context;

import tfg.accelbikeapp.Comandos.Comando;
import tfg.accelbikeapp.Comandos.ComandoCargarSesion;
import tfg.accelbikeapp.Comandos.ComandoScanBLE;

/**
 * Created by rodry on 29/04/16.
 */
public class Controlador {

    private Context ctx;

    public Controlador(Context context){

        ctx = context;

    }

    public void accion(Evento evento, Object datos){

        Comando c = null;

        switch (evento){

            case ESCANEAR_BLE:
                c = new ComandoScanBLE();
                break;

            case CARGAR_SESION:
                c = new ComandoCargarSesion();
                break;

        }

        if (c != null)
            c.ejecutar(ctx, datos);

    }

}
