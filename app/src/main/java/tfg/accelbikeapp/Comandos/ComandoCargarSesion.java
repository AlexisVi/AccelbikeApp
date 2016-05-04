package tfg.accelbikeapp.Comandos;

import android.content.Context;

import com.google.android.gms.maps.model.PolylineOptions;

import tfg.accelbikeapp.Dispatcher;
import tfg.accelbikeapp.File.FileManager;
import tfg.accelbikeapp.Response;

/**
 * Created by rodry on 28/04/16.
 */
public class ComandoCargarSesion implements Comando {

    public void ejecutar(Context ctx, Object path){

        FileManager fileManager = new FileManager(ctx);
        fileManager.updateSesion((String) path);

        PolylineOptions pol = fileManager.leer();

        Dispatcher.getInstancia().dispatch(Response.LOAD_MAP, pol);

    }
}
