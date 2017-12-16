package arias.jenifer.wewant2cook;


import android.content.SharedPreferences;

public class ShoppingItem {

    private String nombre;
    private int cantidad;
    private String unidades;

    public ShoppingItem(){
        this.nombre = "";
        this.cantidad = 0;
        this.unidades = "";
    }

    public ShoppingItem(String nombre, int cantidad, String unidades) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidades = unidades;
    }
    public ShoppingItem(String nombre){
        this.nombre = nombre;
        this.cantidad = 1;
        this.unidades = "uds";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String text) {
        this.nombre = text;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
}