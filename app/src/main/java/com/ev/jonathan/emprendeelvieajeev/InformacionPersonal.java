package com.ev.jonathan.emprendeelvieajeev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private static String ipActualizar = "http://emprendeelviaje.cu.ma/archivosphpEV/";

    private String correoUsuario;

    private TextView tv_informacionPersonal_nombre;
    private TextView tv_informacionPersonal_apellido;
    private TextView tv_informacionPersonal_correo;
    private TextView tv_informacionPersonal_contraseña;
    private TextView tv_informacionPersonal_fecha_nacimiento;
    private TextView tv_informacionPersonal_pais;
    private TextView tv_informacionPersonal_ciudad;

    private LinearLayout ll_actualizar_datos;
    private LinearLayout ll_info;
    private TextView tv_btn_actualizardatos;

    private EditText et_new_info_nombre;
    private EditText et_new_info_apellido;
    private EditText et_new_info_contraseña;

    private Button btn_act_info_nombre;
    private Button btn_act_info_apellido;
    private Button btn_act_info_contraseña;


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

        et_new_info_nombre = (EditText) findViewById(R.id.et_actualizar_nombre);
        et_new_info_apellido = (EditText) findViewById(R.id.et_actualizar_apellido);
        et_new_info_contraseña =(EditText) findViewById(R.id.et_actualizar_contraseña);
        btn_act_info_nombre = (Button) findViewById(R.id.btn_actualizar_nombre);
        btn_act_info_apellido = (Button) findViewById(R.id.btn_actualizar_apellido);
        btn_act_info_contraseña = (Button) findViewById(R.id.btn_actualizar_contraseña);



        ll_info = (LinearLayout) findViewById(R.id.linerlayout_info);
        ll_info.setVisibility(View.INVISIBLE);

        ll_actualizar_datos = (LinearLayout) findViewById(R.id.linerlayout_actualizar_datos);
        tv_btn_actualizardatos = (TextView) findViewById(R.id.tv_btn_actualizardatos);

        ll_actualizar_datos.setVisibility(View.INVISIBLE);
        tv_btn_actualizardatos.setVisibility(View.INVISIBLE);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();
        mostrarDatosUsuario(ipUsuario+correoUsuario);

        tv_btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_actualizar_datos.setVisibility(View.VISIBLE);

            }
        });

        btn_act_info_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_new_info_apellido.getText().toString().equals("")) {
                    verificarActualizacionDatos(correoUsuario,et_new_info_nombre.getText().toString(), 1);
                }else {
                    Toast.makeText(InformacionPersonal.this, "Ingrese un Nombre para continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_act_info_apellido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_new_info_apellido.getText().toString().equals("")) {
                    verificarActualizacionDatos(correoUsuario,et_new_info_apellido.getText().toString(), 2);
                }else {
                    Toast.makeText(InformacionPersonal.this, "Ingrese un Apellido para continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_act_info_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_new_info_contraseña.getText().toString().equals("")) {
                    Hash hash = new Hash();
                    String passwordUsuario = hash.md5(et_new_info_contraseña.getText().toString() + correoUsuario);
                    String salt = hash.md5(passwordUsuario.substring((passwordUsuario.length() / 2), passwordUsuario.length()));
                    passwordUsuario = hash.sha1(passwordUsuario + salt);
                    Toast.makeText(InformacionPersonal.this, "Contraseña: " + passwordUsuario, Toast.LENGTH_SHORT).show();

                    verificarActualizacionDatos(correoUsuario, passwordUsuario, 3);
                }else {
                    Toast.makeText(InformacionPersonal.this, "Ingrese una Contraseña para Continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void mostrarDatosUsuario(String ipUsuario){
        if(!correoUsuario.equals("")){
            solicitudJASON(ipUsuario);
        }else{
            Toast.makeText(InformacionPersonal.this, "No existe información disponible", Toast.LENGTH_SHORT).show();
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
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void manejarDatos(JSONObject datosSolicitud) {
        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {

                JSONObject datosLogin = new JSONObject(datosSolicitud.getString("datos"));
                String nombre = datosLogin.getString("PRIMER_NOMBRE_USUARIO");
                String apellido = datosLogin.getString("PRIMER_APELLIDO_USUARIO");
                String correo = datosLogin.getString("CORREO");
                String fechaNacimeinto = datosLogin.getString("FECHA_NACIMIENTO");
                String pais = datosLogin.getString("CIUDAD");
                String ciudad = datosLogin.getString("PAIS");
                //String idUbicacion = datosLogin.getString("ID_UBICACION");
                ll_info.setVisibility(View.VISIBLE);
                tv_btn_actualizardatos.setVisibility(View.VISIBLE);

                tv_informacionPersonal_nombre.setText(nombre);
                tv_informacionPersonal_apellido.setText(apellido);
                tv_informacionPersonal_correo.setText(correo);
                tv_informacionPersonal_contraseña.setText("**********");
                tv_informacionPersonal_fecha_nacimiento.setText(fechaNacimeinto);
                tv_informacionPersonal_pais.setText(pais);
                tv_informacionPersonal_ciudad.setText(ciudad);
            }else{
                Toast.makeText(InformacionPersonal.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(InformacionPersonal.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
        }
    }

/*
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
*/

    /******************************************************************************************************************/

    private void verificarActualizacionDatos(String correoUser, String dato, int tipo) {
        if(true){
            verificarActualizacion(correoUser, dato, tipo);
        }
    }
    // ?nombre=a&apellido=b&correo=c&password=123&fecha=1995-03-29&pais=ecua&ciudad=quito

    public void verificarActualizacion(String correoUser, String dato, int tipo) {
        String consulta="";
        if(tipo ==1){
            consulta="actualizar_nombreUsuario.php?correo="+correoUser+"&nombre="+dato;
        }
        if(tipo ==2){
            consulta="actualizar_apellidoUsuario.php?correo="+correoUser+"&apellido="+dato;
        }
        if(tipo ==3){
            consulta="actualizar_passwordUsuario.php?correo="+correoUser+"&contraseña="+dato;
        }

        //Toast.makeText(Singup.this, ip + consulta, Toast.LENGTH_SHORT).show();
        solicitudActualizacionJASON(ipActualizar + consulta, tipo);
    }

    //Capturar datos del JSon
    public void solicitudActualizacionJASON(String URL, int tipo) {
        final int num = tipo;
        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                try {
                    verificarRegistro(datosSolicitud, num);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformacionPersonal.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    //Verifica datos capturados
    public void verificarRegistro(JSONObject datosSolicitud, int tipo) throws JSONException {
        try {
            String resultado = datosSolicitud.getString("resultado");
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

            if(tipo==1){
                et_new_info_nombre.setText("");

            }
            if(tipo==2){
                et_new_info_apellido.setText("");
            }
            if(tipo==3){
                et_new_info_contraseña.setText("");
            }

            mostrarDatosUsuario(ipUsuario+correoUsuario);

        } catch (JSONException e) {
            String resultado = datosSolicitud.getString("resultado");
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        }
    }

}
