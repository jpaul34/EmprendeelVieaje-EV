package com.ev.jonathan.emprendeelvieajeev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Login extends AppCompatActivity {

    private static String ip = "http://emprendeelviaje.cu.ma/archivosphpEV/usuario_GETCORREO.php?correo=";

    TextView tv_registro;
    Button btn_ingresar;
    EditText et_correo;
    EditText et_password;
    private  String correoUsuario;
    private  String passwordUsuario;
    private RequestQueue mRequest;
    private VolleyRP volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        tv_registro = (TextView) findViewById(R.id.tv_btnRegistrar);

        et_correo = (EditText) findViewById(R.id.et_loginCorreo);
        et_password = (EditText) findViewById(R.id.et_loginContrañse);


        btn_ingresar = (Button) findViewById(R.id.btn_loginEntrar);


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!et_password.getText().toString().equals("")){
                    Hash hash = new Hash();
                    passwordUsuario = hash.md5(et_password.getText().toString()+et_correo.getText().toString());
                    String salt = hash.md5(passwordUsuario.substring((passwordUsuario.length()/2), passwordUsuario.length()));
                    passwordUsuario = hash.sha1(passwordUsuario+salt);
                    //et_correo.setText(passwordUsuario);
                    //Toast.makeText(Login.this, "Contraseña: "+et_password.getText().toString()+"\n"+passwordUsuario, Toast.LENGTH_SHORT).show();
                    verificarLogin(et_correo.getText().toString().toLowerCase(), passwordUsuario);

                }else {
                    Toast.makeText(Login.this, "Ingrese la Contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void verificarLogin(String correo, String password) {
        correoUsuario = correo;
        passwordUsuario = password;
        // Toast.makeText(this,"El correo es:"+correoUsuario+" y la Contraseña es:" +passwordUsuario,Toast.LENGTH_SHORT).show();
        solicitudJASON(ip + correo);
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
                Toast.makeText(Login.this, "Ocurrio un error, conprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
        String soli = solicitud.toString();
        //¿Toast.makeText(Login.this,solicitud.getMethod()+" --- "+URL+" --- " +soli, Toast.LENGTH_SHORT).show();
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void verificarDatosLogin(JSONObject datosSolicitud) {
        //Toast.makeText(Login.this, "Los datos son:"+datosSolicitud.toString(), Toast.LENGTH_SHORT).show();

        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {
                JSONObject datosLogin = new JSONObject(datosSolicitud.getString("datos"));
                String correo = datosLogin.getString("CORREO");
                String password = datosLogin.getString("PASSWORD");
                //Toast.makeText(Login.this, " "+password+" --> "+passwordUsuario, Toast.LENGTH_SHORT).show();

                if (correo.equals(correoUsuario) && password.equals(passwordUsuario)) {
                    Toast.makeText(this, "Usuario y contraseña correctos", Toast.LENGTH_SHORT).show();
                    //Intent intentInicio = new Intent(Login.this, Ubicacion.class);
                    Intent intentInicio = new Intent(Login.this, Inicio.class);
                    intentInicio.putExtra("correoUsuario", correoUsuario);
                    Login.this.startActivity(intentInicio);
                } else {
                    Toast.makeText(this, "Contraseña incorrecta verifique su correo y contraseña", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }
}

