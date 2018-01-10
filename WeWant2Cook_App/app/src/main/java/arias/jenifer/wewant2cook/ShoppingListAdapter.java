package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;
import static java.lang.Float.parseFloat;

/**
 * Created by h.delanieta.marin on 12/12/2017.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {
    DatabaseReference databaseReference;

    public ShoppingListAdapter (Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;

        if(result == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.activity_shopping_item, null);
        }
        final EditText edit_cantidad = (EditText) result.findViewById(R.id.edit_cantidad);
        final EditText edit_unidades = (EditText) result.findViewById(R.id.edit_unidades);
        final TextView item_nombre = (TextView) result.findViewById(R.id.item_nombre);
        final ShoppingItem item = getItem(position);

        item_nombre.setText(item.getNombre());
        edit_cantidad.setText(Float.toString(item.getCantidad()));
        edit_unidades.setText(item.getUnidades());






        //////////////////////

        edit_cantidad.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int pos, KeyEvent event) {
                float cant = parseFloat(edit_cantidad.getText().toString());
                item.setCantidad(cant);
                return true;
            }
        });

        edit_unidades.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("Hugo", "Pasa por el editor");
                String uni = (edit_unidades.getText().toString());
                item.setUnidades(uni);


                String new_value = String.valueOf(item.getCantidad()).concat(" ").concat(uni);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(String.valueOf(item.getCode())).
                        child(item.getNombre()).setValue(new_value);

                notifyDataSetChanged();
                return true;
            }
        });





        //////////////////




        edit_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!item.getBorrar()) {
                    if((item.getEditar())) {
                        String uni = (edit_cantidad.getText().toString());
                        Log.i("Hugo", item.getNombre());
                        item.setCantidad(Float.parseFloat(uni));
                        item.setEditar(false);
                        String new_value = String.valueOf(uni).concat(" ").concat(item.getUnidades());
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child(String.valueOf(item.getCode())).
                                child(item.getNombre()).setValue(new_value);
                    }
                }
            }
        });




        edit_unidades.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!item.getBorrar()) {
                    if((item.getEditar())) {
                        String uni = (edit_unidades.getText().toString());
                        item.setUnidades(uni);
                        item.setEditar(false);
                        String new_value = String.valueOf(item.getCantidad()).concat(" ").concat(uni);
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child(String.valueOf(item.getCode())).
                                child(item.getNombre()).setValue(new_value);
                    }
                }

            }
        });

        item_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setEditar(true);
            }
        });


        item_nombre.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("jenn","longclick clicado");
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.getAppContext());

                builder.setMessage("¿Seguro que quieres eliminar este ingrediente?");
                builder.setCancelable(true);

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.setBorrar(true);
                        item.setEditar(true);
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child(String.valueOf(item.getCode())).
                               child(item.getNombre()).setValue(null);
                        remove(item);
                        notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        return result;
    }

}
