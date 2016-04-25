package tfg.accelbikeapp.GPS;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by alexis on 25/04/16.
 */
public class CoordenadasThread extends Thread{

    private double lat;
    private double lon;
    private Context ctx;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;
    private boolean gpsActivo;

    public CoordenadasThread(Context ctx){

        super();
        this.ctx = ctx;

        mlocManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        //mlocListener.setMainActivity(this);
        //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

    }

    public void getLocation(){
        gpsActivo = mlocManager.isProviderEnabled(mlocManager.GPS_PROVIDER);
    }
}
