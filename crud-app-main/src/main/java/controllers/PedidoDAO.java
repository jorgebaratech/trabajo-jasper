package controllers;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import models.Pedido;

public class PedidoDAO implements daoPedido{

    private static final String GET_QUERY = "SELECT * FROM `pedido` WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO `pedido` (`id`, `fecha`, `cliente`, `estado`, `producto`) VALUES (NULL, ?, ?, ?, ?);";
    private static final String DELETE_ID_QUERY = "DELETE FROM `pedido` WHERE id = ?";
    private static final String GETALL_QUERY = "SELECT * FROM `pedido` ORDER BY id";
    private static final String GETTODAY_QUERY = "SELECT * FROM `pedido` WHERE `fecha` LIKE ?;"; 
    private static final String GET_CLIENTE_QUERY = "SELECT COUNT(*) FROM `pedido` WHERE `cliente` LIKE ?;";
    private static final String GET_BESTCUSTOMER_QUERY = "SELECT `cliente`, COUNT(*) FROM `pedido` GROUP BY `cliente` ORDER BY COUNT(*) DESC LIMIT 1;";
    private static final String GET_EARNINGS_SUM = "SELECT SUM(`precio`) FROM `pedido` INNER JOIN `producto` ON `pedido`.`producto` = `producto`.`id`;";
    private static final String GET_LASTMONTH_COUNT = "SELECT COUNT(*) FROM `pedido` WHERE `fecha` BETWEEN DATE(NOW() - INTERVAL 1 MONTH) AND now();";
    private static final String UPDATE_QUERY = "UPDATE `pedido` SET `estado` = ? WHERE `pedido`.`id` = ?";


    private static Connection conexion = Conexion.getConexion();

    public PedidoDAO() {
    }

    public Pedido get(Integer id) {
        try (var pst = conexion.prepareStatement(GET_QUERY)) {

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                var pe = new Pedido();

                pe.setId(rs.getInt("id"));
                pe.setFecha(rs.getString("fecha"));
                pe.setCliente(rs.getString("cliente"));
                pe.setEstado(rs.getString("estado"));

                return pe;
            } else {
                System.out.println("No se ha encontrado el pedido");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null;
    }

    public void getByCliente(String cliente) {
        try (var pst = conexion.prepareStatement(GET_CLIENTE_QUERY)) {

            pst.setString(1, cliente);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                System.out.println("El cliente " + cliente + " ha realizado " + rs.getInt("COUNT(*)") + " pedidos");

            } else {
                System.out.println("No se ha encontrado el cliente");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void add(Pedido pe) {
        try (var pst = conexion.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)) {

            pst.setString(1, (pe.getFecha().toString()));
            pst.setString(2, pe.getCliente());
            pst.setString(3, pe.getEstado());
            pst.setString(4, pe.getProducto().toString());
            if (pst.executeUpdate() > 0) {

                var keys = pst.getGeneratedKeys();
                keys.next();

                pe.setId(keys.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void delete(Integer id) {

        try (var pst = conexion.prepareStatement(DELETE_ID_QUERY)) {

            pst.setInt(1, id);

            if (pst.executeUpdate() == 0) {
                System.out.println("No se ha encontrado el pedido");
            } else {
                System.out.println("Pedido eliminado con exito");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public void getByDate(String fecha) {
        try (var pst = conexion.prepareStatement(GETTODAY_QUERY)) {

            pst.setString(1, fecha);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                var pe = new Pedido();

                pe.setId(rs.getInt("id"));
                pe.setFecha(rs.getString("fecha"));
                pe.setCliente(rs.getString("cliente"));
                pe.setEstado(rs.getString("estado"));

                System.out.println(pe);

            } else {
                System.out.println("No se han encontrado pedidos con esa fecha");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public void updateStatus(Integer id, String status) {

        try (var pst = conexion.prepareStatement(UPDATE_QUERY)) {

            pst.setString(1, status);
            pst.setInt(2, id);

            if (pst.executeUpdate() == 0) {
                System.out.println("No se ha encontrado el pedido");
            } else {
                System.out.println("Pedido actualizado con exito");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void topCustomer(){
        try (var pst = conexion.prepareStatement(GET_BESTCUSTOMER_QUERY)) {

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                System.out.println("El cliente con mas pedidos ha sido " + rs.getString("cliente") + " y ha realizado un total de " + rs.getInt("COUNT(*)") + " pedidos");

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void earnings(){
        try (var pst = conexion.prepareStatement(GET_EARNINGS_SUM)) {

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                System.out.println("El total de ganancias es de " + rs.getInt("SUM(`precio`)") + "€");

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void lastMonth(){
        try (var pst = conexion.prepareStatement(GET_LASTMONTH_COUNT)) {

           
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                System.out.println("El total de pedidos del mes pasado es de " + rs.getInt("COUNT(*)"));

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
