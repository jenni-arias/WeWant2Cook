package arias.jenifer.wewant2cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;
import com.google.firebase.*;


import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {
    private ListView shopping_list;
    private Button btn_add;
    private EditText edit_item;

    private ArrayList<ShoppingItem> ShoppingList;
    private ShoppingListAdapter shoppinglist_adapter;
    private DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shopping_list = (ListView) findViewById(R.id.shopping_list);
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);


        ShoppingList = new ArrayList<ShoppingItem>();
        /*ShoppingList.add(new ShoppingItem("Huevos", 4, "und"));
        ShoppingList.add(new ShoppingItem("Harina", 100, "gr"));
        ShoppingList.add(new ShoppingItem("Chocolate", 200, "gr"));
        ShoppingList.add(new ShoppingItem("Papel WC", 1, "und"));*/


        shoppinglist_adapter = new ShoppingListAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                ShoppingList
        );
        shopping_list.setAdapter(shoppinglist_adapter);
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Voy a crear un método para coger el "2" por un lado y el
                // "kg" por otro, también necesitaré un método para subir los valores
                // y que nos ponga en formato "2 kg" en el value del datasnapshot
                ShoppingList.add(new ShoppingItem(dataSnapshot.getKey(),
                        getCantidad(dataSnapshot.getValue().toString()),
                        getUds(dataSnapshot.getValue().toString())));
                shoppinglist_adapter.notifyDataSetChanged();
                Log.i("Hugo", dataSnapshot.getValue().toString());
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
            public void onClick(View view) {
                //Voy a hacerlo muy rudimentario, va a añadir el nombre y
                //el resto será hardcodeado

                String prueba = edit_item.getText().toString(); //"Puré 5 uds";
                String[] parts = prueba.split(" ");
                edit_item.setText("");
                if(parts.length==3) {
                    dref.child(parts[0]).setValue(parts[1].concat(" ").concat(parts[2]));
                }else{
                    dref.child("Puré").setValue("5 uds");
                }

            }
        });



    }

    /*public String getProduct(String s){
        String product = "";
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            product = parts[1];
        }
        return product;
    }*/
    public int getIndex(String s){
        int ind = -1;
        for(int i=0; i<ShoppingList.size(); i++){
            if(ShoppingList.get(i).getNombre().equals(s)){
                ind = i;
            }
        }
        return  ind;
    }

    public String getUds(String s){
        String uds = "";
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            uds = parts[1];
        }
        return uds;
    }

    public int getCantidad(String s){
        int cantidad = 0;
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            cantidad = Integer.parseInt(parts[0]);
        }
        return cantidad;
    }
}
