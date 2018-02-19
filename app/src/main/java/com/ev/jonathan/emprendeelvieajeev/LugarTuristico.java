package com.ev.jonathan.emprendeelvieajeev;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LugarTuristico extends AppCompatActivity {

    private static String ipLugares = "http://emprendeelviaje.cu.ma/archivosphpEV/lugares_GETALL_CIUDAD.php?ciudad=";

    private TextView tv_mensaje;
    private TextView tv_texto;
    private RequestQueue mRequest;
    private VolleyRP volley;

    private TextView tv_nom;
    private String direccion="";

    private boolean isDato=false;

    private ArrayList<Lugar> lugares;

    Location localizacion = null;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_turistico);
        tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);

        tv_texto = (TextView) findViewById(R.id.tv_texto);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lugares = new ArrayList<Lugar>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(this, "Entro 1", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }else {
            // Toast.makeText(this, "Entro 2", Toast.LENGTH_SHORT).show();
            comprobarGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //     Toast.makeText(this, "Entro al onRequestPermision", Toast.LENGTH_SHORT).show();
                comprobarGPS();
                return;
            }
        }
    }

    public void comprobarGPS(){
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            tv_mensaje.setText("\nGPS APAGADO\n");
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            comprobarGPS();
        } else {
            tv_mensaje.setText("\nGPS ENCENDIDO\n");
            locationStart(locationManager, gpsEnabled);
        }
    }

    public void locationStart(LocationManager mlocManager, boolean gpsEnabled) {
        //  Toast.makeText(this, "location star", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        Localizacion local = new Localizacion(tv_mensaje,2);
        local.setLugarActivity(LugarTuristico.this);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) local);
        tv_mensaje.setText("");
    }


    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0 && !isDato) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    tv_mensaje.setText("");
                    /*Address DirCalle = list.get(0);
                    direccion = DirCalle.getAddressLine(0);
                    tv_mensaje.setText(direccion);

                    String[] parts = direccion.split(",");
                    String dirrec = parts[0];
                    final String ciudad_CP = parts[1];
                    final String pais = parts[1];
                    tv_mensaje.setText(ciudad_CP);
                    int pos = ciudad_CP.indexOf(" ");

                    if(pos!=-1){
                      //  Toast.makeText(this, ciudad_CP+" entro en el if", Toast.LENGTH_SHORT).show();
                        String[] parts2 = ciudad_CP.split(" ");
                        String ciudad = parts2[1];
                        //String codigopostal = parts2[1];
                        solicitudJASON(ipLugares+ciudad);
                    }else{
                      //  Toast.makeText(this, ciudad_CP+" entro en el else", Toast.LENGTH_SHORT).show();
                        String[] parts2 = ciudad_CP.split(" ");
                        String ciudad = parts2[1];
                        String codigopostal = parts2[2];
                        solicitudJASON(ipLugares+ciudad);
                    }*/
                    String city = list.get(0).getLocality();
                    tv_mensaje.setText(city);
                    solicitudJASON(ipLugares+city);
                    isDato=true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void solicitudJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                verificarDatosLogin(datosSolicitud);
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

    public void verificarDatosLogin(JSONObject datosSolicitud) {
        String salida="";
        // tv_texto.setText(salida);
        try {
            String resultado = datosSolicitud.getString("resultado");

            if (resultado.equals("CC")) {

                JSONArray datosLugar = new JSONArray(datosSolicitud.getString("datos"));
                int i=0;
                // Toast.makeText(this, ""+datosLugar.length()+" "+datosLugar.getString(0) , Toast.LENGTH_SHORT).show();
                JSONObject jObject;
                while(i<datosLugar.length()){
                    //Toast.makeText(this, ""+i , Toast.LENGTH_SHORT).show();
                    //JSONObject  = datosLugar.getJSONObject(i);
                    jObject = new JSONObject(datosLugar.getString(i));
                    //Toast.makeText(this, ""+jObject.getString("NOMBRE_LUGAR") , Toast.LENGTH_SHORT).show();

                    String nombre_lugar = jObject.getString("NOMBRE_LUGAR");
                    String informacion = jObject.getString("INFORMACION");
                    String tipoLugar = jObject.getString("TIPO");
                    //salidaAUX2 = nombre_lugar+"\n"+tipoLugar+"\n"+informacion;

                    Lugar lugar = new Lugar(nombre_lugar, tipoLugar, informacion);
                    salida+=lugar.toString()+"\n";
                    i++;
                }

                tv_texto.setText(Html.fromHtml(salida));

            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }

    }

    public void imprimirLugar(){

    }

    /*public void imprimirLugar(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.mi_linear_layout);

        LinearLayout contenedor = new LinearLayout(this);
        contenedor.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        for (int j = 0; j < 100; j++ {
            Button boton = new Button(this);
            boton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            boton.setText("mi Botón " + i);
            boton.setId(i);
            contenedor.addView(boton);
        }

        layout.addView(contenedor);
    }*/

}


