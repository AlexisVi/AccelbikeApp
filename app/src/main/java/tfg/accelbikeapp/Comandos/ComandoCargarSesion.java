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
        try {
            ArrayList<PolylineOptions> pol = fileManager.leer();
            double distancia = fileManager.getDistancia();
            Long tiempo = fileManager.getTiempo();
            Transfer transfer = new Transfer(pol,(String)path,
                    distancia, tiempo);

            Dispatcher.getInstancia().dispatch(Response.LOAD_MAP, transfer);
        } catch (Exception e){
            Dispatcher.getInstancia().dispatch(Response.ERROR_CARGAR,null);
        }
    }
}