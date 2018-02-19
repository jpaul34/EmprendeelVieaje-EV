package com.ev.jonathan.emprendeelvieajeev;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class Loc implements LocationListener {

    Location localizacion;

    @Override
    public void onLocationChanged(Location location) {
        this.localizacion = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Location getLocalizacion() {
        return localizacion;
    }
}
