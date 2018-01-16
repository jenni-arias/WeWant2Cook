package arias.jenifer.wewant2cook;

import java.util.ArrayList;


public class Recipes_item {

        private String text;
        private boolean checked;
        private ArrayList<String> lista_ingr;


        public Recipes_item(String text, boolean checked,ArrayList<String> array) {
            this.text = text;
            this.checked = checked;
            this.lista_ingr = array;
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


        public ArrayList<String> getLista_ingr() {
        return lista_ingr;
        }

        public void setLista_ingr(ArrayList<String> lista_ingr) {
            this.lista_ingr = lista_ingr;
        }
}


