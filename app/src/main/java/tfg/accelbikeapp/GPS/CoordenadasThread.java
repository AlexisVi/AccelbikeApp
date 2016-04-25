package tfg.accelbikeapp.GPS;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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
    private ArrayList<Double> coordenadas;

    private boolean gpsActivo;

    public CoordenadasThread(Context ctx){

        super();
        this.ctx = ctx;

        mlocManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        //mlocListener.setMainActivity(this);
        //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

    }

    public void run(){
        while (!Thread.interrupted()) {

            getLocation();

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                return;

            }
        }
    }

    public void getLocation(){
        try {
            gpsActivo = mlocManager.isProviderEnabled(mlocManager.GPS_PROVIDER);
        }catch (Exception e){}

        if (gpsActivo) {
            try {
                mlocManager.requestLocationUpdates(mlocManager.GPS_PROVIDER,
                        1000 * 60,
                        10,
                        mlocListener);

                location = mlocManager.getLastKnownLocation(mlocManager.GPS_PROVIDER);
                if (location != null){
                    coordenadas.add(0,location.getLatitude());
                    coordenadas.add(1,location.getLongitude());
                    //lat = location.getLatitude();
                    //lon = location.getLongitude();
                }else{
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
    }

    public ArrayList<Double> getCoordenadas() {
        return coordenadas;
    }
}
