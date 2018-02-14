package com.ev.jonathan.emprendeelvieajeev;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LugarTuristico extends AppCompatActivity {

    private static String ipLugar = "http://emprendeelviaje.cu.ma/archivosphpEV/lugar_GETID.php?id_lugar=";
    private static String ipTipoLugar = "http://emprendeelviaje.cu.ma/archivosphpEV/tipoLugar_GETID.php?id_tipolugar=";
    private static String ipConsultaIDlugar = "http://emprendeelviaje.cu.ma/archivosphpEV/lugar_GETID_CIUDADPAIS.php?ciudad=";

    private TextView tv_nombre_lugar;
    private TextView tv_tipo_lugar;
    private TextView tv_informacion_lugar;
    private RequestQueue mRequest;
    private VolleyRP volley;

    private TextView tv_nom;
    private String direccion="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_turistico);

        tv_nombre_lugar = (TextView) findViewById(R.id.tv_lugarturistico_nombre);
        tv_informacion_lugar = (TextView) findViewById(R.id.tv_lugarturistico_informacion);
        tv_tipo_lugar =  (TextView) findViewById(R.id.tv_lugarturistico_tipo_lugar);
        tv_nom = (TextView) findViewById(R.id.tv_lugarturistico_nomb);
        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        miUbicacion();

        String[] parts = direccion.split(",");
        String dirrec = parts[0];
        String ciudad_CP = parts[1];
        String pais = parts[1];

        String[] parts2 = ciudad_CP.split(" ");
        String ciudad = parts2[1];
        String codigopostal = parts2[2];
        solicitudIdLujarJASON(ipConsultaIDlugar+ciudad);
    }

    public void mostrarLugarTuristico(String id_Lugar){
        if(!id_Lugar.equals("")){
            solicitudLujarJASON(ipLugar+id_Lugar);

        }else{
            tv_nom.setVisibility(View.INVISIBLE);
            tv_nom.setText("No existe información disponible");
            tv_nombre_lugar.setVisibility(View.INVISIBLE);
            TextView tv_info = (TextView) findViewById(R.id.tv_lugarturistico_info);;
            tv_info.setVisibility(View.INVISIBLE);
            tv_informacion_lugar.setVisibility(View.INVISIBLE);
            TextView tv_tipo = (TextView) findViewById(R.id.tv_lugarturistico_tipo);;
            tv_tipo.setVisibility(View.INVISIBLE);
            tv_tipo_lugar.setVisibility(View.INVISIBLE);
        }
    }

    public void solicitudLujarJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                obtenerDatosLugar(datosSolicitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LugarTuristico.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void obtenerDatosLugar(JSONObject datosSolicitud) {
        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {
                JSONObject datosLugar = new JSONObject(datosSolicitud.getString("datos"));

                String nombre_lugar = datosLugar.getString("NOMBRE_LUGAR");
                String informacion = datosLugar.getString("INFORMACION");
                String tipoLugar = datosLugar.getString("ID_TIPOLUGAR");
                solicitudTipoLujarJASON(ipTipoLugar+tipoLugar);

                tv_nombre_lugar.setText(nombre_lugar);
                tv_informacion_lugar.setText(informacion);

            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

    public void solicitudTipoLujarJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                obtenerDatosTipoLugar(datosSolicitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LugarTuristico.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void obtenerDatosTipoLugar(JSONObject datosSolicitud) {
        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {
                JSONObject datosLugar = new JSONObject(datosSolicitud.getString("datos"));
                String tipoLugar = datosLugar.getString("TIPO");

                tv_tipo_lugar.setText(tipoLugar);

            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }


    public void solicitudIdLujarJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                obtenerIdLugar(datosSolicitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LugarTuristico.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);

    }

    public void obtenerIdLugar(JSONObject datosSolicitud) {
        String id = "";
        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {
                JSONObject datosLugar = new JSONObject(datosSolicitud.getString("datos"));
                String id_lugarAux = datosLugar.getString("ID_LUGAR");
                mostrarLugarTuristico(id_lugarAux);
            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion = DirCalle.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    LocationListener location_Listener = new LocationListener() {

        LugarTuristico lugar;

        public void onLocationChanged(Location location){
            this.lugar.setLocation(location);
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

    };

    private void miUbicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        setLocation(location);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);
    }
}
