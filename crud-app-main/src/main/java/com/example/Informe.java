import controllers.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Informe {

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
