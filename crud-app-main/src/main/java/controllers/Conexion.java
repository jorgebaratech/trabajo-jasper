package controllers;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Conexion {

    private static Connection conexion;

    static{
        Properties config = new Properties();
        try{

            config.load( new FileReader("config.cfg") );
            conexion = DriverManager.getConnection(config.getProperty("url"), config.getProperty("user"), config.getProperty("password"));
        } catch (SQLException | IOException ex) {
            System.out.println(ex);
        }
    }

    public static Connection getConexion() {
        return conexion;
    }
}