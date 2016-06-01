package tfg.accelbikeapp.GUI.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProvider {

    public static HashMap<String,List<String>> getInfo(double distancia , Long tiempo){

        HashMap<String,List<String>> Detalles_mediciones = new HashMap<String,List<String>>();

        // Distancia
        List<String> MedicionUno = new ArrayList<>();
        String distanciaAux = String.format("%.2f",distancia/1000f);
        MedicionUno.add(distanciaAux + "Km");

        // Tiempo
        int segundos = (int) (tiempo / 1000) % 60 ;
        int minutos = (int) ((tiempo / (1000*60)) % 60);
        int horas   = (int) ((tiempo / (1000*60*60)) % 24);
        List<String> MedicionDos = new ArrayList<>();
        MedicionDos.add(horas + " h " + minutos + " m " + segundos + " s ");

        // Velocidad media
        double horasVel =  ((double)tiempo / 3600000f);
        double velocidadMedia = Double.valueOf(distanciaAux.replace(",", "."))/horasVel;
        List<String> MedicionTres =  new ArrayList<>();
        MedicionTres.add(velocidadMedia + " Km/h");

        /*
        List<String> MedicionCuatro =  new ArrayList<>();
        MedicionCuatro.add("MedicionCuatro hijo");*/

        Detalles_mediciones.put("Distancia recorrida", MedicionUno);
        Detalles_mediciones.put("Tiempo",MedicionDos);
        Detalles_mediciones.put("Velocidad media",MedicionTres);

        //Detalles_mediciones.put("MedicionCuatro",MedicionCuatro);

        return Detalles_mediciones;
    }
}