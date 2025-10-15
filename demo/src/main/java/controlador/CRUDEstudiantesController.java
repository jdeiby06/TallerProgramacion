package controlador;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Estudiante;
import config.AppServices;

public class CRUDEstudiantesController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, Integer> colId;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, Integer> colEdad;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    // ==================== REGISTRAR ====================
    @FXML
    void registrarEstudiante(ActionEvent event) {
        // Validar campos vacíos
        if (txtNombre.getText().isEmpty() || txtEdad.getText().isEmpty()) {
            warningAlert.setTitle("Advertencia");
            warningAlert.setContentText("Complete la información necesaria para registrar el estudiante.");
            warningAlert.show();
            return;
        }

        // Validar si hay ID (significa que seleccionaste un estudiante)
        if (!txtId.getText().isEmpty()) {
            warningAlert.setTitle("Advertencia");
            warningAlert.setContentText("Limpie los campos antes de registrar un nuevo estudiante.");
            warningAlert.show();
            return;
        }

        // Registrar estudiante
        AppServices.getEstudiante().registrarEstudiante(
                txtNombre.getText(),
                Integer.parseInt(txtEdad.getText())
        );

        // Actualizar y limpiar
        actualizarTabla();
        limpiarCampos(null);
    }

    // ==================== ACTUALIZAR ====================
    @FXML
    void actualizarEstudiante(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            AppServices.getEstudiante().actualizarEstudiante(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    Integer.parseInt(txtEdad.getText())
            );
            actualizarTabla();
            limpiarCampos(null);
        } else {
            warningAlert.setTitle("Advertencia");
            warningAlert.setContentText("Seleccione un estudiante para actualizar.");
            warningAlert.show();
        }
    }

    // ==================== ELIMINAR ====================
    @FXML
    void eliminarEstudiante(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            AppServices.getEstudiante().eliminarEstudiantes(Integer.parseInt(txtId.getText()));
            actualizarTabla();
            limpiarCampos(null);
        } else {
            warningAlert.setTitle("Advertencia");
            warningAlert.setContentText("Seleccione un estudiante para eliminar.");
            warningAlert.show();
        }
    }

    // ==================== LIMPIAR ====================
    @FXML
    void limpiarCampos(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtEdad.clear();
        tblEstudiantes.getSelectionModel().clearSelection();
    }

    // ==================== REFRESCAR ====================
    @FXML
    void refrescarTabla(ActionEvent event) {
        actualizarTabla();
    }

    // ==================== ACTUALIZAR TABLA ====================
    private void actualizarTabla() {
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getItems().addAll(AppServices.getEstudiante().obtenerEstudiantes());
    }

    // ==================== INICIALIZAR ====================
    public void initialize() {
        warningAlert.setTitle("Advertencia");

        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        // Cargar datos iniciales
        tblEstudiantes.getItems().addAll(AppServices.getEstudiante().obtenerEstudiantes());

        // Detectar selección de fila
        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtId.setText(String.valueOf(newVal.getId()));
                txtNombre.setText(newVal.getNombre());
                txtEdad.setText(String.valueOf(newVal.getEdad()));
            }
        });
    }
}
