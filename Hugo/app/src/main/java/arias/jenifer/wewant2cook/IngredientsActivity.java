package arias.jenifer.wewant2cook;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// TODO poder editar el títol del ActionBar amb el nom de la recipe seleccionada
//HECHO



public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredients_item> IngredientsList;
    private ListView list;
    private Button btn_add;
    private String recipe_name;
    private String[] ingredientes_array;

    public static Context context;

    private IngredientsAdapter ingredient_adapter;
    String v;
    private ArrayList<Ingredients_item> lista;


   /* private ArrayList<String> IngredientsList;
    private ArrayAdapter<String> ingredient_adapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        btn_add = (Button) findViewById(R.id.btn_add);
        list = (ListView) findViewById(R.id.ingredients_list);
        //Toolbar toolbar;
        //toolbar = (Toolbar)findViewById(R.id.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        onCreateOptionsMenu(toolbar.getMenu());
        /*ActionBar actionBar = getActionBar();
        actionBar.setSubtitle("mytest");
        actionBar.setTitle("vogella.com");
        */

        Intent intent_name = getIntent();
        toolbar.setTitle(intent_name.getStringExtra("name"));
        IngredientsList = new ArrayList<>();
        IngredientsList.add(new Ingredients_item("Huevos", "unidad", 4));
        IngredientsList.add(new Ingredients_item("Mantequilla", "gr", 100));
        IngredientsList.add(new Ingredients_item("Harina", "gr", 80));

        /*IngredientsList = new ArrayList<>();
        IngredientsList.add("Huevos");
        IngredientsList.add("Harina");
        IngredientsList.add("Mantequilla");*/

        ingredient_adapter = new IngredientsAdapter(     // Creamos el Adapter
                this,
                android.R.layout.simple_list_item_1,
                IngredientsList
        );

        list.setAdapter(ingredient_adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent();
                data.putExtra("name",recipe_name);
                Bundle bundle = new Bundle();
                int i =0;
                ingredientes_array = new String[IngredientsList.size()];
                while (i<IngredientsList.size()){
                    // Log.i("Intent",IngredientsList.get(i).getText());
                    Log.i("Hugo",String.valueOf(IngredientsList.size()));
                    //lista.add(new Ingredients_item(IngredientsList.get(i).getText(),IngredientsList.get(i).getUnits(), IngredientsList.get(i).getNumber()));
                    //bundle.putSerializable("ingredient", IngredientsList.get(i).getText());
                    //bundle.putSerializable("ingredient", IngredientsList);
                    // Log.i("Hugo", IngredientsList.get(i).getUnits());
                    String text = IngredientsList.get(i).getText();
                    String number = String.valueOf(IngredientsList.get(i).getNumber());
                    String units = IngredientsList.get(i).getUnits();
                    ingredientes_array[i] = text.concat(" ").concat(number).concat(" ").concat(units);
                    //data.putExtras(bundle);
                    //Log.i("Hugo", ingredientes_array[i]);

                    i+=1;

                }

                bundle.putStringArray("ingredientes", ingredientes_array);
                data.putExtra("size", IngredientsList.size());
                // data.putExtras(bundle);
                //setResult(RESULT_OK, data);
                setResult(Activity.RESULT_OK, data);
                //ArrayList<Spanned> passwords = search.getResult();



                // data.putExtra("ingredient",(Serializable)IngredientsList);
                //setResult(RESULT_OK, data);
                finish();

               // Log.i("Hugo", "btn_add pasa");

                //Toast.makeText(getBaseContext(), "Has clicat el button", Toast.LENGTH_SHORT).show();


            }
        });
        // Venim de RecipesActivity
        //Intent intent = getIntent();
        //recipe_name = intent.getStringExtra("name");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // objecto para rellenar
        MenuInflater inflater = getMenuInflater(); // inflador de menús
        inflater.inflate(R.menu.options,menu); //  ar partir de ése recurso, mételo en menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.action_settings:
                // Anem a RecipesActivity
                int i =0;
                //lista = new ArrayList<>();
                Intent data = new Intent();
                data.putExtra("name",recipe_name);
                Bundle bundle = new Bundle();

                ingredientes_array = new String[IngredientsList.size()];
                while (i<IngredientsList.size()){
                   // Log.i("Intent",IngredientsList.get(i).getText());
                    Log.i("Hugo",String.valueOf(IngredientsList.size()));
                    //lista.add(new Ingredients_item(IngredientsList.get(i).getText(),IngredientsList.get(i).getUnits(), IngredientsList.get(i).getNumber()));
                    //bundle.putSerializable("ingredient", IngredientsList.get(i).getText());
                    //bundle.putSerializable("ingredient", IngredientsList);
                   // Log.i("Hugo", IngredientsList.get(i).getUnits());
                   String text = IngredientsList.get(i).getText();
                    String number = String.valueOf(IngredientsList.get(i).getNumber());
                    String units = IngredientsList.get(i).getUnits();
                    ingredientes_array[i] = text.concat(" ").concat(number).concat(" ").concat(units);
                    //data.putExtras(bundle);
                    //Log.i("Hugo", ingredientes_array[i]);

                    i+=1;

                }

                bundle.putStringArray("ingredientes", ingredientes_array);
                data.putExtra("size", IngredientsList.size());
                // data.putExtras(bundle);
                //setResult(RESULT_OK, data);
                setResult(Activity.RESULT_OK, data);
                //ArrayList<Spanned> passwords = search.getResult();



                // data.putExtra("ingredient",(Serializable)IngredientsList);
                //setResult(RESULT_OK, data);
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