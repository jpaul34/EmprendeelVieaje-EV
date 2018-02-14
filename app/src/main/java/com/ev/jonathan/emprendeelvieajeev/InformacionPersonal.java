package com.ev.jonathan.emprendeelvieajeev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    }
}
