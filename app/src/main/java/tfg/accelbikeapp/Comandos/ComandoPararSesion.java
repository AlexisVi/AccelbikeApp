package tfg.accelbikeapp.Comandos;

import android.content.Context;

/**
 * Created by rodry on 4/05/16.
 */
public class ComandoPararSesion implements Comando {

    @Override
    public void ejecutar(Context ctx, Object datos) {

        for (Thread t : Thread.getAllStackTraces().keySet()) {

            if (t.getName().equals((String)datos)) {

                t.interrupt();
                break;

            }
        }
    }
}
