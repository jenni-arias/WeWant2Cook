package arias.jenifer.wewant2cook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WeWant2CookActivity extends AppCompatActivity {

    private Button btn_list;
    private Button btn_recipes;
    private int code = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_want_2_cook);
        Intent recibido = getIntent();
        code = recibido.getIntExtra("code", -1);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_recipes = (Button) findViewById(R.id.btn_recipes);

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

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wewant2cookactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compartir:
                //TODO: En el EditText tiene que aparecer el código del Firebase para compartir
                Log.i ("ActionBar", "Compartir");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText input = new EditText(this);
                input.setText(String.valueOf(code));
                builder.setView(input);
                builder.setTitle("Copia el código siguiente:");
                builder.setNegativeButton("Cerrar", null);
                builder.show();
                return true;

            case R.id.action_cerrar:
                Log.i ("ActionBar", "Cerrar");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("¿Seguro que deseas cerrar la sesión?");
                builder2.setMessage("Se eliminarán todo los datos");

                builder2.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(WeWant2CookActivity.this, PreActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder2.setNegativeButton("Cancelar", null);
                builder2.show();
                
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

