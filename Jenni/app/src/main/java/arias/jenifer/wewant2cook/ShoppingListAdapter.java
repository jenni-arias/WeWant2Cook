package arias.jenifer.wewant2cook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

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
        EditText edit_cantidad = result.findViewById(R.id.edit_cantidad);
        EditText edit_unidades = result.findViewById(R.id.edit_unidades);
        TextView item_nombre = result.findViewById(R.id.item_nombre);

        ShoppingItem item = getItem(position);
        item_nombre.setText(item.getNombre());
        edit_cantidad.setText(String.format("%d", item.getCantidad()));
        edit_unidades.setText(item.getUnidades());

        return result;
    }
}
