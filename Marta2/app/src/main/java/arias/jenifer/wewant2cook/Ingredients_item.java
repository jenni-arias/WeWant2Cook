package arias.jenifer.wewant2cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ingredients_item {
    private String ingredientNAme, units;

    private float number;

    public Ingredients_item(String ingredientNAme, String units, float number) {
        this.ingredientNAme = ingredientNAme;
        this.units = units;
        this.number = number;
    }

    public String getText() {
        return ingredientNAme;
    }
    public String getUnits() {
        return units;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number=number;
    }
    public void setUnits (String units){ this.units = units; }

    public Ingredients_item(String units) {
        this.units = units;
    }
    public Ingredients_item(float number) {
        this.number = number;
    }

}
