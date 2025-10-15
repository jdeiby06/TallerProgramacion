package modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import services.ConexionBDD;

public class Estudiante {

    private int id;
    private String nombre;
    private int edad;
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public Estudiante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Estudiante() {

    }

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

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void registrarEstudiante(String nombre, int edad) {
        String sql = "INSERT INTO estudiante (nombre, edad) VALUES (?, ?)";

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, edad);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Registro exitoso.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible registrar el estudiante.");
            errorAlert.show();
        }
    }

    public ArrayList<Estudiante> obtenerEstudiantes() {
        String sql = "SELECT id, nombre, edad FROM estudiante";
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        try {
            Connection connection = ConexionBDD.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Estudiante estudiante = new Estudiante(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getInt("edad"));
                estudiantes.add(estudiante);
            }
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible obtener los estudiantes.");
            errorAlert.show();
        }

        return estudiantes;
    }

    public void actualizarEstudiante(int id, String nombre, int edad) {
        String sql = "UPDATE estudiante SET nombre = ?, edad = ? WHERE id = ?";

        try {
            Connection connection = ConexionBDD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, edad);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Actualización exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible actualizar la información el estudiante.");
            errorAlert.show();
        }
    }

    public void eliminarEstudiantes(int id){
        String sql = "DELETE FROM estudiante WHERE id = ?";

        try (Connection connection = ConexionBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Eliminación exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible eliminar el estudiantes.");
            errorAlert.show();
        }
    }
}