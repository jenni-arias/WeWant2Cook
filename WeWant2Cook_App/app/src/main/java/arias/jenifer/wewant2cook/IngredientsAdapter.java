package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.Float.parseFloat;

/**
 * Created by Marta on 14/12/17.
 */

public class IngredientsAdapter extends ArrayAdapter<Ingredients_item> {

    public static Context context;


    public IngredientsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View result = convertView;
        if (result == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.activity_ingredients_item, null);
        }

        final EditText num = (EditText)result.findViewById(R.id.edit_number);
       // num.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final EditText units = (EditText) result.findViewById(R.id.edit_units);
        final TextView ingredient_name = (TextView)result.findViewById(R.id.Ingr_name);

        final Ingredients_item item = getItem(position);

        ingredient_name.setText(item.getText());
        units.setText(item.getUnits());
        num.setText((Float.toString(item.getNumber())));

        num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int pos, KeyEvent keyEvent) {
                Log.e("Marta","funciona clicat num");

                try {
                    float n = parseFloat(num.getText().toString());
                    item.setNumber(n);
                }catch (NumberFormatException nfe){
                    Toast.makeText(getContext(), R.string.completar_bien_campos, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        } );

        units.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.e("Marta","funciona clicat units");
                String u = (units.getText().toString());
                item.setUnits(u);
                return true;
            }
        });

        ingredient_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("Marta","funciona clicat el textview");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(IngredientsActivity.getAppContext());
                // TODO: Traducir el builder...
                builder1.setMessage(R.string.seguro_eliminar2); //ingredient_name.getText().toString())
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.si,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                remove(item);
                            }
                        });

                builder1.setNegativeButton(
                        R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });

        return result;
    }


}