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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        IngredientsList.add(new Ingredients_item("Huevos", "unidad", 4));
        IngredientsList.add(new Ingredients_item("Mantequilla", "gr", 100));
        IngredientsList.add(new Ingredients_item("Harina", "gr", 80));



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

                Toast.makeText(getBaseContext(), "Has clicat el button", Toast.LENGTH_SHORT).show();


            }
        });

        Intent intent = getIntent();
        recipe_name = intent.getStringExtra("name");
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
