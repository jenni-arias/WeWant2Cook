package arias.jenifer.wewant2cook;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WeWant2CookActivity extends AppCompatActivity {
//ID DEL PROYECTO: wewant2cook
    private Button btn_list;
    private Button btn_recipes;
    private int code = -1;
    private final Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_want_2_cook);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_recipes = (Button) findViewById(R.id.btn_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        onCreateOptionsMenu(toolbar.getMenu());
        Intent recibido = getIntent();
        code = recibido.getIntExtra("code", -1);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingList.class);
                if(code==-1) {
                    intent.putExtra("code", 1);
                }else{
                    intent.putExtra("code", code);
                }
                startActivity(intent);
            }
        });
        btn_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipesActivity.class);
                if(code==-1) {
                    intent.putExtra("code", 1);
                }else{
                    intent.putExtra("code", code);
                }
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:


                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.android_user_input_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                TextView codigo = (TextView) mView.findViewById(R.id.userInputDialog);
                codigo.setText(String.valueOf(code));
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setNegativeButton("Got it",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                return true;
            case R.id.action_cerrar:
                DatabaseReference dref = FirebaseDatabase.getInstance()
                        .getReference().child(String.valueOf(code));
                Intent intent = new Intent(this, PreActivity.class);
                startActivity(intent);
                finish();
                Log.i("Hugo", "Pulsado cerrar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
