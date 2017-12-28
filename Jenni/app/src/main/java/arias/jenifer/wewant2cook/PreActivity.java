package arias.jenifer.wewant2cook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PreActivity extends AppCompatActivity {

    Button btn_nueva_lista;
    Button btn_unirse_lista;
    RelativeLayout Layout_unirse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        btn_nueva_lista = (Button) findViewById(R.id.btn_nueva_lista);
        btn_unirse_lista = (Button) findViewById(R.id.btn_unirse_lista);
        Layout_unirse = (RelativeLayout) findViewById(R.id.Layout_unirse);

        Layout_unirse.setVisibility(View.INVISIBLE);

        btn_nueva_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WeWant2CookActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btn_unirse_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Layout_unirse.setVisibility(View.VISIBLE);
            }
        });
    }
}
