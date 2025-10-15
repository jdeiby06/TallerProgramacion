package com.uniajc.mvn.modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import com.uniajc.mvn.services.ConexionBDD; // Asumo que esta clase es accesible

/**
 * Clase de modelo para representar un Curso y manejar sus operaciones
 * CRUD con la base de datos.
 */
public class Curso {

    private int id;
    private String nombre; // e.g., "Matemáticas", "Inglés"
    private int creditos;  // e.g., 3, 4, 5 (asumiendo un atributo adicional)

    // Alertas estáticas, siguiendo el patrón de Estudiante
    final private static Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    final private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * Constructor con todos los campos.
     */
    public Curso(int id, String nombre, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    /**
     * Constructor vacío.
     */
    public Curso() {

    }

    // --- Getters y Setters ---

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

    public int getCreditos() {
        return this.creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    // --- Métodos CRUD ---

    /**
     * Registra un nuevo curso en la base de datos.
     * @param nombre El nombre del curso (ej: "Matemáticas").
     * @param creditos La cantidad de créditos del curso.
     */
    public void registrarCurso(String nombre, int creditos) {
        String sql = "INSERT INTO curso (nombre, creditos) VALUES (?, ?)";

        try (Connection connection = ConexionBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, creditos);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Registro de curso exitoso.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible registrar el curso.");
            errorAlert.show();
            // Lo ideal es loggear la excepción: Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtiene y retorna una lista de todos los cursos registrados.
     * @return Una lista de objetos Curso.
     */
    public ArrayList<Curso> obtenerCursos() {
        String sql = "SELECT id, nombre, creditos FROM curso";
        ArrayList<Curso> cursos = new ArrayList<>();

        try (Connection connection = ConexionBDD.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Curso curso = new Curso(
                    resultSet.getInt("id"), 
                    resultSet.getString("nombre"), 
                    resultSet.getInt("creditos")
                );
                cursos.add(curso);
            }
        } catch (Exception e) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible obtener los cursos.");
            errorAlert.show();
        }

        return cursos;
    }

    /**
     * Actualiza la información de un curso existente.
     * @param id El ID del curso a actualizar.
     * @param nombre El nuevo nombre del curso.
     * @param creditos La nueva cantidad de créditos.
     */
    public void actualizarCurso(int id, String nombre, int creditos) {
        String sql = "UPDATE curso SET nombre = ?, creditos = ? WHERE id = ?";

        try (Connection connection = ConexionBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, creditos);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Actualización de curso exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible actualizar la información del curso.");
            errorAlert.show();
        }
    }

    /**
     * Elimina un curso de la base de datos por su ID.
     * @param id El ID del curso a eliminar.
     */
    public void eliminarCurso(int id) {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (Connection connection = ConexionBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            confirmationAlert.setTitle("Éxito");
            confirmationAlert.setContentText("Eliminación de curso exitosa.");
            confirmationAlert.show();
        } catch (Exception ex) {
            errorAlert.setTitle("Error");
            errorAlert.setContentText("No fue posible eliminar el curso.");
            errorAlert.show();
        }
    }
}