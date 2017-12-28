package arias.jenifer.wewant2cook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class WeWant2CookActivity extends AppCompatActivity {

    private Button btn_list;
    private Button btn_recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_want_2_cook);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_recipes = (Button) findViewById(R.id.btn_recipes);


        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wewant2cookactivity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compartir:
                Log.i ("ActionBar", "Compartir!");
                return true;
            case R.id.action_cerrar:
                Log.i ("ActionBar", "Cerrar!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

