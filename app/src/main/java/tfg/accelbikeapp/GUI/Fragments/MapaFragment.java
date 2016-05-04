package tfg.accelbikeapp.GUI.Fragments;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import tfg.accelbikeapp.R;

/**
 * Created by David on 22/02/2016.
 */
public class MapaFragment extends Fragment {
    GoogleMap googleMap;
    MapView mapView;
    PolylineOptions polyline;

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mapa_layout,container,false);

        mapView = (MapView)v.findViewById(R.id.mi_mapa);
        mapView.onCreate(savedInstanceState);

        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addPolyline(polyline);

        List<LatLng> puntos = polyline.getPoints();

        if (!puntos.isEmpty()) {

            Log.i("MapaFragment", "La lista de puntos es no vacia");
            // Cogemos el punto del medio de la polilinea
            LatLng loc = puntos.get(puntos.size() / 2);
            // Hacemos zoom sobre ese punto
            // TODO estaria bien coger la distancia total para calcular el zoom o algo asi
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10.0f));

        }

        //googleMap.setMyLocationEnabled(true);
        return v;

    }

    public void setPolilinea(PolylineOptions opciones){

        polyline = opciones;

    }


}
