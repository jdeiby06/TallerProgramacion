package controlador;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import modelo.Estudiante;
import config.AppServices;
import javafx.scene.control.cell.PropertyValueFactory;

public class CRUDEstudiantesController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, Integer> colId;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, Integer> colEdad;

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    @FXML void registrarEstudiante(ActionEvent event) {
        if (txtId.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtEdad.getText().isEmpty()) {
            AppServices.getEstudiante().registrarEstudiante(txtNombre.getText(), Integer.parseInt(txtEdad.getText()));
            actualizarTabla();
        } else if (txtNombre.getText().isEmpty() || txtEdad.getText().isEmpty()) {
            warningAlert.setContentText("Complete la información necesaria para registrar el estudiante.");
            warningAlert.show();
        } else {
            warningAlert.setContentText("Limpie los campos para registrar el estudiante.");
            warningAlert.show();
        }
    }

    @FXML void actualizarEstudiante(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            AppServices.getEstudiante().actualizarEstudiante(Integer.parseInt(txtId.getText()), txtNombre.getText(), Integer.parseInt(txtEdad.getText()));
            actualizarTabla();
        } else {
            warningAlert.setContentText("Seleccione un estudiante para actualizar.");
            warningAlert.show();
        }
    }

    @FXML void eliminarEstudiante(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            AppServices.getEstudiante().eliminarEstudiantes(Integer.parseInt(txtId.getText()));
            actualizarTabla();
        } else {
            warningAlert.setContentText("Seleccione un estudiante para eliminar.");
            warningAlert.show();
        }
    }

    @FXML void limpiarCampos(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtEdad.clear();
        tblEstudiantes.getSelectionModel().clearSelection();
    }

    @FXML void refrescarTabla(ActionEvent event) {
        actualizarTabla();
    }

    private void actualizarTabla() {
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getItems().addAll(AppServices.getEstudiante());
    }

    public void initialize() {
        warningAlert.setTitle("Advertencia");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        tblEstudiantes.getItems().addAll(AppServices.getEstudiante().obtenerEstudiantes());
        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener((ignoredObservableValue, oldValue, newValue) -> {
            if (newValue != null) {
                txtId.setText(String.valueOf(tblEstudiantes.getSelectionModel().getSelectedItem().getId()));
                txtNombre.setText(tblEstudiantes.getSelectionModel().getSelectedItem().getNombre());
                txtEdad.setText(String.valueOf(tblEstudiantes.getSelectionModel().getSelectedItem().getEdad()));
            }
        });
    }
}