package com.example;
import controllers.Conexion;
import java.time.LocalDateTime;
import java.util.Scanner;

import controllers.PedidoDAO;
import controllers.ProductoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Pedido;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Menu {

    public Menu() throws JRException{
        try {
            menu();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu() throws JRException, ClassNotFoundException{
        System.out.println("Bienvenido, este es el menú de opciones: \n");
        System.out.println("1. Ver la carta" );
        System.out.println("2. Hacer un pedido");
        System.out.println("3. Eliminar un pedido");
        System.out.println("4. Marcar un pedido como recogido");
        System.out.println("5. Ver el numero de pedidos asociados a un cliente");
        System.out.println("6. Ver los pedidos realizados hoy");
        System.out.println("7. Resumen Estadístico");
        System.out.println("8. Enseñame los reportes");
        Scanner sc = new Scanner(System.in);
        int opcion = sc.nextInt();
        switch(opcion){
        case 1:
            ProductoDAO carta = new ProductoDAO();
            carta.getAll().forEach(System.out::println);
            pressEnter();
            break;
        case 2:
            PedidoDAO pedido2 = new PedidoDAO();
            Pedido pe = new Pedido();
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Introduce el nombre del cliente");
            String cliente = sc2.nextLine();
            pe.setCliente(cliente);
            System.out.println("Introduce el identificador del producto");
            Integer producto = sc2.nextInt();
            pe.setProducto(producto);
            
            pe.setFecha(LocalDateTime.now().toString());
            pe.setEstado("En camino");

            pedido2.add(pe);
            pressEnter();
            break;
        case 3:
            Scanner sc3 = new Scanner(System.in);
            PedidoDAO pedido3 = new PedidoDAO();
            System.out.println("Introduce el id del pedido");
            Integer id = sc3.nextInt();
            pedido3.delete(id);
            break;
        case 4:
            Scanner sc4 = new Scanner(System.in);
            PedidoDAO pedido4 = new PedidoDAO();
            System.out.println("Introduce el id del pedido");
            int id2 = sc4.nextInt();
            pedido4.updateStatus(id2, "Recogido");
            break;
        case 5:
            Scanner sc5 = new Scanner(System.in);
            PedidoDAO pedido5 = new PedidoDAO();
            System.out.println("Introduce el nombre del cliente");
            String cliente2 = sc5.nextLine();
            pedido5.getByCliente(cliente2);
            break;
        case 6:
            PedidoDAO pedido6 = new PedidoDAO();
            var fecha = LocalDateTime.now().toString();
            pedido6.getByDate( fecha.substring(0, fecha.indexOf("T")));
            break;
        case 7:
            PedidoDAO pedido7 = new PedidoDAO();
            pedido7.topCustomer();
            pedido7.earnings();
            pedido7.lastMonth();
            pressEnter();
            break;
          case 8:
            {
                Connection con = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/desayunos_crud?useTimezone=true&serverTimezone=UTC", "root", "");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                HashMap<String, Object> parametros = new HashMap<>();
                try {
                    JasperPrint jp = JasperFillManager.fillReport("src/main/java/reports/reporte1.jasper", parametros, Conexion.getConexion());
                    JasperViewer jv = new JasperViewer(jp, false);
                    jv.setVisible(true);
                } catch (JRException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       

            
        case 9:
            System.exit(0);
            break;
        default: menu();
        }
        menu();
    }

    public static void pressEnter() { 
		System.out.println("Presiona Enter para continuar.");
		try
		{
			System.in.read();
		}  
		catch(Exception e)
		{}  
	}
    
    
    public static void informe(String[] args) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password");

      HashMap<String, Object> parameters = new HashMap<>();

      JasperPrint jasperPrint = JasperFillManager.fillReport("reporte1.jasper", parameters, Conexion.getConexion());

      JasperViewer.viewReport(jasperPrint, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    

}
