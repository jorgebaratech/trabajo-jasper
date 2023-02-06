package controllers;

import java.util.ArrayList;

import models.Producto;

public interface daoProducto {
    
    Producto get(Integer id);
    
    void add(Producto p);
    
    ArrayList<Producto> getAll();
    
}
