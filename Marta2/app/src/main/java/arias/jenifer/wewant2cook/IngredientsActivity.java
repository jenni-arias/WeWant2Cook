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
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredients_item> IngredientsList;
    private ListView list;
    private Button btn_add;

    public static Context context;

    private IngredientsAdapter ingredient_adapter;
    String v;


   /* private ArrayList<String> IngredientsList;
    private ArrayAdapter<String> ingredient_adapter;*/
   private static final String TAG = IngredientsActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        btn_add = (Button) findViewById(R.id.btn_add);
        list = (ListView) findViewById(R.id.ingredients_list);
        Toolbar toolbar;
        toolbar = (Toolbar)findViewById(R.id.toolbar);




        IngredientsList = new ArrayList<>();
        IngredientsList.add(new Ingredients_item ("Huevos","unidad",4));
        IngredientsList.add(new Ingredients_item ("Mantequilla","gr",100));
        IngredientsList.add(new Ingredients_item ("Harina","gr", 80));

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

                Log.e(TAG,"Paco");
                //aixó quan cliquis al boto add tenvia un missatge d'aquests que desapareixen als segons
                Toast.makeText(getBaseContext(), "Has clicat el button", Toast.LENGTH_SHORT).show();
                // Code here executes on main thread after user presses button

            }
        });
        Intent intent = getIntent();

        String recipe_name = intent.getStringExtra("name");
        toolbar.setTitle(recipe_name);











    }

    public static Context getAppContext(){
        return context;
    }


}
