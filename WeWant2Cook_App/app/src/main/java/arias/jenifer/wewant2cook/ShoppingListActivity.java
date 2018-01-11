package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView shopping_list;
    private Button btn_add;
    private EditText edit_item;
    private TextView item_nombre;
    private ArrayList<ShoppingItem> ShoppingList;
    private ArrayList<ShoppingItem> ShoppingListFromRecipies;
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
        DatabaseReference databaseReference;

        shopping_list = (ListView) findViewById(R.id.shopping_list);
        btn_add = (Button) findViewById(R.id.btn_add);
        item_nombre =  (TextView) findViewById(R.id.item_nombre);

        ShoppingList = new ArrayList<>();
        ShoppingListFromRecipies = new ArrayList<>();


        Intent intent = getIntent();
        code = intent.getIntExtra("code", 1);

        shoppinglist_adapter = new ShoppingListAdapter(
                this,
                android.R.layout.simple_list_item_1,
                ShoppingList
        );

        shopping_list.setAdapter(shoppinglist_adapter);

        Intent intent2 = getIntent();
        ArrayList<String> ings  = intent2.getStringArrayListExtra("ings");
        ArrayList<Integer> nums  = intent2.getIntegerArrayListExtra("nums");
        ArrayList<String> units  = intent2.getStringArrayListExtra("units");

        if (ings != null) {
            for (int i = 0; i < ings.size(); i++) {
                String new_value = String.valueOf(nums.get(i)).concat(" ").concat(units.get(i));
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(String.valueOf(code)).
                        child(ings.get(i)).setValue(new_value);
                shoppinglist_adapter.notifyDataSetChanged();
                Log.i("ShoppingList", ings.get(0)+nums.get(0)+units.get(0));
            }
        }



        dref = FirebaseDatabase.getInstance().getReference().child(String.valueOf(code));
        mListener = dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShoppingList.add(new ShoppingItem(dataSnapshot.getKey(),
                        getCantidad(dataSnapshot.getValue().toString()),
                        getUds(dataSnapshot.getValue().toString()), shopping_list, ShoppingList,code));
                shoppinglist_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int i = getIndex(dataSnapshot.getKey());
                if(i!=-1) {
                    ShoppingList.remove(getIndex(dataSnapshot.getKey()));
                }
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

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout lila1= new LinearLayout(context);
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(context);
                final EditText input1 = new EditText(context);
                final EditText input2 = new EditText(context);
                final TextView text1 = new TextView(context);
                text1.setText("Nombre del ingrediente");
                final TextView text2 = new TextView(context);
                text2.setText("Cantidad");
                final TextView text3 = new TextView(context);
                text3.setText("Unidades");
                lila1.addView(text1);
                lila1.addView(input);
                lila1.addView(text2);
                lila1.addView(input1);
                lila1.addView(text3);
                lila1.addView(input2);
                alert.setView(lila1);
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int in2;
                                String in1 =input.getText().toString();
                                if(!input1.getText().toString().isEmpty()) {
                                    in2 = Integer.parseInt(input1.getText().toString());
                                }else{
                                    in2 = 0;
                                }
                                String in3 = input2.getText().toString();
                                if(!in1.isEmpty()&&!in3.isEmpty()&&in2!=0) {
                                    addItem(in1, in2, in3);
                                    dialog.cancel();
                                }else{
                                    String value = "Complete todos los campos";
                                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();                                }
                            }
                        });
                alert.create().show();
            }






            //addItem();
            //}
        });

    }

    private int getIndex(String s) {
        int ind = -1;
        for(int i=0; i<ShoppingList.size(); i++){
            if(ShoppingList.get(i).getNombre().equalsIgnoreCase(s)){
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

    private float getCantidad(String s) {
        float cantidad = 0;
        String[] parts = s.split(" ");

        if(parts.length>=2) {
            cantidad = Float.parseFloat(parts[0]);
        }
        return cantidad;
    }



    private void addItem(String nombre, int cant, String unids) {

        dref.child(nombre).setValue(String.valueOf(cant).concat(" ").concat(unids));
        shoppinglist_adapter.notifyDataSetChanged();
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
