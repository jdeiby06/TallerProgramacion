package modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import Services.ConexionBDD; // Asumo que esta clase es correcta

public class Docente {

    private int id;
    private String nombre;
    private double salario; // Nuevo atributo, por ejemplo
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public Docente(int id, String nombre, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
    }

    public Docente() {

    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    // --- Métodos CRUD ---

    public void registrarDocente(String nombre, double salario) {
        String sql = "INSERT INTO docente (nombre, salario) VALUES (?, ?)"; // Ajuste la tabla y campos

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, salario);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Registro de docente exitoso.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible registrar el docente.");
            errorAlert.show();
        }
    }

    public ArrayList<Docente> obtenerDocentes() {
        String sql = "SELECT id, nombre, salario FROM docente"; // Ajuste la tabla y campos
        ArrayList<Docente> docentes = new ArrayList<>();

        try {
            Connection connection = ConexionBDD.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Docente docente = new Docente(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getDouble("salario"));
                docentes.add(docente);
            }
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible obtener los docentes.");
            errorAlert.show();
        }

        return docentes;
    }

    public void actualizarDocente(int id, String nombre, double salario) {
        String sql = "UPDATE docente SET nombre = ?, salario = ? WHERE id = ?"; // Ajuste la tabla y campos

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, salario);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Actualización de docente exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible actualizar la información del docente.");
            errorAlert.show();
        }
    }

    public void eliminarDocente(int id){
        String sql = "DELETE FROM docente WHERE id = ?"; // Ajuste la tabla

        try (Connection connection = ConexionBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Eliminación de docente exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible eliminar el docente.");
            errorAlert.show();
        }
    }
}
