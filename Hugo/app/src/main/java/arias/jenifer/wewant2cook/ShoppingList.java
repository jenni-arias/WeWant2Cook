package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;
import com.google.firebase.*;


import java.util.ArrayList;
import java.util.Random;

public class ShoppingList extends AppCompatActivity {
    private ListView shopping_list;
    private Button btn_add;
    private EditText edit_item;
    private TextView item_nombre;
    public static Context context;

    private ArrayList<ShoppingItem> ShoppingList;
    private ShoppingListAdapter shoppinglist_adapter;
    private DatabaseReference dref;
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



        ShoppingList = new ArrayList<ShoppingItem>();


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
                //Voy a crear un método para coger el "2" por un lado y el
                // "kg" por otro, también necesitaré un método para subir los valores
                // y que nos ponga en formato "2 kg" en el value del datasnapshot
                //if(!isAlreadyInList(s, dataSnapshot)) {
                    ShoppingList.add(new ShoppingItem(dataSnapshot.getKey(),
                            getCantidad(dataSnapshot.getValue().toString()),
                            getUds(dataSnapshot.getValue().toString())));
                    shoppinglist_adapter.notifyDataSetChanged();
                    Log.i("Hugo", dataSnapshot.getValue().toString());
                //}else{
                    //)  Log.i("Hugo", "Ya estaba en la lista");
                Log.i("Hugo", "Escuchador puesto");
                //}
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
                    if (parts.length == 3) {
                        dref.child(parts[0]).setValue(parts[1].concat(" ").concat(parts[2]));
                    } else {
                        dref.child("Puré").setValue("5 uds");
                    }

            }
        });


        /*
        *
        * btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }


        });*/



    }

    /*public String getProduct(String s){
        String product = "";
        String[] parts = s.split(" ");
        if(parts.length>=2) {
            product = parts[1];
        }
        return product;
    }*/

    //Con este método pretendo comprobar si existe ya ese ingrediente
    //si existe habría que sumar la cantidad a la ya existente y actualizar el nodo
    //en cuestión
    //TODO PROBAR QUE FUNCIONA
    public boolean isAlreadyInList(String s, DataSnapshot data){
        boolean already = false;
        Query node = dref.child(s);
        if(node != null){
            already = true;
            //String value = dref.child(s).setValue()
            //DataSnapshot data = new DataSnapshot(dref, dref.child(s));
            String datos_s = data.getValue().toString();
            //En vez de 10 debería estar `puesta la nueva cantidad que introduzcamos
            int cantidad = getCantidad(datos_s) + 10;
            String unidades = getUds(data.getValue().toString());
            String new_value = String.valueOf(cantidad).concat(" ").concat(unidades);
            dref.child(s).setValue(new_value);

        }


        return already;
    }

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

    public static Context getAppContext() {
        return context;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dref.removeEventListener(mListener);
        Log.i("Hugo", "Escuchador destruido");
    }
}
