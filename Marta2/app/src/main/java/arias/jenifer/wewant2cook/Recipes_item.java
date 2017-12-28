package arias.jenifer.wewant2cook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class Recipes_item {

        private String text;
        private boolean checked;


        public Recipes_item(String text, boolean checked) {
            this.text = text;
            this.checked = checked;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public void toggleleChecked() {
            this.checked=!this.checked;
        }

        static public void goIngredients(String n){
        // Anem a IngredientsActivity
        Intent intent = new Intent(RecipesActivity.getAppContext(), IngredientsActivity.class);
        intent.putExtra("name",n);
        //startActivityForResult(intent,0);
            Log.i("Marta","Funció goIngredients");
            Log.i("Marta",n);
        }
}


