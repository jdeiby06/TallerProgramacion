package controlador;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import modelo.Curso;


 // Importamos la clase Curso
import config.AppServices; // Asumimos que getCurso() existe aquí
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de JavaFX para la gestión de Cursos (CRUD).
 * Imita la lógica de CRUDEstudiantesController.
 */
public class CRUDCursosController {

    // --- Componentes FXML ---
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCreditos; // Nuevo campo para 'creditos'
    @FXML private TableView<Curso> tblCursos; // TableView para la clase Curso
    @FXML private TableColumn<Curso, Integer> colId;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, Integer> colCreditos; // Columna para 'creditos'

    // Alerta de advertencia
    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    // --- Métodos de Acción (Mapeados a FXML) ---

    /**
     * Registra un nuevo curso si los campos de nombre y créditos están llenos
     * y el ID está vacío.
     */
    @FXML
    void registrarCurso(ActionEvent event) {
        // Validación similar a la de Estudiante
        if (txtId.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtCreditos.getText().isEmpty()) {
            try {
                String nombre = txtNombre.getText();
                int creditos = Integer.parseInt(txtCreditos.getText());
                
                // Llamada al método del modelo
                AppServices.getCurso().registrarCurso(nombre,creditos);
                actualizarTabla();
                limpiarCampos(null); // Limpiar campos después del registro
            } catch (NumberFormatException e) {
                warningAlert.setContentText("Los créditos deben ser un número válido.");
                warningAlert.show();
            }
        } else if (txtNombre.getText().isEmpty() || txtCreditos.getText().isEmpty()) {
            warningAlert.setContentText("Complete la información necesaria (Nombre y Créditos) para registrar el curso.");
            warningAlert.show();
        } else {
            warningAlert.setContentText("Limpie los campos para registrar un nuevo curso.");
            warningAlert.show();
        }
    }

    /**
     * Actualiza la información de un curso seleccionado. Requiere que el campo ID no esté vacío.
     */
    @FXML
    void actualizarCurso(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                int creditos = Integer.parseInt(txtCreditos.getText());
                
                // Llamada al método del modelo
                AppServices.getCurso().actualizarCurso(id, nombre, creditos);
                actualizarTabla();
                limpiarCampos(null);
            } catch (NumberFormatException e) {
                warningAlert.setContentText("El ID y los créditos deben ser números válidos.");
                warningAlert.show();
            }
        } else {
            warningAlert.setContentText("Seleccione un curso para actualizar.");
            warningAlert.show();
        }
    }

    /**
     * Elimina el curso seleccionado. Requiere que el campo ID no esté vacío.
     */
    @FXML
    void eliminarCurso(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtId.getText());
                
                // Llamada al método del modelo
                AppServices.getCurso().eliminarCurso(id);
                actualizarTabla();
                limpiarCampos(null);
            } catch (NumberFormatException e) {
                // Esta excepción es poco probable si el campo se llena solo al seleccionar
            }
        } else {
            warningAlert.setContentText("Seleccione un curso para eliminar.");
            warningAlert.show();
        }
    }

    /**
     * Limpia todos los campos de texto y la selección de la tabla.
     */
    @FXML
    void limpiarCampos(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtCreditos.clear();
        tblCursos.getSelectionModel().clearSelection();
    }

    /**
     * Refresca la tabla cargando nuevamente los datos desde la base de datos.
     */
    @FXML
    void refrescarTabla(ActionEvent event) {
        actualizarTabla();
    }

    // --- Métodos Auxiliares ---

    /**
     * Lógica para recargar los datos en el TableView.
     */
    private void actualizarTabla() {
        tblCursos.getItems().clear();
        // Nota: Asumimos que AppServices.getCurso() retorna una instancia de Curso 
        // y que el método obtenerCursos() existe en el modelo Curso.
        tblCursos.getItems().addAll(AppServices.getCurso().obtenerCursos());
    }

    // --- Inicialización ---

    /**
     * Método de inicialización del controlador (llamado después de cargar FXML).
     */
    public void initialize() {
        warningAlert.setTitle("Advertencia");

        // 1. Mapeo de columnas de la tabla a las propiedades del objeto Curso
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCreditos.setCellValueFactory(new PropertyValueFactory<>("creditos")); // Mapeo al nuevo campo 'creditos'

        // 2. Cargar datos iniciales
        actualizarTabla();

        // 3. Listener para la selección de fila
        tblCursos.getSelectionModel().selectedItemProperty().addListener((ignoredObservableValue, oldValue, newValue) -> {
            if (newValue != null) {
                // Rellenar campos de texto con la información del curso seleccionado
                Curso cursoSeleccionado = newValue;
                txtId.setText(String.valueOf(cursoSeleccionado.getId()));
                txtNombre.setText(cursoSeleccionado.getNombre());
                txtCreditos.setText(String.valueOf(cursoSeleccionado.getCreditos()));
            }
        });
    }
}