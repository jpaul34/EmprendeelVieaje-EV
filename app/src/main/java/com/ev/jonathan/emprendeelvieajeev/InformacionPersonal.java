package com.ev.jonathan.emprendeelvieajeev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class InformacionPersonal extends AppCompatActivity {

    private static String ipUsuario = "http://emprendeelviaje.cu.ma/archivosphpEV/usuario_GETCORREO.php?correo=";
    private static String ipUbicacion = "http://emprendeelviaje.cu.ma/archivosphpEV/ubicacion_GETID.php?id_ubicacion=";
    private String correoUsuario;

    private TextView tv_informacionPersonal_nombre;
    private TextView tv_informacionPersonal_apellido;
    private TextView tv_informacionPersonal_correo;
    private TextView tv_informacionPersonal_contraseña;
    private TextView tv_informacionPersonal_fecha_nacimiento;
    private TextView tv_informacionPersonal_pais;
    private TextView tv_informacionPersonal_ciudad;

    private RequestQueue mRequest;
    private VolleyRP volley;
    //private Location localizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_personal);
        correoUsuario = getIntent().getExtras().getString("correoUsuario");

        tv_informacionPersonal_nombre = (TextView) findViewById(R.id.tv_informacionPersonal_nombre);
        tv_informacionPersonal_apellido = (TextView) findViewById(R.id.tv_informacionPersonal_apellido);
        tv_informacionPersonal_correo =  (TextView) findViewById(R.id.tv_informacionPersonal_correo);
        tv_informacionPersonal_contraseña = (TextView) findViewById(R.id.tv_informacionPersonal_contraseña);
        tv_informacionPersonal_fecha_nacimiento = (TextView) findViewById(R.id.tv_informacionPersonal_fecha_nacimiento);
        tv_informacionPersonal_pais =  (TextView) findViewById(R.id.tv_informacionPersonal_pais);
        tv_informacionPersonal_ciudad =  (TextView) findViewById(R.id.tv_informacionPersonal_ciudad);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();
        mostrarDatosUsuario(ipUsuario+correoUsuario);

    }

    public void mostrarDatosUsuario(String ipUsuario){
        if(!correoUsuario.equals("")){
            solicitudJASON(ipUsuario);

        }else{
            /*tv_nom.setVisibility(View.INVISIBLE);
            tv_nom.setText("No existe información disponible");
            tv_nombre_lugar.setVisibility(View.INVISIBLE);
            TextView tv_info = (TextView) findViewById(R.id.tv_lugarturistico_info);;
            tv_info.setVisibility(View.INVISIBLE);
            tv_informacion_lugar.setVisibility(View.INVISIBLE);
            TextView tv_tipo = (TextView) findViewById(R.id.tv_lugarturistico_tipo);;
            tv_tipo.setVisibility(View.INVISIBLE);
            tv_tipo_lugar.setVisibility(View.INVISIBLE);*/
        }
    }

    public void solicitudJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                manejarDatos(datosSolicitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformacionPersonal.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void manejarDatos(JSONObject datosSolicitud) {
        //Toast.makeText(Login.this, "Los datos son:"+datosSolicitud.toString(), Toast.LENGTH_SHORT).show();

        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {

                JSONObject datosLogin = new JSONObject(datosSolicitud.getString("datos"));
                String idUsuario = datosLogin.getString("ID_USUARIO");
                String nombre = datosLogin.getString("PRIMER_NOMBRE_USUARIO");
                String apellido = datosLogin.getString("PRIMER_APELLIDO_USUARIO");
                String correo = datosLogin.getString("CORREO");
                String contraseña = datosLogin.getString("PASSWORD");
                String fechaNacimeinto = datosLogin.getString("FECHA_NACIMIENTO");
                String idUbicacion = datosLogin.getString("ID_UBICACION");

                tv_informacionPersonal_nombre.setText(nombre);
                tv_informacionPersonal_apellido.setText(apellido);
                tv_informacionPersonal_correo.setText(correo);
                tv_informacionPersonal_contraseña.setText("**********");
                tv_informacionPersonal_fecha_nacimiento.setText(fechaNacimeinto);

                solicitudUbicacioJASON(ipUbicacion+idUbicacion);

            }
        } catch (JSONException e) {
        }

    }


    public void solicitudUbicacioJASON(String URL) {
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                manejarDatosUbicacion(datosSolicitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformacionPersonal.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void manejarDatosUbicacion(JSONObject datosSolicitud) {
        //Toast.makeText(Login.this, "Los datos son:"+datosSolicitud.toString(), Toast.LENGTH_SHORT).show();

        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {

                JSONObject datosLogin = new JSONObject(datosSolicitud.getString("datos"));

                String pais = datosLogin.getString("CIUDAD");
                String ciudad = datosLogin.getString("PAIS");
                tv_informacionPersonal_pais.setText(pais);
                tv_informacionPersonal_ciudad.setText(ciudad);
            }
        } catch (JSONException e) {
        }

    }

}
