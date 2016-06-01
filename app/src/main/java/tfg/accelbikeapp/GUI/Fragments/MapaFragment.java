package tfg.accelbikeapp.GUI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tfg.accelbikeapp.R;
/**
 * Created by David on 22/02/2016.
 */
public class MapaFragment extends Fragment {
    GoogleMap googleMap;
    MapView mapView;
    TextView editText;
    //TextView editTextDistancia;
    String sesion;
    double distancia;
    Long tiempo;
    ArrayList<PolylineOptions> polyline;

    //Expandable List
    HashMap<String, List<String>> Categorias_mediciones;
    List<String> Lista_mediciones;
    ExpandableListView Exp_list;
    ExpandableAdapter adapter;
    //Fin Expandable List

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

        //Expandable View
        Exp_list = (ExpandableListView)v.findViewById(R.id.mi_lista_expandible);
        //Cargamos la distancia en el menu

        Categorias_mediciones = DataProvider.getInfo(distancia, tiempo);
        Lista_mediciones = new ArrayList<>(Categorias_mediciones.keySet());
        adapter = new ExpandableAdapter(this.getContext(),Categorias_mediciones,Lista_mediciones);
        Exp_list.setAdapter(adapter);
        //Mapa Google
        mapView = (MapView)v.findViewById(R.id.mi_mapa);
        editText = (TextView)v.findViewById(R.id.editText);
        //editTextDistancia = (TextView)v.findViewById(R.id.editText2);
        mapView.onCreate(savedInstanceState);

        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        int i = 0;

        while (i < polyline.size()){
            googleMap.addPolyline(polyline.get(i));
            i++;
        }


        //Sesion que se muestra
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText(sesion);
                //editTextDistancia.setText("Distancia recorrida:" + distancia);
            }
        });

        // Cogemos el punto del medio de la polilinea
        PolylineOptions polylineOptionsAux = polyline.get(polyline.size() / 2);
        List<LatLng> puntos = polylineOptionsAux.getPoints();

        if (!puntos.isEmpty()) {
            // Hacemos zoom sobre el punto medio
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntos.get(0), 10.0f));

        }
        //googleMap.setMyLocationEnabled(true);
        return v;
    }

    public void setPolilinea(ArrayList<PolylineOptions> opciones){ polyline = opciones; }

    public void setSesion(String nombre){ sesion = nombre; }

    public void setDistancia(double distancia){
        this.distancia = distancia;
    }

    public void setTiempo (Long tiempo) {
        this.tiempo = tiempo;
    }
}