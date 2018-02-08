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
    private static String ip = "http://www.hosttest.byethost8.com/archivosphp/Login_GETID.php?correo=";
    TextView tv_registro;
    Button btn_ingresar;
    EditText et_correo;
    EditText et_password;
    String correoUsuario;
    String passwordUsuario;
    private RequestQueue mRequest;
    private VolleyRP volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        tv_registro = (TextView) findViewById(R.id.tv_btnRegistrar);

        tv_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(Login.this, Signup.class);
                Login.this.startActivity(intentRegistro);
            }
        });

        et_correo = (EditText) findViewById(R.id.et_loginCorreo);
        et_password = (EditText) findViewById(R.id.et_loginContrañse);


        btn_ingresar = (Button) findViewById(R.id.btn_loginEntrar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarLogin(et_correo.getText().toString().toLowerCase(), et_password.getText().toString().toLowerCase());
            }
        });
    }

    public void verificarLogin(String correo, String password) {
        correoUsuario = correo;
        passwordUsuario = password;

        solicitudJASON(ip + correo);
    }

    public void solicitudJASON(String URL) {
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
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
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void verificarDatosLogin(JSONObject datosSolicitud) {
        try {
            String resultado = datosSolicitud.getString("resultado");
            if (resultado.equals("CC")) {
                JSONObject datosLogin = new JSONObject(datosSolicitud.getString("datos"));
                String correo = datosLogin.getString("correo");
                String password = datosLogin.getString("password");
                if (correo.equals(correoUsuario) && password.equals(passwordUsuario)) {
                    Toast.makeText(this, "Usuario y contraseña correctos", Toast.LENGTH_SHORT).show();
                    Intent intentInicio = new Intent(Login.this, Inicio.class);
                    Login.this.startActivity(intentInicio);
                } else {
                    Toast.makeText(this, "Contraserrecta  su correo y contraseña", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

}
