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
    private RecipesAdapter recipes_adapter;
    public static Context context;

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

        recipes_adapter = new RecipesAdapter(
                this,
                android.R.layout.simple_list_item_1,
                RecipesList
        );

        list.setAdapter(recipes_adapter);



    }


    public void saveRecipe(String n) {
        // Anem a IngredientsActivity
        Intent intent = new Intent(RecipesActivity.getAppContext(), IngredientsActivity.class);
        intent.putExtra("name", n);
        startActivityForResult(intent, 0);
    }

    // Venim de IngredientsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    String Recipe = data.getStringExtra("name");

                    ArrayList<String> ing = new ArrayList<String>();
                    ArrayList<String> units = new ArrayList<String>();
                    ArrayList<Integer> number = new ArrayList<Integer>();

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

                        Log.i("Intent", ingredientName.get(i));
                        Log.i("Intent", ingredientNumber.get(i).toString());
                        Log.i("Intent", ingredientUnits.get(i));
                    }
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_recipes, menu);
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
        // TODO Búsqueda de recetas ( V8)
    }

    private void add_shppinglist() {

        // TODO enviarlo al Firebase -> son los arraylist: ing,  units, number (V4)
    }

    private void clearAll() {

    }

    private void clearChecked() {

    }

    public static Context getAppContext() {
        return context;
    }
}