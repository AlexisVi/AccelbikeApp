package tfg.accelbikeapp.Comandos;

import android.content.Context;

import java.util.List;

import tfg.accelbikeapp.Dispatcher;
import tfg.accelbikeapp.File.FileManager;
import tfg.accelbikeapp.Response;

/**
 * Created by rodry on 10/05/16.
 */
public class ComandoListarSesiones implements Comando {

    @Override
    public void ejecutar(Context ctx, Object datos) {

        FileManager fm = new FileManager(ctx);
        List<String> sesiones = fm.listarSesiones();

        Dispatcher.getInstancia().dispatch(Response.LIST_SESSIONS, sesiones);

    }
}
