package tfg.accelbikeapp.GPS;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;


import java.util.ArrayList;

/**
 * Created by alexis on 25/04/16.
 */
public class CoordenadasThread extends Thread{

    private double lat;
    private double lon;
    private Context ctx;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;
    private Location location;
    private volatile ArrayList<Double> coordenadas; //volatile por "la cosa de pillar" datos cuando se pueden estar modificando

    private boolean gpsActivo;

    public CoordenadasThread(Context ctx){

        super();
        this.ctx = ctx;

        mlocManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        coordenadas = new ArrayList<>(2);

        //mlocListener.setMainActivity(this);
        //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

    }

    public void run(){

        try{

            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mlocListener, Looper.getMainLooper());

        }
        catch(SecurityException e){

            e.printStackTrace();

        }

        while (!Thread.interrupted()) {

            getLocation();

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                return;

            }
        }
    }

    private void getLocation(){
        try {
            gpsActivo = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){

            Log.i("CoordenadasThread", "Excepciooon");

        }

        if (gpsActivo) {
            try {
               /* mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000 * 60,
                        10,
                     mlocListener);*/

                location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null){
                    coordenadas.add(0,location.getLatitude());
                    coordenadas.add(1,location.getLongitude());

                    //lat = location.getLatitude();
                    //lon = location.getLongitude();
                }else{
                    Log.e("CoordenadasThread", "location es null!!");
                    lat = 0.0;
                    lon = 0.0;
                }

            }
            catch(SecurityException e){

                e.printStackTrace();

            }

            //Log.i("lat", Double.toString(lat));
            //Log.i("lon", Double.toString(lon));
        }
        else Log.i("CoordenadasThread", "Provider disabled");
    }

    public ArrayList<Double> getCoordenadas() {
        return coordenadas;
    }
}
