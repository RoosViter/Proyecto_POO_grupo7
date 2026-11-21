package model;
import java.util.Objects;

// Clase para guardar las notas de seguimiento que se le agreguen a una llamada
public class NotaSeguimiento {
  //contador para ir sumando cuantas notas se crean y no repetir cod.
  private static int contadorNotas = 0;
  //Datos para cada nota
  private String codigo;
  private String fechaHora;
  private Operador operador;
  private String texto;
}
