package com.ev.jonathan.emprendeelvieajeev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    Button btn_informacion;
    Button btn_ubicacion;
    Button btn_lugares_turisticos;
    Button btn_acercade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btn_informacion = (Button) findViewById(R.id.btn_inicio_info);
        btn_ubicacion = (Button) findViewById(R.id.btn_inicio_ubi);
        btn_lugares_turisticos = (Button) findViewById(R.id.btn_inicio_lugarturis);
        btn_acercade = (Button) findViewById(R.id.btn_ini_acercade);

        btn_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInformacion = new Intent(Inicio.this, InformacionPersonal.class);
                Inicio.this.startActivity(intentInformacion);
            }
        });

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(Inicio.this, Mapa.class);
                Inicio.this.startActivity(intentMapa);
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

}
