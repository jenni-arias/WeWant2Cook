package arias.jenifer.wewant2cook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PreActivity extends AppCompatActivity {

    Button btn_nueva_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        btn_nueva_lista = (Button) findViewById(R.id.btn_nueva_lista);

        btn_nueva_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
