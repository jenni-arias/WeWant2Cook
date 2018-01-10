package arias.jenifer.wewant2cook;


import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingItem {

    private String nombre;
    private float cantidad;
    private String unidades;
    private int code;
    private ListView list;
    private ArrayList<ShoppingItem> ShoppingList;
    private boolean borrar;
    private boolean editar;

    public ShoppingItem(){

    }

    public ShoppingItem(String nombre, float cantidad, String unidades, ListView list,
                        ArrayList<ShoppingItem> ShoppingList, int code) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidades = unidades;
        this.list = list;
        this.ShoppingList = ShoppingList;
        this.code = code;
        this.borrar = false;
        this.editar = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String text) {
        this.nombre = text;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public ListView getList() {
        return list;
    }

    public void setList (ListView list) {
        this.list = list;
    }

    public ArrayList<ShoppingItem> getArray() {
        return ShoppingList;
    }

    public void setArray (ArrayList<ShoppingItem> ShoppingList) {
        this.ShoppingList = ShoppingList;
    }

    public int getCode() {
        return code;
    }

    public void setCode (int code) {
        this.code = code;
    }

    public boolean getBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean getEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
