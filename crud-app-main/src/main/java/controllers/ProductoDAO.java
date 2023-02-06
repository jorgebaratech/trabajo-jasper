package controllers;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Producto;

public class ProductoDAO implements daoProducto {

    private static final String GET_QUERY = "SELECT * FROM producto WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO `producto` (`id`, `nombre`, `tipo`, `precio`, `disponibilidad`) VALUES (NULL, ?, ?, ?, ?);";
    private static final String GETALL_QUERY = "SELECT * FROM producto ORDER BY id";
    private static final String DELETE_ID_QUERY = "DELETE FROM producto WHERE id = ?";
    private static final String UPDATE_QUERY = """
            UPDATE `producto` SET
                `nombre` = ?,
                `tipo` = ?,
                `precio` = ?,
                `disponibilidad` = ?
            WHERE `producto`.`id` = ?""";

    private static Connection conexion = Conexion.getConexion();

    public ProductoDAO() {
    }

    @Override
    public Producto get(Integer id) {
        try (var pst = conexion.prepareStatement(GET_QUERY)) {

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                var p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setTipo(rs.getString("tipo"));
                p.setPrecio(rs.getInt("precio"));
                p.setDisponibilidad(rs.getInt("disponibilidad"));

                return p;
            } else {
                System.out.println("No se ha encontrado el producto");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null;
    }

    @Override
    public void add(Producto p) {
        try (var pst = conexion.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)) {

            pst.setString(1, p.getNombre());
            pst.setString(2, p.getTipo());
            pst.setLong(3, p.getPrecio());
            pst.setInt(4, p.getDisponibilidad());

            if (pst.executeUpdate() > 0) {

                var keys = pst.getGeneratedKeys();
                keys.next();

                p.setId(keys.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public ArrayList<Producto> getAll() {

        var salida = new ArrayList<Producto>();

        try (var pst = conexion.prepareStatement(GETALL_QUERY)) {

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                var p = new Producto();
                
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setTipo(rs.getString("tipo"));
                p.setDisponibilidad(rs.getInt("disponibilidad"));
                p.setPrecio(rs.getInt("precio"));
                salida.add(p);

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return salida;
    }

}
