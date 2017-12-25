package arias.jenifer.wewant2cook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView shopping_list;
    private Button btn_add;
    private EditText edit_item;

    private ArrayList<ShoppingItem> ShoppingList;
    private ShoppingListAdapter shoppinglist_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shopping_list = (ListView) findViewById(R.id.shopping_list);
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);

        ShoppingList = new ArrayList<>();
        ShoppingList.add(new ShoppingItem("Huevos", 4, "und"));
        ShoppingList.add(new ShoppingItem("Harina", 100, "gr"));
        ShoppingList.add(new ShoppingItem("Chocolate", 200, "gr"));
        ShoppingList.add(new ShoppingItem("Papel WC", 1, "und"));


        shoppinglist_adapter = new ShoppingListAdapter(
                this,
                android.R.layout.simple_list_item_1,
                ShoppingList
        );

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        shopping_list.setAdapter(shoppinglist_adapter);

    }

    private void addItem() {
        String item_text = edit_item.getText().toString();
        if(!item_text.isEmpty()) {
            ShoppingList.add(new ShoppingItem(item_text, 0, ""));
            shoppinglist_adapter.notifyDataSetChanged();
            edit_item.setText("");
        }
        shopping_list.smoothScrollToPosition(ShoppingList.size()-1);
    }
}
