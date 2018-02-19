package com.ev.jonathan.emprendeelvieajeev;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    Button btn_informacion;
    Button btn_ubicacion;
    Button btn_lugares_turisticos;
    Button btn_acercade;
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btn_informacion = (Button) findViewById(R.id.btn_inicio_info);
        btn_ubicacion = (Button) findViewById(R.id.btn_inicio_ubi);
        btn_lugares_turisticos = (Button) findViewById(R.id.btn_inicio_lugarturis);
        btn_acercade = (Button) findViewById(R.id.btn_ini_acercade);
        correoUsuario = getIntent().getExtras().getString("correoUsuario");

        btn_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInformacion = new Intent(Inicio.this, InformacionPersonal.class);
                intentInformacion.putExtra("correoUsuario", correoUsuario);
                Inicio.this.startActivity(intentInformacion);
            }
        });

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(gpsEnabled) {
                    Intent intentMapa = new Intent(Inicio.this, Mapa.class);
                    Inicio.this.startActivity(intentMapa);
                }else{
                    Toast.makeText(Inicio.this, "Encienda el GPS", Toast.LENGTH_LONG).show();
                    comprobarGPS(gpsEnabled);
                }
            }
        });

        btn_lugares_turisticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLugarTuristico = new Intent(Inicio.this, LugarTuristico.class);
                Inicio.this.startActivity(intentLugarTuristico);
            }
        });

        btn_acercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAcercaDe = new Intent(Inicio.this, AcercaDe.class);
                Inicio.this.startActivity(intentAcercaDe);
            }
        });

    }

    public void comprobarGPS(boolean gpsEnabled){
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        } else {
            Toast.makeText(Inicio.this, "GPS Encendido", Toast.LENGTH_LONG).show();
        }
    }
}
