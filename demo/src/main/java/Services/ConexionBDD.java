package Services;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.FileInputStream;
import javafx.scene.control.Alert;

public class ConexionBDD {

  private static Connection connection = null;
  final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

  public static Connection getConnection() {

    errorAlert.setTitle("Error");

    try {
      if (connection == null || connection.isClosed()) {

        Properties properties = new Properties();
        properties.load(new FileInputStream(("config.properties")));

        Class.forName("org.postgresql.Driver");

        String url = properties.get("URL").toString();
        String username = properties.get("USERNAME").toString();
        String password = properties.get("PASSWORD").toString();

        connection = DriverManager.getConnection(url, username, password);
      }
    } catch (ClassNotFoundException e) {
      errorAlert.setContentText("Error al cargar el driver JDBC:\n" + e.getMessage());
      errorAlert.show();
    } catch (SQLException e) {
      errorAlert.setContentText("Error al conectar a la base de datos:\n" + e.getMessage());
      errorAlert.show();
    } catch (IOException e) {
      errorAlert.setContentText("Error al cargar el archivo:\n" + e.getMessage());
      errorAlert.show();
    }

    return connection;
  }
}