package arias.jenifer.wewant2cook;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private ArrayList<Recipes_item> RecipesList;
    private ListView list;
    private Button btn_add;
    //private ArrayAdapter<String> recipes_adapter;
    private RecipesAdapter recipes_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        list = (ListView)findViewById(R.id.recipes_list);
        btn_add = (Button)findViewById(R.id.btn_add);

        RecipesList = new ArrayList<>();
        RecipesList.add(new Recipes_item ("Tortilla de patatas",true));
        RecipesList.add(new Recipes_item ("Pastel de Queso",false));
        RecipesList.add(new Recipes_item ("Brownies",false));
        RecipesList.add(new Recipes_item ("Puré de verduras",false));
        RecipesList.add(new Recipes_item ("Tortitas",false));


        /*RecipesList.add("Pastel de Queso");
        RecipesList.add("Brownies");
        RecipesList.add("Pasta a la Carbonara");
        RecipesList.add("Puré de verduras");
        RecipesList.add("Tortitas");*/

        recipes_adapter = new RecipesAdapter(     // Creamos el Adapter
                this,
                android.R.layout.simple_list_item_1,
                RecipesList
        );

        list.setAdapter(recipes_adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
               RecipesList.get(pos).toggleleChecked();
                recipes_adapter.notifyDataSetChanged();

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_r();

                Log.i("Marta","click");

            }
        });
    }

    // Enlace de RecipesActivity a IngredientsActivity

    private void add_r() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setTitle(R.string.confirm_add); // títol del builder
        builder.setMessage("Hello");


        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                RecipesList.add(new Recipes_item (input.getText().toString(),false));
                saveRecipe(input.getText().toString());
            }
        });
        builder.show();
    }

    public void saveRecipe(String n){
        Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra("name",n);
        startActivityForResult(intent,0);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if (resultCode == AppCompatActivity.RESULT_OK){
                    String Recipe = data.getStringExtra("recipe");
                    RecipesList.add(new Recipes_item (Recipe,false));
                }
        }
    }

    }
