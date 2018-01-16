package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

// TODO poder editar el títol del ActionBar amb el nom de la recipe seleccionada

public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredients_item> IngredientsList;
    private ListView list;
    private Button btn_add;
    private String recipe_name;
    private static final String  FILENAME_CODE = "code.txt";

    public static Context context;

    private IngredientsAdapter ingredient_adapter;

    private ArrayList<Ingredients_item> lista;

    private static final String  FILENAME_INGR = "ING.txt";



    private static final int MAX_BYTES = 80000;

    private void writeIngredientsList(){

        try {
            FileOutputStream fos = openFileOutput(FILENAME_INGR, Context.MODE_PRIVATE);
            for (int i=0; i < IngredientsList.size(); i++){
                Ingredients_item it = IngredientsList.get(i);
                String line = String.format("%s;%s;%s;%5f\n", recipe_name, it.getText(), it.getUnits(),it.getNumber());
                fos.write(line.getBytes());
                Log.i("Marta Què escriu", line);
            }
            fos.close();



        } catch (FileNotFoundException e) {
            Log.e("Marta", "writeItemList filenotfound");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Marta", "writeItemList IOEXception");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        }

    }

    private void readIngredientsList(){
        //  IngredientsList = new ArrayList<>();
        Log.i("Marta", "dins readIngreients I");
        try {
            FileInputStream fis = openFileInput(FILENAME_INGR);
            byte[] buffer_i = new byte[MAX_BYTES];
            int nread = fis.read(buffer_i);

            if (nread>0) {
                String content = new String(buffer_i, 0, nread);
                String[] lines = content.split("\n");

                for (String line : lines) {

                    String[] parts = line.split(";");
                    Log.i("Marta Què llegeix I: ", line);

                    if ( parts[0].equals(recipe_name)){
                        Log.i("Marta Recipe NAme", recipe_name + "==?" + parts[0]);

                        String[] parts2 = parts[3].split(",");

                        addItem(parts[1],Float.valueOf(parts2[0]),parts[2]);
                    }

                }
                fis.close();
            }

        } catch (FileNotFoundException e) {
            Log.i("Marta", "readItemList:  filenotfoundException");

        } catch (IOException e) {
            Log.e("Marta", "readItemList IOEXception");
            Toast.makeText(this, R.string.cannotread, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        writeIngredientsList();
        Log.i("Marta", "onStop Ingredients");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //writeIngredientsList();

        Log.i("Marta", "onDestroy Ingredients");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        btn_add = (Button) findViewById(R.id.btn_addR);
        list = (ListView) findViewById(R.id.ingredients_list);

        IngredientsList = new ArrayList<>();

        ingredient_adapter = new IngredientsAdapter(
                this,
                android.R.layout.simple_list_item_1,
                IngredientsList
        );

        list.setAdapter(ingredient_adapter);

        // IngredientsList.add(new Ingredients_item("Pa","Barres",2));

        Intent intent = getIntent();
        recipe_name = intent.getStringExtra("name");
        readIngredientsList();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("Marta", "btn_add");

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout lila1= new LinearLayout(context);
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(context);
                final EditText input1 = new EditText(context);
                final EditText input2 = new EditText(context);
                final TextView text1 = new TextView(context);
                text1.setText(R.string.nombre_ingrediente);
                final TextView text2 = new TextView(context);
                text2.setText(R.string.cantidad);
                final TextView text3 = new TextView(context);
                text3.setText(R.string.unidades);
                lila1.addView(text1);
                lila1.addView(input);
                lila1.addView(text2);
                lila1.addView(input1);
                lila1.addView(text3);
                lila1.addView(input2);
                alert.setView(lila1);
                alert.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        float in2;
                        String in1 =input.getText().toString();
                        String in3 = input2.getText().toString();

                        if(input1.getText().toString().isEmpty()){
                            in2 = 0;
                        }
                        else { in2 = Float.valueOf(input1.getText().toString());;}

                        if(!in1.isEmpty()&&!in3.isEmpty()&&in2!=0) {
                            addItem(in1, in2, in3);
                            Log.i("Marta Dins el if", "addItem if");
                            dialog.cancel();
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.completar_campos, Toast.LENGTH_SHORT).show();                                }
                    }
                });
                alert.create().show();
            }
            //addItem();
            //}
        });

    }




    public void addItem(String in1, Float in2, String in3) {


        IngredientsList.add(new Ingredients_item(in1,in3,in2));

        Log.i("Marta AddItem", IngredientsList.get(0).getText());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.action_settings:
                // Anem a RecipesActivity

                int i =0;
                lista = new ArrayList<>();
                Intent data = new Intent();
                data.putExtra("name",recipe_name);

                ArrayList<String> ing = new ArrayList<String>();
                ArrayList<String> units = new ArrayList<String>();
                ArrayList<Integer> number = new ArrayList<Integer>();

                for ( i=0; i<IngredientsList.size();i++){

                    String ingname = IngredientsList.get(i).getText();
                    int num = (int) IngredientsList.get(i).getNumber();
                    String uni = IngredientsList.get(i).getUnits();

                    ing.add(ingname);
                    units.add(uni);
                    number.add(num);
                }

                data.putExtra("ingredient",ing);
                data.putExtra("number",number);
                data.putExtra("units",units);

                setResult(RESULT_OK, data);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Context getAppContext(){
        return context;
    }
}
