package config;

import modelo.Estudiante;
import modelo.DocenteService; 
import modelo.Curso; 

public class AppServices {

    private static final Estudiante ESTUDIANTE = new Estudiante();
    private static final DocenteService DOCENTE = new DocenteService();
    private static final Curso CURSO = new Curso();

    public static Estudiante getEstudiante() {
        return ESTUDIANTE;
    }

    public static DocenteService getDocente() {
        return DOCENTE;
    }

    public static Curso getCurso() {
        return CURSO;
    }
}
