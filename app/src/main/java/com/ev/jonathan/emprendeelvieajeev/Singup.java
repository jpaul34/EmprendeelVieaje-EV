package com.ev.jonathan.emprendeelvieajeev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Singup extends AppCompatActivity {

    private static String ip = "http://emprendeelviaje.cu.ma/archivosphpEV/registrar_Usuario.php";
    private Button btn_registrar;
    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_correo;
    private EditText et_password;
    private EditText et_fecha;
    private EditText et_pais;
    private EditText et_ciudad;
    private  String passwordUsuario;
    private RequestQueue mRequest;
    private VolleyRP volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();
        et_nombre = (EditText) findViewById(R.id.et_regNombre);
        et_apellido = (EditText) findViewById(R.id.et_regApellido);
        et_correo = (EditText) findViewById(R.id.et_regCorreo);
        et_password = (EditText) findViewById(R.id.et_regContraseña);
        et_fecha = (EditText) findViewById(R.id.et_regFechaNacimiento);
        et_pais = (EditText) findViewById(R.id.et_regPais);
        et_ciudad = (EditText) findViewById(R.id.et_regCiudad);
        btn_registrar = (Button) findViewById(R.id.btn_regRegistrarse);
       btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !et_nombre.getText().toString().equals("") && !et_apellido.getText().toString().equals("") &&
                        !et_correo.getText().toString().equals("") && !et_password.getText().toString().equals("") &&
                        !et_fecha.getText().toString().equals("")  &&
                        !et_pais.getText().toString().equals("") && !et_ciudad.getText().toString().equals("")){
                    Hash hash = new Hash();
                    passwordUsuario = hash.md5(et_password.getText().toString()+et_correo.getText().toString());
                    String salt = hash.md5(passwordUsuario.substring((passwordUsuario.length()/2), passwordUsuario.length()));
                    passwordUsuario = hash.sha1(passwordUsuario+salt);
                                        verificarDatos( et_nombre.getText().toString(), et_apellido.getText().toString(),
                            et_correo.getText().toString(), passwordUsuario, et_fecha.getText().toString(),
                            et_pais.getText().toString(), et_ciudad.getText().toString());
                }else {
                    Toast.makeText(Singup.this, "Ingrese todos los campos para el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verificarDatos(String nombreUser, String apellidoUser, String correoUser, String passwordUser, String fechaNacimientoUser, String paisUser, String ciudadUsr) {
        if(true){
            verificarRegistro(nombreUser, apellidoUser, correoUser, passwordUser, fechaNacimientoUser, paisUser, ciudadUsr);
        }
    }
    // ?nombre=a&apellido=b&correo=c&password=123&fecha=1995-03-29&pais=ecua&ciudad=quito

    public void verificarRegistro(String nombreUser, String apellidoUser, String correoUser, String passwordUser, String fechaNacimientoUser, String paisUser, String ciudadUsr) {
        String consulta="";
        consulta="?nombre="+nombreUser+"&apellido="+apellidoUser+"&correo="+correoUser+"&password="+passwordUser+
                "&fecha="+fechaNacimientoUser+"&pais="+paisUser+"&ciudad="+ciudadUsr;
        //Toast.makeText(Singup.this, ip + consulta, Toast.LENGTH_SHORT).show();

        solicitudJASON(ip + consulta);
    }

    //Capturar datos del JSon
    public void solicitudJASON(String URL) {

        final JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datosSolicitud) {
                //Toast.makeText(Singup.this, "Entro "+URL, Toast.LENGTH_SHORT).show();
                try {
                    verificarRegistro(datosSolicitud);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Singup.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    //Verifica datos capturados
    public void verificarRegistro(JSONObject datosSolicitud) throws JSONException {
        try {
            String resultado = datosSolicitud.getString("resultado");
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            et_nombre.setText("");
            et_apellido.setText("");
            et_correo.setText("");
            et_password.setText("");
            et_fecha.setText("");
            et_pais.setText("");
            et_ciudad.setText("");

        } catch (JSONException e) {
            String resultado = datosSolicitud.getString("resultado");
            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

        }
    }



}
