package tfg.accelbikeapp.GPS;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

/**
 * Created by alexis on 25/04/16.
 */
public class MyLocationListener implements LocationListener {

    public MyLocationListener(){

        Looper.prepare();

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i("LocationListener", Double.toString(location.getLatitude()));
        Log.i("LocationListener", Double.toString(location.getLongitude()));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
