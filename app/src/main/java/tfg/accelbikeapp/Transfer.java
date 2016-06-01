package tfg.accelbikeapp;

import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by alexis on 24/05/16.
 */
public class Transfer {

    private ArrayList<PolylineOptions> polylineOption;
    private String sesion;
    private double distancia;
    private Long tiempo;


    public Transfer(ArrayList<PolylineOptions> polylineOption, String sesion, double distancia, Long tiempo){
        this.polylineOption = polylineOption;
        this.sesion = sesion;
        this.distancia = distancia;
        this.tiempo = tiempo;
    }

    public ArrayList<PolylineOptions> getPolylineOption() {
        return polylineOption;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public void setPolylineOption(ArrayList<PolylineOptions> polylineOption) {
        this.polylineOption = polylineOption;
    }

   public void setTiempo(Long tiempo){
       this.tiempo = tiempo;
   }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Long getTiempo() {
        return tiempo;
    }
}