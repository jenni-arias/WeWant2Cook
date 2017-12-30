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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RecipesActivity extends AppCompatActivity {

    private ArrayList<Recipes_item> RecipesList;
    private ListView list;
    private Button btn_add;
    //private ArrayAdapter<String> recipes_adapter;
    private RecipesAdapter recipes_adapter;
    public static Context context;
    private ArrayList<Recipes_item> lista;
    private int code = -1;
    private DatabaseReference dref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Intent received_intent = getIntent();
        code = received_intent.getIntExtra("code", -1);
        Log.i("Hugo", String.valueOf(code));

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        onCreateOptionsMenu(toolbar.getMenu());

        list = (ListView) findViewById(R.id.recipes_list);
        btn_add = (Button) findViewById(R.id.btn_add);

        RecipesList = new ArrayList<>();
        RecipesList.add(new Recipes_item("Tortilla de patatas", true));
        RecipesList.add(new Recipes_item("Pastel de Queso", false));
        RecipesList.add(new Recipes_item("Brownies", false));
        RecipesList.add(new Recipes_item("Puré de verduras", false));
        RecipesList.add(new Recipes_item("Tortitas", false));

        dref = FirebaseDatabase.getInstance().getReference().child(String.valueOf(code));
        //for(int i =0; i<RecipesList.size(); i++){
           // String value = RecipesList.get(i).getNumber()
           // dref.child(RecipesList.get(i).getText()).setValue()
            dref.child("Patatas").setValue("2 kg");
       // }



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

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
               /* Log.i("Marta", "longclick FET");
                String NameRecipe = RecipesList.get(pos).getText();
                saveRecipe(NameRecipe);
                recipes_adapter.notifyDataSetChanged();*/
                Intent intent = new Intent(RecipesActivity.this, IngredientsActivity.class);
                intent.putExtra("name", RecipesList.get(pos).getText());
                startActivityForResult(intent, 0);
                return true;
                }
        });

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

                RecipesList.add(new Recipes_item (input.getText().toString(),false));
                Log.i("Marta",input.getText().toString());
                saveRecipe(input.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    public void saveRecipe(String n){
        // Anem a IngredientsActivity
        Intent intent = new Intent(RecipesActivity.this, IngredientsActivity.class);
        intent.putExtra("name",n);
        startActivityForResult(intent, 0);
    }



    // Venim de IngredientsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Hugo","Pasa");

       switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    // TODO rebre els ingredients i guardar-los a un String
                    String Recipe = data.getStringExtra("name");
                    //String Ingredients = data.getStringExtra("ingredient");
                    Log.i("Hugo","Pasa");
                    //Esto no sé si es bundle o ingredient
                    Bundle bundle = data.getBundleExtra("bundle");
                    int size = data.getIntExtra("size", -1);
                    String[] ingredientes_array = new String[size];
                    ingredientes_array = bundle.getStringArray("ingredient");

                    for(int i =0; i<size; i++){
                        String parts[] = ingredientes_array[i].split(" ");
                        dref.child(parts[0]).setValue(parts[1].concat(parts[2]));

                    }

                    Log.i("Hugo", "size " + size);
                    //lista = (ArrayList<Recipes_item>) data.getSerializableExtra("ingredient");
                    for(int i =0; i<lista.size();i++){
                       // int num = lista.get(i).
                    }

                    //lista = (ArrayList<Recipes_item>) data.getSerializableExtra("ingredient");

                    RecipesList.add(new Recipes_item (Recipe,false));
                    //VOY A SUPONER QUE EL INTENT FUNCIONA AUNQUE NO LO HAGA



                    Log.i("Tornada",Recipe);
                    Log.i("Tornada", lista.size()+"");

                    //Log.i("Print Items Count", lista.size()+"");


                }
        }
    }
    public static Context getAppContext(){
        return context;
    }

    //TODO EL MÉTODO ONOPTIONSITEMSELECTED, ESTÁ EN LA CLASE INGREDIENTS YA IMPLEMENTADO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // objecto para rellenar
        MenuInflater inflater = getMenuInflater(); // inflador de menús
        inflater.inflate(R.menu.options,menu); //  ar partir de ése recurso, mételo en menu
        return true;
    }






}