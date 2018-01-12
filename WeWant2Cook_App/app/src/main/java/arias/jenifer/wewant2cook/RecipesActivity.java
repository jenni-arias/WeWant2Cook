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

    private ArrayList<String> IngredientsRecipies = new ArrayList<>();


    DatabaseReference dref;
    private ChildEventListener mListener;
    private int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Intent received_intent = getIntent();
        code = received_intent.getIntExtra("code", -1);

        list = (ListView) findViewById(R.id.recipes_list);
        btn_add = (Button) findViewById(R.id.btn_add);

        RecipesList = new ArrayList<>();

        recipes_adapter = new RecipesAdapter(
                this,
                android.R.layout.simple_list_item_1,
                RecipesList
        );

        list.setAdapter(recipes_adapter);

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

        builder.setTitle(R.string.confirm_add);

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
    }

    // Venim de IngredientsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    String Recipe = data.getStringExtra("name");
                    Log.i("Marta onActivityResult",Recipe); // això ho fa be!

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

                        IngredientsRecipies.add(concat);

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
                Log.i("Marta", "Clear checked");
                return true;
            case R.id.clear_all:
                clearAll();
                Log.i("Marta", "Clear all");
                return true;
            case R.id.add_shoppinglist:

                int mm;
                int jj;

                    for (jj = 0; jj < IngredientsRecipies.size(); jj++) {
                        for ( mm = 0; mm<RecipesList.size(); mm++) {


                        String[] name = IngredientsRecipies.get(jj).split(";");

                        if (RecipesList.get(mm).getText().equals(name[0])) {

                            if (RecipesList.get(mm).isChecked()) {

                                    add_shppinglist(name[1].toString(), name[2], name[3]);
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

    private void add_shppinglist(String ingre, String num, String unidades) {

       float num_f = Float.parseFloat(num);

        String cad = ingre.concat(";").concat(num).concat(";").concat(unidades);


        // S'ha de borrar els intents

                Intent intent2 = new Intent(RecipesActivity.getAppContext(), ShoppingListActivity.class);

                intent2.putExtra("ings", ingre);
                intent2.putExtra("nums", num);
                intent2.putExtra("units", unidades);

        dref = FirebaseDatabase.getInstance().getReference();

        dref.child(String.valueOf(code)).child(ingre).setValue(num.concat(" ").concat(unidades));

                Log.i("Recipes", ing.get(0));
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
            Log.i("Marta", RecipesList.size()+"" );
            if (RecipesList.get(i).isChecked()) {
                Log.i("Marta", RecipesList.get(i).isChecked()+","+RecipesList.get(i).getText() );
                RecipesList.remove(i);


            } else {
                Log.i("Marta", RecipesList.get(i).isChecked()+","+RecipesList.get(i).getText() );
                i++;
            }
        }
        recipes_adapter.notifyDataSetChanged();

    }

    public static Context getAppContext() {
        return context;
    }
}