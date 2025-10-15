package config;
import modelo.Docente;
import modelo.Estudiante;
import modelo.Curso;

public class AppServices {

    // Instancia Singleton para Estudiante
    private static final Estudiante ESTUDIANTE = new Estudiante();
    
    // Instancia Singleton para Docente
    private static final Docente DOCENTE = new Docente();
    
    // Instancia Singleton para Curso (¡Nuevo!)
    private static final Curso CURSO = new Curso();

    /**
     * Devuelve la única instancia del modelo Estudiante.
     * @return Instancia de Estudiante.
     */
    public static Estudiante getEstudiante() {
        return ESTUDIANTE;
    }

    /**
     * Devuelve la única instancia del modelo Docente.
     * @return Instancia de Docente.
     */
    public static Docente getDocente() {
        return DOCENTE;
    }

    /**
     * Devuelve la única instancia del modelo Curso. (¡Nuevo!)
     * @return Instancia de Curso.
     */
    public static Curso getCurso() {
        return CURSO;
    }
}