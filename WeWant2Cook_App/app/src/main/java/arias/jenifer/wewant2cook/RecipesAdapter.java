package arias.jenifer.wewant2cook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marta on 4/12/17.
 */

public class RecipesAdapter extends ArrayAdapter<Recipes_item> {

    static Context context = RecipesActivity.getAppContext();
    Context mContext;

    public RecipesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View result = convertView;
        mContext = parent.getContext();

        if (result == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.activity_recipes_item, null);
        }

        CheckBox checkbox = (CheckBox) result.findViewById(R.id.Recipes_item);
        final Recipes_item item = getItem(position);
        checkbox.setText(item.getText());
        checkbox.setChecked(item.isChecked());

        checkbox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                saveRecipe(item.getText(), position ,item.getLista_ingr());
                return true;
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.toggleleChecked();
            }
        });
        return result;
    }

    public void saveRecipe(String n, int pos, ArrayList<String> item) {
        Intent intent = new Intent(context, IngredientsActivity.class);
        intent.putExtra("name", n);
        intent.putExtra("pos", pos);
        intent.putExtra("item", item);

        ((Activity) mContext).startActivityForResult(intent,0);
    }
}

