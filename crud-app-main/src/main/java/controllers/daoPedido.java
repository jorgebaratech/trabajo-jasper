package controllers;

import models.Pedido;

public interface daoPedido {

    Pedido get(Integer id);

    void add(Pedido pe);

    void updateStatus(Integer id, String status);

    void delete(Integer id);

}
