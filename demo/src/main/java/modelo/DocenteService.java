package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DocenteService {

    private final ObservableList<Docente> docentes = FXCollections.observableArrayList();

    public DocenteService() {
        // Datos iniciales de prueba
        docentes.add(new Docente(1, "Carlos Pérez", 2500000));
        docentes.add(new Docente(2, "Ana Gómez", 3200000));
    }

    public ObservableList<Docente> obtenerDocentes() {
        return docentes;
    }

    public void registrarDocente(String nombre, double salario) {
        int nuevoId = docentes.size() + 1;
        docentes.add(new Docente(nuevoId, nombre, salario));
    }

    public void actualizarDocente(int id, String nombre, double salario) {
        for (Docente d : docentes) {
            if (d.getId() == id) {
                d.setNombre(nombre);
                d.setSalario(salario);
                break;
            }
        }
    }

    public void eliminarDocente(int id) {
        docentes.removeIf(d -> d.getId() == id);
    }
}
