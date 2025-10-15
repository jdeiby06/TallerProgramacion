package controlador;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import modelo.Docente; // Importar el nuevo modelo
import config.AppServices; // Asumo que esta clase es correcta
import javafx.scene.control.cell.PropertyValueFactory;

public class CRUDDocentesController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtSalario; // Cambiado de txtEdad a txtSalario
    @FXML private TableView<Docente> tblDocentes; // Cambiado a Docente
    @FXML private TableColumn<Docente, Integer> colId; // Cambiado a Docente
    @FXML private TableColumn<Docente, String> colNombre; // Cambiado a Docente
    @FXML private TableColumn<Docente, Double> colSalario; // Cambiado a Double para salario

    final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    @FXML void registrarDocente(ActionEvent event) {
        if (txtId.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtSalario.getText().isEmpty()) {
            try {
                // Usar el método del modelo Docente y parsear a double
                AppServices.getDocente().registrarDocente(txtNombre.getText(), Double.parseDouble(txtSalario.getText()));
                actualizarTabla();
            } catch (NumberFormatException e) {
                warningAlert.setContentText("El salario debe ser un número válido.");
                warningAlert.show();
            }
        } else if (txtNombre.getText().isEmpty() || txtSalario.getText().isEmpty()) {
            warningAlert.setContentText("Complete la información necesaria para registrar el docente.");
            warningAlert.show();
        } else {
            warningAlert.setContentText("Limpie los campos para registrar el docente.");
            warningAlert.show();
        }
    }

    @FXML void actualizarDocente(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            try {
                // Usar el método del modelo Docente y parsear a double y int
                int id = Integer.parseInt(txtId.getText());
                double salario = Double.parseDouble(txtSalario.getText());
                AppServices.getDocente().actualizarDocente(id, txtNombre.getText(), salario);
                actualizarTabla();
            } catch (NumberFormatException e) {
                 warningAlert.setContentText("ID y/o salario deben ser números válidos.");
                 warningAlert.show();
            }
        } else {
            warningAlert.setContentText("Seleccione un docente para actualizar.");
            warningAlert.show();
        }
    }

    @FXML void eliminarDocente(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            // Usar el método del modelo Docente
            AppServices.getDocente().eliminarDocente(Integer.parseInt(txtId.getText()));
            actualizarTabla();
        } else {
            warningAlert.setContentText("Seleccione un docente para eliminar.");
            warningAlert.show();
        }
    }

    @FXML void limpiarCampos(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtSalario.clear();
        tblDocentes.getSelectionModel().clearSelection();
    }

    @FXML void refrescarTabla(ActionEvent event) {
        actualizarTabla();
    }

    private void actualizarTabla() {
        tblDocentes.getItems().clear();
        // Obtener la lista de docentes
        tblDocentes.getItems().addAll(AppServices.getDocente().obtenerDocentes());
    }

    public void initialize() {
        warningAlert.setTitle("Advertencia");

        // Enlazar columnas de la tabla a las propiedades del modelo Docente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario")); // Enlazar a 'salario'

        // Cargar datos iniciales
        tblDocentes.getItems().addAll(AppServices.getDocente().obtenerDocentes());

        // Listener para llenar los campos de texto al seleccionar una fila
        tblDocentes.getSelectionModel().selectedItemProperty().addListener((ignoredObservableValue, oldValue, newValue) -> {
            if (newValue != null) {
                txtId.setText(String.valueOf(newValue.getId()));
                txtNombre.setText(newValue.getNombre());
                txtSalario.setText(String.valueOf(newValue.getSalario())); // Usar el salario
            }
        });
    }
}