package arias.jenifer.wewant2cook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public static Context context;
    int textlength = 0;
    private String listview_array[];//= {"Huevos"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        list = (ListView) findViewById(R.id.recipes_list);
        btn_add = (Button) findViewById(R.id.btn_add);

        RecipesList = new ArrayList<>();
        RecipesList.add(new Recipes_item("Tortilla de patatas", true));
        RecipesList.add(new Recipes_item("Pastel de Queso", false));
        RecipesList.add(new Recipes_item("Brownies", false));
        RecipesList.add(new Recipes_item("Puré de verduras", false));
        RecipesList.add(new Recipes_item("Tortitas", false));


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

        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                RecipesList.get(pos).toggleleChecked();
                recipes_adapter.notifyDataSetChanged();
                Log.i("Menu", RecipesList.get(pos).isChecked()+"");


            }
        });*/

       /* list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.i("Marta", "longclick FET");
                String NameRecipe = RecipesList.get(pos).getText();
                saveRecipe(NameRecipe);
                recipes_adapter.notifyDataSetChanged();


                return true;
            }
        });*/



        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_r();

                Log.i("Marta", "click");

            }
        });



    }

    private void add_r() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setTitle(R.string.confirm_add); // títol del builder
        //builder.setMessage("Hello");


        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                RecipesList.add(new Recipes_item(input.getText().toString(), false));
                Log.i("Marta", input.getText().toString());

                saveRecipe(input.getText().toString());
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    public void saveRecipe(String n) {
        // Anem a IngredientsActivity
        Intent intent = new Intent(RecipesActivity.getAppContext(), IngredientsActivity.class);
        intent.putExtra("name", n);
        startActivityForResult(intent, 0);
        //startActivity(intent);

    }

    // Venim de IngredientsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    // TODO rebre els ingredients i guardar-los a un String
                    String Recipe = data.getStringExtra("name");

                    ArrayList<String> ing = new ArrayList<String>();
                    ArrayList<String> units = new ArrayList<String>();
                    ArrayList<Integer> number = new ArrayList<Integer>();


                    //lista = (ArrayList<Recipes_item>) data.getSerializableExtra("ingredient");

                    RecipesList.add(new Recipes_item(Recipe, false));

                    ArrayList<String> ingredientName = data.getStringArrayListExtra("ingredient");
                    ArrayList<Integer> ingredientNumber = data.getIntegerArrayListExtra("number");
                    ArrayList<String> ingredientUnits = data.getStringArrayListExtra("units");


                    for (int i = 0; i < ingredientName.size(); i++) {

                        String ingname = ingredientName.get(i);
                        Integer num = ingredientNumber.get(i);
                        String uni = ingredientUnits.get(i);

                        ing.add(ingname);
                        units.add(uni);
                        number.add(num);

                        //Log.i("Intent",i+"");
                        //Log.i("Intent",ing.size()+"");
                        Log.i("Intent", ingredientName.get(i));
                        Log.i("Intent", ingredientNumber.get(i).toString());
                        Log.i("Intent", ingredientUnits.get(i));


                    }
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // objecto para rellenar
        MenuInflater inflater = getMenuInflater(); // inflador de menús
        inflater.inflate(R.menu.options_recipes, menu); //  ar partir de ése recurso, mételo en menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.clear_cheched:
                clearChecked();
                Log.i("Menu", "Clear checked");
                return true;
            case R.id.clear_all:
                clearAll();
                Log.i("Menu", "Clear all");
                return true;
            case R.id.add_shoppinglist:
                add_shppinglist();
                Log.i("Menu", "Add to shoppinglist");
                return true;
            case R.id.action_buscar:
                SearchRecipe();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SearchRecipe() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setTitle(R.string.search_recipe);


        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO Búsqueda de recetas

                        }
            });


        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();


    }

    private void add_shppinglist() {

        // TODO enviarlo al Firebase -> son los arraylist: ing,  units, number (V4)
    }




    private void clearAll() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_clear_all);
        builder.setPositiveButton(R.string.clear_all, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RecipesList.clear();
                recipes_adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void clearChecked() {
        int i = 0;
        while (i < RecipesList.size()) {
            Log.i("Menu", RecipesList.size()+"" );
            if (RecipesList.get(i).isChecked()) {
                Log.i("Menu", RecipesList.get(i).isChecked()+","+RecipesList.get(i).getText() );
                RecipesList.remove(i);


            } else {
                Log.i("Menu", RecipesList.get(i).isChecked()+","+RecipesList.get(i).getText() );
                i++;
            }
        }
        recipes_adapter.notifyDataSetChanged();
    }

    public static Context getAppContext() {
        return context;
    }
}