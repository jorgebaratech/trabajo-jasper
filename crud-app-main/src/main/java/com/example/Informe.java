/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

import controllers.Conexion;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author FranciscoRomeroGuill
 */
public class Informe {
 
    public static void showReport() throws JRException, ClassNotFoundException, SQLException {
 
        HashMap hm = new HashMap();
        
        // descarga dentro del mismo proyecto
        //hm.put("TITLE", "Informe "+new java.util.Date().toString());

        String reporte1 = "informe.jasper";
        //JasperReport report = JasperCompileManager.compileReport(fileName);

        
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte1, hm, Conexion.getConexion());
        
        //JasperPrint jasperPrint = JasperFillManager.fillReport(fileName, hm, Conexion.conectar());
        
        JRViewer viewer = new JRViewer(jasperPrint);
 
        JFrame frame = new JFrame("reporte1.jrxml");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);        

        System.out.print("Done!");
    }    
    
}
