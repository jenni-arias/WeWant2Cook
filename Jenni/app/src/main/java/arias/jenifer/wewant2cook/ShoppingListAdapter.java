package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import static java.lang.Float.parseFloat;

/**
 * Created by j.arias.gallego on 09/12/2017.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {


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
        final EditText edit_cantidad = result.findViewById(R.id.edit_cantidad);
        final EditText edit_unidades = result.findViewById(R.id.edit_unidades);
        final TextView item_nombre = result.findViewById(R.id.item_nombre);
        final ShoppingItem item = getItem(position);

        item_nombre.setText(item.getNombre());
        edit_cantidad.setText(Float.toString(item.getCantidad()));
        edit_unidades.setText(item.getUnidades());

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
                String uni = (edit_unidades.getText().toString());
                item.setUnidades(uni);
                return true;
            }
        });


      item_nombre.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              Log.e("jenn","longclick clicado");
              AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.getAppContext());

              builder.setMessage("Seguro que quieres eliminar este ingrediente?");
              builder.setCancelable(true);

              builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      remove(item);
  //                    shoppinglist_adapter.notifyDataSetChanged();
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
   /*     shopping_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                Log.i("jenn", "click");
                maybeRemoveItem(pos);
                return true;
            }
        });*/

 /*   private void maybeRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        String fmt = "Seguro que quieres eliminar este ingrediente?";

        builder.setMessage(String.format(fmt, ShoppingList.get(pos).getNombre()));

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingList.remove(pos);
                shoppinglist_adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }*/