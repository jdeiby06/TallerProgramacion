package com.uniajc.mvn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // === Ventana principal: Estudiantes ===
            FXMLLoader estudiantesLoader = new FXMLLoader(getClass().getResource("/vistas/CRUDEstudiantes.fxml"));
            Scene estudiantesScene = new Scene(estudiantesLoader.load());
            stage.setScene(estudiantesScene);
            stage.setTitle("Gestión de Estudiantes");
            stage.show();
            System.out.println("✅ Interfaz de Estudiantes cargada correctamente.");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar la interfaz de Estudiantes: " + e.getMessage());
        }

        // === Ventana de Cursos ===
        cargarVentana("/vistas/CRUDCurso.fxml", "Gestión de Cursos");

        // === Ventana de Docentes ===
        cargarVentana("/vistas/CRUDDocente.fxml", "Gestión de Docentes");
    }

    /**
     * Método auxiliar para cargar cualquier ventana adicional.
     */
    private void cargarVentana(String rutaFXML, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(loader.load()));
            nuevoStage.setTitle(tituloVentana);
            nuevoStage.show();
            System.out.println("✅ Interfaz de " + tituloVentana + " cargada correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al cargar la interfaz de " + tituloVentana + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
