package arias.jenifer.wewant2cook;

import android.content.Context;
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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WeWant2CookActivity extends AppCompatActivity {

    private Button btn_list;
    private Button btn_recipes;
    private int code = -1;
    private static final String  FILENAME_CODE = "code.txt";
    private static final int MAX_BYTES = 80000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_want_2_cook);
        Intent recibido = getIntent();
        code = recibido.getIntExtra("code", -1);
        if(code!=-1){
            writeCode();
        }
        readCode();
        Log.i("Hugo", "Wewant2cook on create: " + code);
        if(code == -1){
            Intent intent_back = new Intent(this, PreActivity.class);
            startActivity(intent_back);
        } else {
            btn_list = (Button) findViewById(R.id.btn_list);
            btn_recipes = (Button) findViewById(R.id.btn_recipes);

            btn_recipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RecipesActivity.class);
                    if (code == -1) {
                        intent.putExtra("code", 1);
                    } else {
                        intent.putExtra("code", code);
                    }
                    startActivity(intent);
                }
            });

            btn_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
                    if (code == -1) {
                        intent.putExtra("code", 1);
                    } else {
                        intent.putExtra("code", code);
                    }
                    startActivity(intent);

                }
            });
        }

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
                        deleteFile(FILENAME_CODE);
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

    private void writeCode(){

        try {
            FileOutputStream fos = openFileOutput(FILENAME_CODE, Context.MODE_PRIVATE);

            String line = String.valueOf(code);
            fos.write(line.getBytes());
            Log.i("Marta", line);

            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("Marta", "writeCode filenotfound");
            Toast.makeText(this, "No se puede escribir", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Marta", "writeCode IOEXception");
            Toast.makeText(this, "No se puede escribir", Toast.LENGTH_SHORT).show();
        }

    }

    private void readCode(){
        try {
            FileInputStream fis = openFileInput(FILENAME_CODE);
            byte[] buffer_i = new byte[MAX_BYTES];
            int nread = fis.read(buffer_i);
            if (nread>0) {
                String content = new String(buffer_i, 0, nread);
                code = Integer.parseInt(content);
                fis.close();
            }

        } catch (FileNotFoundException e) {
            Log.i("Marta", "readCode:  filenotfoundException");

        } catch (IOException e) {
            Log.e("Marta", "readCode IOEXception");
            Toast.makeText(this, "No se puede leer", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Hugo","Wewant2cook on destroy: " +String.valueOf(code));
        writeCode();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Hugo", "onStop()");
        Log.i("Hugo","Wewant2cook on stop: " +String.valueOf(code));
        writeCode();
    }


}

