package arias.jenifer.wewant2cook;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.provider.FirebaseInitProvider;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PreActivity extends AppCompatActivity {

    private Button btn_nueva_lista;
    private Button btn_unirse_lista;
    private Button btn_unirse;
    private EditText user_code;
    private RelativeLayout Layout_unirse;
    private DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        btn_nueva_lista = (Button) findViewById(R.id.btn_nueva_lista);
        btn_unirse_lista = (Button) findViewById(R.id.btn_unirse_lista);
        btn_unirse = (Button) findViewById(R.id.btn_unirse);
        Layout_unirse = (RelativeLayout) findViewById(R.id.Layout_unirse);
        user_code = (EditText) findViewById(R.id.codigo_unirse);

        Layout_unirse.setVisibility(View.INVISIBLE);


        btn_nueva_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(v.getContext(), WeWant2CookActivity.class);
                //final int new_code = 0;
                Log.i("Hugo", "Entra");
                dref = FirebaseDatabase.getInstance().getReference();
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int code =0;
                        if (snapshot.hasChild(String.valueOf(code))) {
                            do{
                                int min = 0;
                                int max = 5000;

                                Random r = new Random();
                                code = r.nextInt(max - min + 1) + min;
                            }

                            while(snapshot.hasChild(String.valueOf(code)));
                        } intent.putExtra("code",code);
                        startActivityForResult(intent,0);
                    } @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("Hugo", databaseError.toString());
                    }
                });



               /* do{
                    int min = 0;
                    int max = 5000;

                    Random r = new Random();
                    new_code = r.nextInt(max - min + 1) + min;
                }

                while(new_code<100);*/
                /*intent.putExtra("code", new_code);
                startActivityForResult(intent, 0);*/
            }
        });

        btn_unirse_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Layout_unirse.setVisibility(View.VISIBLE);

                btn_unirse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: Al introducir el código y clicar el botón "Unirse" tiene que aparecer WeWant2CookActivity de la lista compartida.
                        Intent intent = new Intent(v.getContext(),WeWant2CookActivity.class);
                        String s = user_code.getText().toString();
                        intent.putExtra("code",Integer.valueOf(s));
                        startActivityForResult(intent, 0);
                    }
                });

            }
        });
    }
}