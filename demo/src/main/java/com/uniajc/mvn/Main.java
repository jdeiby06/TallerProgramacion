package com.uniajc.mvn;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

public class Main extends Application {
   


    @Override
    public void start(Stage stage) throws Exception {


        



        // 1. Configuración de la ventana principal (Estudiantes)
        final FXMLLoader estudiantesLoader = new FXMLLoader(getClass().getResource("/vistas/CRUDEstudiantes.fxml"));
final Scene estudiantesScene = new Scene(estudiantesLoader.load());

stage.setScene(estudiantesScene);
stage.setTitle("Estudiantes");
stage.show();

        // 2. Configuración de la ventana de Cursos (Nueva)
        try {
            
            final FXMLLoader cursosLoader = new FXMLLoader(getClass().getResource("/vistas/CRUDCursos.fxml"));
            final Stage cursosStage = new Stage();
            cursosStage.setScene(new Scene(cursosLoader.load()));
            cursosStage.setTitle("Cursos");
            cursosStage.show();
        } catch (Exception e) {
            
            System.err.println("Error al cargar la interfaz de Cursos: " + e.getMessage());
        }

       
        try {
            // NOTA: Asumo que el archivo se llama CRUDDocentes.fxml y existe en /views/
            final FXMLLoader docentesLoader = new FXMLLoader(getClass().getResource("/vistas/CRUDDocentes.fxml"));
            final Stage docentesStage = new Stage();
            docentesStage.setScene(new Scene(docentesLoader.load()));
            docentesStage.setTitle("Docentes");
            docentesStage.show();
        } catch (Exception e) {
            // Manejo de error si no se encuentra o carga el FXML
            System.err.println("Error al cargar la interfaz de Docentes: " + e.getMessage());
        }
    }

    public static void main(String[] args)  {
        launch();
    }
}   