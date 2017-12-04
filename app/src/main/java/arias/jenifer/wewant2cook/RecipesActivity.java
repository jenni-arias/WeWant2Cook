package arias.jenifer.wewant2cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private ArrayList<String> RecipesList;
    private ListView list;
    private Button btn_add;
    private ArrayAdapter<String> recipes_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        list = (ListView)findViewById(R.id.recipes_list);
        btn_add = (Button)findViewById(R.id.btn_add);

        RecipesList = new ArrayList<>();
        RecipesList.add("Toritlla de patatas");
        RecipesList.add("Pastel de Queso");
        RecipesList.add("Brownies");
        RecipesList.add("Pasta a la Carbonara");
        RecipesList.add("Puré de verduras");
        RecipesList.add("Tortitas");

        recipes_adapter = new ArrayAdapter<String>(     // Creamos el Adapter
                this,
                android.R.layout.simple_list_item_1,
                RecipesList
        );

        list.setAdapter(recipes_adapter);


    }
}
