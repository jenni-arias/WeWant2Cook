package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;
import com.google.firebase.*;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView shopping_list;
    private Button btn_add;
    private EditText edit_item;
    private TextView item_nombre;
    private ArrayList<ShoppingItem> ShoppingList;
    private ShoppingListAdapter shoppinglist_adapter;

    public static Context context;

    DatabaseReference dref;
    private ChildEventListener mListener;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shopping_list = (ListView) findViewById(R.id.shopping_list);
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);
        item_nombre =  (TextView) findViewById(R.id.item_nombre);

        ShoppingList = new ArrayList<>();
     /*   ShoppingList.add(new ShoppingItem("Huevos", 4, "und"));
        ShoppingList.add(new ShoppingItem("Harina", 100, "gr"));
        ShoppingList.add(new ShoppingItem("Chocolate", 200, "gr"));
        ShoppingList.add(new ShoppingItem("Papel WC", 1, "und")); */

        Intent intent = getIntent();
        code = intent.getIntExtra("code", 1);

        shoppinglist_adapter = new ShoppingListAdapter(
                this,
                android.R.layout.simple_list_item_1,
                ShoppingList
        );

        shopping_list.setAdapter(shoppinglist_adapter);

        dref = FirebaseDatabase.getInstance().getReference().child(String.valueOf(code));
        mListener = dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShoppingList.add(new ShoppingItem(dataSnapshot.getKey(),
                        getCantidad(dataSnapshot.getValue().toString()),
                        getUds(dataSnapshot.getValue().toString())));
                shoppinglist_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ShoppingList.remove(getIndex(dataSnapshot.getKey()));
                shoppinglist_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

    }

    private int getIndex(String s) {
        int ind = -1;
        for(int i=0; i<ShoppingList.size(); i++){
            if(ShoppingList.get(i).getNombre().equals(s)){
                ind = i;
            }
        }
        return  ind;
    }

    private String getUds(String s) {
        String uds = "";
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            uds = parts[1];
        }
        return uds;
    }

    private int getCantidad(String s) {
        int cantidad = 0;
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            cantidad = Integer.parseInt(parts[0]);
        }
        return cantidad;
    }

    /*
    private void addItem() {
        String item_text = edit_item.getText().toString();
        if(!item_text.isEmpty()) {
            ShoppingList.add(new ShoppingItem(item_text, 0, ""));
            shoppinglist_adapter.notifyDataSetChanged();
            edit_item.setText("");
        }
        shopping_list.smoothScrollToPosition(ShoppingList.size()-1);
    } */

    private void addItem() {
        String item_text = edit_item.getText().toString(); //"Puré 5 und
        String[] parts = item_text.split(" ");
        edit_item.setText("");

        if (parts.length == 3) {
            dref.child(parts[0]).setValue(parts[1].concat(" ").concat(parts[2]));
        }
        else {
            Toast.makeText(context, "Añade en formato correcto: Ingrediente cantidad unidades",
                    Toast.LENGTH_SHORT).show();
        }
     //   shopping_list.smoothScrollToPosition(ShoppingList.size()-1);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dref.removeEventListener(mListener);
    }

}
