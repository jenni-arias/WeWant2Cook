package arias.jenifer.wewant2cook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredients_item> IngredientsList;
    private ListView list;
    private Button btn_add;
    private String recipe_name;
    private TextView titulo_ingrediente;
    private static final String  FILENAME_CODE = "code.txt";
    public static Context context;
    private IngredientsAdapter ingredient_adapter;
    private ArrayList<Ingredients_item> lista;

   // private static final String  FILENAME_INGR = "ING.txt";
    //private static final String FILENAME_INGR = "ings.txt";
    private static final String FILENAME_INGR = "fich_ingr.txt";


    private static final int MAX_BYTES = 800000;
   // String lines_before;
    byte[] buffer_i = new byte[MAX_BYTES];
    ArrayList<String> lines_before = new ArrayList<>();

    private int pos;



   /* private void writeIngredientsList(){
        Log.i("Marta dins writeI", FILENAME_INGR.length()+"");

        try {
            FileOutputStream fos = openFileOutput(FILENAME_INGR, Context.MODE_PRIVATE);
            int num = 0;
            int l =0;


            for (int i=0; i < IngredientsList.size(); i++){
                Ingredients_item it = IngredientsList.get(i);
                String line = String.format("%s;%s;%s;%f\n", recipe_name, it.getText(), it.getUnits(),it.getNumber());

                lines_before.add(line);

                //fos.write(lines_before.getBytes());
                Log.i("Marta Què escriu", line);
            }

            for ( int pos = 0; pos < lines_before.size(); pos ++){
                fos.write(lines_before.get(pos).getBytes());
            }

            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("Marta", "writeItemList filenotfound");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Marta", "writeItemList IOEXception");
            Toast.makeText(this, R.string.cannotwrite, Toast.LENGTH_SHORT).show();
        }
    }

    private void readIngredientsList(){

        Log.i("Marta", "dins readIngreients I");
        try {
            FileInputStream fis = openFileInput(FILENAME_INGR);
            //byte[] buffer_i = new byte[MAX_BYTES];
            int nread = fis.read(buffer_i);

            if (nread>0) {
                String content = new String(buffer_i, 0, nread);
                String[] lines = content.split("\n");
              //  lines_before = content.split("\n");

                Log.i("Marta lines ", lines.length+"");
                Log.i("Marta lines", lines[0]);



                for (String line : lines) {
                    String[] parts = line.split(";");
                    Log.i("Marta Què llegeix I: ", line);
                    Log.i("Marta Part[0]", parts[0]);
                    Log.i("Marta Recipe", recipe_name);

                    for( int arraypos = 0 ; arraypos<lines_before.size(); arraypos++ ){
                        if (lines_before.get(arraypos).split(";").equals(recipe_name)){

                            addItem(parts[1],Float.valueOf(parts[3]),parts[2]);

                        }
                    }

                   /* if ( parts[0].equals(recipe_name)){
                        Log.i("Marta Recipe NAme", recipe_name + "==?" + parts[0]);

                        String[] parts2 = parts[3].split(",");

                        addItem(parts[1],Float.valueOf(parts2[0]),parts[2]);
                    }
                }
                fis.close();
            }

        } catch (FileNotFoundException e) {
            Log.i("Marta", "readItemList:  filenotfoundException");

        } catch (IOException e) {
            Log.e("Marta", "readItemList IOEXception");
            Toast.makeText(this, R.string.cannotread, Toast.LENGTH_SHORT).show();
        }

    }*/

    @Override
    protected void onStop() {
        super.onStop();
       // writeIngredientsList();
        Log.i("Marta", "onStop Ingredients");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //writeIngredientsList();

        Log.i("Marta", "onDestroy Ingredients");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        btn_add = (Button) findViewById(R.id.btn_addR);
        list = (ListView) findViewById(R.id.ingredients_list);
        titulo_ingrediente = (TextView) findViewById(R.id.titulo_ingrediente);

        IngredientsList = new ArrayList<>();

        ingredient_adapter = new IngredientsAdapter(
                this,
                android.R.layout.simple_list_item_1,
                IngredientsList
        );

        list.setAdapter(ingredient_adapter);

        Intent intent = getIntent();
        recipe_name = intent.getStringExtra("name");
        pos = intent.getIntExtra("pos",0);

        lines_before = intent.getStringArrayListExtra("item");

        //it.getLista_ingr().get(i);
       // String line = String.format("%s;%b;%s\n", it.getText(), it.isChecked(),con);

        ArrayList<String> ingN = new ArrayList<>();
        int var = 0;

        //String[] m = lines_before.get(0).split("_");

            /*Ingredients_item item = new Ingredients_item(lines_before.get(0), lines_before.get(2), Float.parseFloat(lines_before.get(1)));
            item.setUnits(lines_before.get(2));
            item.setNumber(Float.parseFloat(lines_before.get(1)));
            item.setName(lines_before.get(0));
            IngredientsList.add(item);*/



            /*Ingredients_item item = new Ingredients_item(ingN[0], ingN[2],Float.parseFloat(ingN[1]));
            item.setUnits(ingN[2]);
            item.setNumber(Float.parseFloat(ingN[1]));
            item.setName(ingN[0]);*/

//            var = var +3;





        titulo_ingrediente.setText(recipe_name);
       // readIngredientsList();




        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout lila1= new LinearLayout(context);
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(context);
                final EditText input1 = new EditText(context);
                input1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


                final EditText input2 = new EditText(context);
                final TextView text1 = new TextView(context);
                text1.setText(R.string.nombre_ingrediente);
                final TextView text2 = new TextView(context);
                text2.setText(R.string.cantidad);
                final TextView text3 = new TextView(context);
                text3.setText(R.string.unidades);
                lila1.addView(text1);
                lila1.addView(input);
                lila1.addView(text2);
                lila1.addView(input1);
                lila1.addView(text3);
                lila1.addView(input2);
                alert.setView(lila1);
                alert.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        float in2;
                        String in1 =input.getText().toString();
                        String in3 = input2.getText().toString();

                        if(input1.getText().toString().isEmpty()){
                            in2 = 0;
                        }
                        else { in2 = Float.valueOf(input1.getText().toString());;}

                        if(!in1.isEmpty()&&!in3.isEmpty()&&in2!=0) {
                            addItem(in1, in2, in3);
                            lines_before.add(in1+"_"+in2+"_"+in3);

                            dialog.cancel();
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.completar_campos, Toast.LENGTH_SHORT).show();                                }
                    }
                });
                alert.create().show();
            }

        });
    }

    public void addItem(String in1, Float in2, String in3) {
        IngredientsList.add(new Ingredients_item(in1,in3,in2));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.action_settings:
                // Anem a RecipesActivity

                int i =0;
                lista = new ArrayList<>();
                Intent data = new Intent();
                data.putExtra("name",recipe_name);

                ArrayList<String> ing = new ArrayList<String>();
                ArrayList<String> units = new ArrayList<String>();
                ArrayList<Integer> number = new ArrayList<Integer>();

                for ( i=0; i<IngredientsList.size();i++){
                    String ingname = IngredientsList.get(i).getText();
                    int num = (int) IngredientsList.get(i).getNumber();
                    String uni = IngredientsList.get(i).getUnits();

                    ing.add(ingname);
                    units.add(uni);
                    number.add(num);
                }

                data.putExtra("ingredient",ing);
                data.putExtra("number",number);
                data.putExtra("units",units);

                data.putExtra("ArrayIngredients", lines_before);

                setResult(RESULT_OK, data);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Context getAppContext(){
        return context;
    }
}
