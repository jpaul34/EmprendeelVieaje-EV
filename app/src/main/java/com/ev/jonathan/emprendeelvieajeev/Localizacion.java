package com.ev.jonathan.emprendeelvieajeev;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

public class Localizacion  implements LocationListener {
    private Mapa mapaActivity;
    private LugarTuristico lugarActivity;
    private TextView mensaje1;
    private Location localizacion;
    private int llamada=0;

    public Localizacion(TextView mensaje1, int num){
        this.mensaje1=mensaje1;
        this.llamada=num;
    }


    public Mapa getMapActivity() {
        return mapaActivity;
    }

    public Mapa getLugarActivity() {
        return mapaActivity;
    }

    public void setMapaActivity(Mapa mapaActivity) {
        this.mapaActivity = mapaActivity;
        this.llamada = 1;
    }

    public void setLugarActivity(LugarTuristico lugarActivity) {
        this.lugarActivity = lugarActivity;
        this.llamada = 2;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la detecci—n de un cambio de ubicacion
        loc.getLatitude();
        loc.getLongitude();
        localizacion = loc;

        if(llamada == 1 ){
            mapaActivity.setLocation(loc);
        }
        if(llamada == 2){
            lugarActivity.setLocation(loc);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        mensaje1.setText("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este mŽtodo se ejecuta cuando el GPS es activado
        mensaje1.setText("GPS Activado");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Este metodo se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de localizacion (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temp˜ralmente no disponible pero se
        // espera que este disponible en breve
        // AVAILABLE -> Disponible
    }

    public Location getLocalizacion() {
        return localizacion;
    }
}