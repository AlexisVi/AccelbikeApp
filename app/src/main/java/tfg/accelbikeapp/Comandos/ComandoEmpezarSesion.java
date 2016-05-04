package tfg.accelbikeapp.Comandos;

import android.content.Context;

import tfg.accelbikeapp.File.FileThread;

/**
 * Created by rodry on 4/05/16.
 */
public class ComandoEmpezarSesion implements Comando {

    @Override
    public void ejecutar(Context ctx, Object datos) {

        FileThread thread = new FileThread(ctx, (String)datos);
        thread.start();

    }
}
