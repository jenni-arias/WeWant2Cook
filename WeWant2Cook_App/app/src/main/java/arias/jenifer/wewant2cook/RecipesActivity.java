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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RecipesActivity extends AppCompatActivity {

    private ArrayList<Recipes_item> RecipesList;
    private ListView list;
    private Button btn_add;
    private RecipesAdapter recipes_adapter;
    public static Context context;


    ArrayList<String> ing = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    ArrayList<Integer> number = new ArrayList<Integer>();

    ArrayList<String> IngredientsRecipies = new ArrayList<>();

    DatabaseReference dref;
    private ChildEventListener mListener;
    private int code;


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

        //RecipesList.add(new Recipes_item(Recipe, false));





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


                        String concat = Recipe+";"+ingname+";"+num+";"+uni;
                        // TODO que no es repateixin les receptes
                        //IngredientsRecipies.add(concat);
                        IngredientsRecipies.add(concat);

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

                int mm;
                int jj;
                for ( mm = 0; mm<RecipesList.size(); mm++) {

                    Log.i("Menu1", RecipesList.size() + "");
                    //while (jj < IngredientsRecipies.size()){
                    for (jj = 0; jj < IngredientsRecipies.size(); jj++) {
                        Log.i("Menu2", jj + "");

                        String[] name = IngredientsRecipies.get(jj).split(";");
                        //Log.i("Menu2.1", name[jj] );
                        Log.i("Menu2.1", RecipesList.get(mm).getText());


                        if (RecipesList.get(mm).getText().equals(name[jj])) {
                            Log.i("Menu3", name[jj]);

                            if (RecipesList.get(mm).isChecked()) {

                                add_shppinglist();
                                Log.i("Menu4", RecipesList.get(mm).getText());

                            } else {
                                Log.i("Menu", "noChecked");
                                mm++;
                            }

                        }

                    }

                }



                recipes_adapter.notifyDataSetChanged();

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

                Intent intent2 = new Intent(RecipesActivity.getAppContext(), ShoppingListActivity.class);
                intent2.putExtra("ings", ing);
                intent2.putExtra("nums", number);
                intent2.putExtra("units", units);
                startActivity(intent2);



                Log.i("Recipes", ing.get(0));
            }


    private void clearAll() {

    }

    private void clearChecked() {

    }

    public static Context getAppContext() {
        return context;
    }
}