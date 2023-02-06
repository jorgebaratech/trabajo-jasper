package models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {

    private Integer id;
    private String nombre;
    private String tipo;
    private Integer precio;
    private Integer disponibilidad;

    @Override
    public String toString() {
        return  id + "  " + nombre + "  " + "precio = " + precio + "    " +tipo + " disponibilidad = " + disponibilidad;
    }
}
