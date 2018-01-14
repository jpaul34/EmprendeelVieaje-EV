package com.ev.jonathan.emprendeelvieajeev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    TextView tv_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_registro = (TextView) findViewById(R.id.tv_btnRegistrar);

        tv_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(Login.this, Registro.class);
                Login.this.startActivity(intentRegistro);
            }
        });

    }
}
