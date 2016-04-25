package tfg.accelbikeapp.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by alexis on 11/04/16.
 */
public class FileManager{

    private Context ctx;
    private String fullpath;
    File archivo;

    public FileManager(Context context){

        this.ctx = context;
        this.fullpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Alexis/";
        File f = new File (fullpath);

        if (!f.exists()){
            f.mkdirs();
        }
    }

    /*private void delete(){
        File f = new File(archivo);
        f.delete();
    }*/

    public void updateSesion(String sesion){

         archivo = new File(fullpath, sesion);
    }

    public void guardar(String x, String y, String z, String lat, String lon){
        //Chupa Carlos
        //String filename = "prueba";
        String content = x + ";" + y + ";" + z + ";" + lat + ";" + lon ;
        Log.i("CHUPA CARLOS", content);

        try {
            //File f = new File(sesionActual);
            //FileOutputStream fos = ctx.openFileOutput(fullpath, Context.MODE_APPEND);

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo,true));

            //Log.i("fos", "pasado");
            //byte[] contentInBytes  = content.getBytes();
            //Log.i("BYTE[]","pasado");
            bw.write(content);
            //fos.write(contentInBytes);
            //Log.i("fos.write", "pasado");
            //Log.i("guardar", sesionActual);
            //fos.flush();
            //fos.close();
            bw.newLine();
            bw.flush();
            bw.close();

        }catch (Exception e){
            Log.i("Guardar",e.getMessage());
        }
    }

    public String leer(){
        String texto = "";
        try
        {
            //BufferedReader fin = new BufferedReader(new InputStreamReader(
                    //ctx.openFileInput(sesionActual)));
            //Log.i("BufferedReader", "BufferedReader");
            //texto = fin.readLine();
            //Log.i("LEER", texto);
            //fin.close();
            //Log.i("leer",sesionActual);
        }
        catch (Exception ex)
        {
            //Log.e("Leer", ex.getMessage());
            ex.printStackTrace();
        }
        return texto;
    }
}