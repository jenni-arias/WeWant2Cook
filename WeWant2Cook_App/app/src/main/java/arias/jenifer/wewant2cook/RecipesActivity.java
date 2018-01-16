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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class RecipesActivity extends AppCompatActivity {

    private ArrayList<Recipes_item> RecipesList;
    private ListView list;
    private Button btn_addR;
    private RecipesAdapter recipes_adapter;
    public static Context context;
    private static final String  FILENAME_CODE = "code.txt";

    ArrayList<String> ing = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    ArrayList<Integer> number = new ArrayList<Integer>();
    ArrayList<String> ArrayIng = new ArrayList<>();

    private ArrayList<String> IngredientsRecipies = new ArrayList<>();


    DatabaseReference dref;
    private ChildEventListener mListener;
    private int code;

    private static final String  FILENAME_RECP = "fich_Reci.txt";
    private static final int MAX_BYTES = 80000;

    private void writeRecipesList(){

        try {
            FileOutputStream fos = openFileOutput(FILENAME_RECP, Context.MODE_PRIVATE);
            for (int i=0; i < RecipesList.size(); i++){
                Recipes_item it = RecipesList.get(i);
                String con = it.getLista_ingr().toString();
                for (int y =1; y < it.getLista_ingr().size(); y ++){
                    con = con +"_"+ it.getLista_ingr();

                }
                //it.getLista_ingr().get(i);
                String line = String.format("%s;%b;%s\n", it.getText(), it.isChecked(),con);
               // Log.i("Marta què escriu R", line);
                fos.write(line.getBytes());
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

    private void readRecipesList(){
        RecipesList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILENAME_RECP);
            byte[] buffer = new byte[MAX_BYTES];
            int nread = fis.read(buffer);
            if (nread>0) {
                String content = new String(buffer, 0, nread);
                String[] lines = content.split("\n");
                for (String line : lines) {
                    String[] parts = line.split(";");
                    ArrayList<String> array = new ArrayList<String>();
                    String c = parts[2];
                    String[] xx =c.split("_");
                    for(int var = 0; var < xx.length; var ++){
                        array.add(xx[var]);
                    }

                    RecipesList.add(new Recipes_item(parts[0], parts[1].equals("true"),array));
                   // Log.i("Marta Què llegeix R", line);
                }
                fis.close();
            }

        } catch (FileNotFoundException e) {
            Log.i("Marta", "readItemList:  filenotfoundException");

        } catch (IOException e) {
            Log.e("Marta", "readItemList IOEXception");
            Toast.makeText(this, R.string.cannotread , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       // Log.i("Marta", "onStop() Recipes");
        writeCode();
        writeRecipesList();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Intent received_intent = getIntent();
        code = received_intent.getIntExtra("code", -1);

        list = (ListView) findViewById(R.id.recipes_list);
        btn_addR = (Button) findViewById(R.id.btn_addR);

        RecipesList = new ArrayList<>();
        readRecipesList();

        recipes_adapter = new RecipesAdapter(
                this,
                android.R.layout.simple_list_item_1,
                RecipesList
        );
        list.setAdapter(recipes_adapter);

        btn_addR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_r();

            }
        });
    }
    private Recipes_item itemR;

    private void add_r() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setTitle(R.string.confirm_add);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String in = input.getText().toString();

                if (!in.isEmpty()){
                    ArrayList<String> array = new ArrayList<String>();
                    //Recipes_item item = new Recipes_item(in, false,  array);
                    itemR = new Recipes_item(in, false,  array);
                    RecipesList.add(itemR);
                    saveRecipe(input.getText().toString(), itemR);
                    recipes_adapter.notifyDataSetChanged();
                    list.smoothScrollToPosition(RecipesList.size()-1);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.completar_campos, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton(R.string.cancelar, null);
        builder.show();
    }

    public void saveRecipe(String n, Recipes_item item) {
        // Anem a IngredientsActivity
        Log.i("Marta", "SaveRecipe()");

        Intent intent = new Intent(RecipesActivity.getAppContext(), IngredientsActivity.class);
        intent.putExtra("name", n);
        intent.putExtra("code", code);
        intent.putExtra("ArrayIngredients", ArrayIng);
        intent.putExtra("item", item.getLista_ingr());

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
                    ArrayList<String> ArrayIng = data.getStringArrayListExtra("ArrayIngredients");

                    for (int i = 0; i < ingredientName.size(); i++) {

                        String ingname = ingredientName.get(i);
                        Integer num = ingredientNumber.get(i);
                        String uni = ingredientUnits.get(i);

                        int var = 0;
                        ArrayList<String> lista = new ArrayList<>();


                            String[] ingN = ArrayIng.get(i).split("_");

                           /* Ingredients_item item = new Ingredients_item(ingN[0], ingN[2],Float.parseFloat(ingN[1]));
                            item.setUnits(ingN[2]);
                            item.setNumber(Float.parseFloat(ingN[1]));
                            item.setName(ingN[0]);

                           // lista.add(item);
//                            var = var +3;*/



                        itemR.setLista_ingr(ArrayIng);


                        ing.add(ingname);
                        units.add(uni);
                        number.add(num);

                        String concat = Recipe+";"+ingname+";"+num+";"+uni;
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
                Log.i("Menu", "Clear checked");
                return true;
            case R.id.clear_all:
                clearAll();
                Log.i("Menu", "Clear all");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setTitle(R.string.confirm_add);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String in = input.getText().toString();
                for ( int pos = 0 ; pos < RecipesList.size(); pos ++ ){
                    if (in.equals(RecipesList.get(pos).getText()) ){
                        list.smoothScrollToPosition(pos);
                    }
                }
            }
        });

        builder.setNegativeButton(R.string.cancelar, null);
        builder.show();
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
        builder.setNegativeButton(R.string.cancelar, null);
        builder.create().show();
    }

    private void clearChecked() {
        int i = 0;
        while (i < RecipesList.size()) {
            if (RecipesList.get(i).isChecked()) {
                RecipesList.remove(i);
            } else {
                i++;
            }
        }
        recipes_adapter.notifyDataSetChanged();
    }

    public static Context getAppContext() {
        return context;
    }

    private void writeCode(){

        try {
            FileOutputStream fos = openFileOutput(FILENAME_CODE, Context.MODE_PRIVATE);

            String line = String.valueOf(code);
            fos.write(line.getBytes());
            Log.i("Marta", line);
            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("Marta", "writeCode filenotfound");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Marta", "writeCode IOEXception");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeCode();
        writeRecipesList();
       // Log.i("Marta", "onDestroy Recipes");
    }
}