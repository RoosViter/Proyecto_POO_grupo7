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
  
  // Constructor que suma1 al contador, arma el cod y guarda losdatos
  public NotaSeguimiento(String fechaHora, Operador operador, String texto) {
    contadorNotas++;
    this.codigo = generarCodigo(contadorNotas);
    this.fechaHora = fechaHora;
    this.operador = operador;
    this.texto = texto;
  }

  // Le ponemos ceros adelante para que el cod quede tipo NT-001, NT-010, etc.
  private static String generarCodigo(int numero) {
    if (numero < 10) {
      return "NT-00" + numero;
    } else if (numero < 100) {
      return "NT-0" + numero;
    }
    return "NT-" + numero;
  }
  
  // Getters para poder leer los datos de la nota desde otrs clases
  public String getCodigo() {
    return codigo;
  }

  public String getFechaHora() {
    return fechaHora;
  }

  public Operador getOperador() {
    return operador;
  }

  public String getTexto() {
    return texto;
  }

  // Setters por si toca cambiar alguno de los datos despues
  public void setFechaHora(String fechaHora) {
    this.fechaHora = fechaHora;
  }

  public void setOperador(Operador operador) {
    this.operador = operador;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }
}
