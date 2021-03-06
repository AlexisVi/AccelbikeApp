package tfg.accelbikeapp.File;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import android.location.Location;

/**
 * Created by alexis on 11/04/16.
 */
public class FileManager {

    private Context ctx;
    private String fullpath;
    File archivo;
    private double distancia;
    private long tiempo;

    public FileManager(Context context){

        this.ctx = context;
        this.fullpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Accelbike/";
        File f = new File (fullpath);

        if (!f.exists()){
            boolean created = f.mkdirs();
        }
    }

    /*
    private void delete(){
        File f = new File(archivo);
        f.delete();
    }
    */

    public void updateSesion(String sesion){

         archivo = new File(fullpath, sesion);
    }

    public ArrayList<String> listarSesiones(){

        String[] ficheros = new File(fullpath).list();

        return new ArrayList<>(Arrays.asList(ficheros));

    }

    public void guardar(String content){

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

            bw.write(content);
            bw.newLine();
            bw.flush();
            bw.close();

        }
        catch (Exception e){
            Log.i("Guardar", e.getMessage());
        }
    }

    public ArrayList<PolylineOptions> leer() throws Exception {

        Log.i("FileManager", "Leer!");

        if (isEmpty()) throw new IOException();

        ArrayList<PolylineOptions> ruta = new ArrayList<>();
        PolylineOptions polylineOptions = new PolylineOptions();
        Location loc = new Location("");
        Location locAux = new Location("");
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        Double lat;
        Double lon;
        Double latAux;
        Double lonAux;
        Double x;
        Double y;
        Double z;
        Double baseX;

        if ((linea = br.readLine()) != null) {

            String[] datos = linea.split(";");

            //----- leer lat,lon,ejes ----
            latAux = Double.parseDouble(datos[3]);
            lonAux = Double.parseDouble(datos[4]);
            x = Double.parseDouble(datos[0]);
            y = Double.parseDouble(datos[1]);
            z = Double.parseDouble(datos[2]);
            baseX = x;
            //----------------------------

            polylineOptions.add(new LatLng(Double.parseDouble(datos[3]), Double.parseDouble(datos[4])));

            while((linea = br.readLine()) != null && !linea.startsWith("T")){

                loc.setLatitude(latAux);
                loc.setLongitude(lonAux);

                polylineOptions = new PolylineOptions();
                datos = linea.split(";");

                lat = Double.parseDouble(datos[3]);
                lon = Double.parseDouble(datos[4]);
                polylineOptions.add(new LatLng(latAux, lonAux));
                polylineOptions.add(new LatLng(lat, lon));

                locAux.setLatitude(lat);
                locAux.setLongitude(lon);

                distancia += loc.distanceTo(locAux);

                //latAux y lonAux tienen ahora el valor de la ultima localizacion
                latAux = lat;
                lonAux = lon;

                //--------------  ¡Pruebas!  --------------
                /*
                Double aux = ((Math.abs(x) + Math.abs(y) + Math.abs(z)) - 1000 );
                if (aux >= 0 && aux<= 200){
                    polylineOptions.color(Color.GREEN);
                } else if (aux > 200 && aux<= 400){
                    polylineOptions.color(Color.YELLOW);
                } else if (aux > 400){
                    polylineOptions.color(Color.RED);
                }

                if ((x - baseX)  >= 0 && (x - baseX)  <= 400){
                    polylineOptions.color(Color.GREEN);
                } else if ((x - baseX)  > 400 && (x - baseX)  <= 450){
                    polylineOptions.color(Color.YELLOW);
                } else if ((x - baseX)  < 0){
                    polylineOptions.color(Color.RED);
                }
                */
                if (x  >= 0){
                    polylineOptions.color(Color.GREEN);
                } else if (x< 0 ){
                    polylineOptions.color(Color.RED);
                }

                ruta.add(polylineOptions);
                x = Double.parseDouble(datos[0]);
                y = Double.parseDouble(datos[1]);
                z = Double.parseDouble(datos[2]);
            }
            if(linea != null){
                tiempo = Long.valueOf(linea.substring(1));
            }
        }

        br.close();

        return ruta;
    }

    public boolean isEmpty(){ return archivo.length() == 0; }

    public double getDistancia(){ return distancia; }

    public long getTiempo(){ return tiempo; }
}