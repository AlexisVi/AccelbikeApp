package tfg.accelbikeapp.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexis on 11/04/16.
 */
public class FileManager {

    private Context ctx;
    private String fullpath;
    File archivo;

    public FileManager(Context context){

        this.ctx = context;
        this.fullpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Accelbike/";
        File f = new File (fullpath);

        if (!f.exists()){
            boolean created = f.mkdirs();
        }
    }

    /*private void delete(){
        File f = new File(archivo);
        f.delete();
    }*/

    public void updateSesion(String sesion){

         archivo = new File(fullpath, sesion);

    }

    public ArrayList<String> listarSesiones(){

        String[] ficheros = new File(fullpath).list();

        return new ArrayList<>(Arrays.asList(ficheros));

    }

    public void guardar(String x, String y, String z, String lat, String lon){

        String content = x + ";" + y + ";" + z + ";" + lat + ";" + lon ;

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

            bw.write(content);
            bw.newLine();
            bw.flush();
            bw.close();

        }
        catch (Exception e){
            Log.i("Guardar",e.getMessage());
        }
    }

    public PolylineOptions leer() {
        String texto = "";

        Log.i("FileManager", "Leer!");

        PolylineOptions polylineOptions = new PolylineOptions();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea;
            while ((linea = br.readLine()) != null){

                String[] datos = linea.split(";");
                Double lat = Double.parseDouble(datos[3]);
                Double lon = Double.parseDouble(datos[4]);

                polylineOptions.add(new LatLng(lat, lon));

            }

            br.close();

        }
        catch (Exception ex)
        {
            //Log.e("Leer", ex.getMessage());
            ex.printStackTrace();
        }

        return polylineOptions;
    }
}