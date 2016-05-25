package tfg.accelbikeapp.Comandos;

import android.content.Context;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import tfg.accelbikeapp.Dispatcher;
import tfg.accelbikeapp.File.FileManager;
import tfg.accelbikeapp.Response;
import tfg.accelbikeapp.Transfer;

/**
 * Created by rodry on 28/04/16.
 */
public class ComandoCargarSesion implements Comando {

    public void ejecutar(Context ctx, Object path){

        FileManager fileManager = new FileManager(ctx);
        fileManager.updateSesion((String) path);
        ArrayList<PolylineOptions> pol = fileManager.leer();
        double distancia = fileManager.getDistancia();
        Transfer transfer = new Transfer(pol,(String) path, distancia);

        Dispatcher.getInstancia().dispatch(Response.LOAD_MAP, transfer);

    }
}
