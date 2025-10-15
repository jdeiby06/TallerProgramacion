package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class ConexionBDD {

    private static Connection connection = null;
    private static final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public static Connection getConnection() {
        errorAlert.setTitle("Error de conexión");

        try {
            if (connection == null || connection.isClosed()) {

                // 🔧 Datos de conexión
                String host = "127.0.0.1";
                String database = "uniajc_db";
                String user = "root";
                String password = "12345";

                // ✅ URL JDBC para MySQL
                String url = "jdbc:mysql://" + host + ":3306/" + database + "?useSSL=false&serverTimezone=UTC";

                // ✅ Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // ✅ Intentar conectar
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Conexión exitosa a la base de datos " + database);
            }

        } catch (ClassNotFoundException e) {
            errorAlert.setContentText("No se encontró el driver de MySQL:\n" + e.getMessage());
            errorAlert.show();
        } catch (SQLException e) {
            errorAlert.setContentText("Error al conectar con la base de datos:\n" + e.getMessage());
            errorAlert.show();
            e.printStackTrace();
        }

        return connection;
    }
}
