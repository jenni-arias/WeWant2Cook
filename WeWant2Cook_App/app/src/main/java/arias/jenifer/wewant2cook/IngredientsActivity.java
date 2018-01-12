package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.ConditionVariable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO poder editar el títol del ActionBar amb el nom de la recipe seleccionada

public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredients_item> IngredientsList;
    private ListView list;
    private Button btn_add;
    private String recipe_name;

    public static Context context;

    private IngredientsAdapter ingredient_adapter;

    private ArrayList<Ingredients_item> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        btn_add = (Button) findViewById(R.id.btn_add);
        list = (ListView) findViewById(R.id.ingredients_list);

        IngredientsList = new ArrayList<>();

        ingredient_adapter = new IngredientsAdapter(
                this,
                android.R.layout.simple_list_item_1,
                IngredientsList
        );

        list.setAdapter(ingredient_adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Marta", "btn_add");

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout lila1= new LinearLayout(context);
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(context);
                final EditText input1 = new EditText(context);
                final EditText input2 = new EditText(context);
                final TextView text1 = new TextView(context);
                text1.setText("Nombre del ingrediente");
                final TextView text2 = new TextView(context);
                text2.setText("Cantidad");
                final TextView text3 = new TextView(context);
                text3.setText("Unidades");
                lila1.addView(text1);
                lila1.addView(input);
                lila1.addView(text2);
                lila1.addView(input1);
                lila1.addView(text3);
                lila1.addView(input2);
                alert.setView(lila1);
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int in2;
                                String in1 =input.getText().toString();
                                if(!input1.getText().toString().isEmpty()) {
                                    in2 = Integer.parseInt(input1.getText().toString());
                                }else{
                                    in2 = 0;
                                }
                                String in3 = input2.getText().toString();
                                if(!in1.isEmpty()&&!in3.isEmpty()&&in2!=0) {
                                    addItem(in1, in2, in3);
                                    dialog.cancel();
                                }else{
                                    String value = "Complete todos los campos";
                                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();                                }
                            }

                        });
                alert.create().show();
            }
        });

        Intent intent = getIntent();
        recipe_name = intent.getStringExtra("name");
    }

    private void addItem(String in1, int in2, String in3) {
        IngredientsList.add(new Ingredients_item(in1,in3,in2));
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
