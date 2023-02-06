package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
    private Integer id;
    private String fecha;
    private String cliente;
    private String estado;
    private Integer producto;

}
