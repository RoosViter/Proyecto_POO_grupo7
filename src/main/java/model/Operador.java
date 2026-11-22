package model;

import java.util.Objects;

/**
 * Representa a un operador del servicio integrado de seguridad ECU 911.
 * Hereda de la clase Persona y genera su codigo secuencial usando un atributo estatico.
 */
public class Operador extends Persona {
  private static int contadorOperadores = 0;

  private String codigo;
  private String turno;
  private String email;

  /**
   * Constructor de la clase Operador.
   *
   * @param nombres   Nombres del operador.
   * @param apellidos Apellidos del operador.
   * @param turno     Turno asignado (Matutino, Vespertino o Nocturno).
   * @param email     Correo electronico del operador (opcional).
   */
  public Operador(String nombres, String apellidos, String turno, String email) {
    super(nombres, apellidos);
    contadorOperadores++;
    this.codigo = generarCodigo(contadorOperadores);
    this.turno = turno;
    this.email = email;
  }

   /**
    * Formatea el numero secuencial como OP-01, OP-02, etc.
    *
    * @param numero Numero secuencial del operador.
    * @return Codigo formateado.
    */
  private static String generarCodigo(int numero) {
    if (numero < 10) {
      return "OP-0" + numero;
    }
    return "OP-" + numero;
  }

  /**
   * Obtiene el codigo unico del operador.
   *
   * @return Codigo del operador.
   */
  public String getCodigo() {
    return codigo;
  }

  /**
   * Obtiene el turno del operador.
   *
   * @return Turno del operador.
   */
  public String getTurno() {
    return turno;
  }

  /**
   * Establece el turno del operador.
   *
   * @param turno Nuevo turno.
   */
  public void setTurno(String turno) {
    this.turno = turno;
  }

  /**
   * Obtiene el correo electronico del operador.
   *
   * @return Correo electronico.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Establece el correo electronico del operador.
   *
   * @param email Nuevo correo electronico.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  // Sobrescribe el resumen de Persona para incluir codigo y turno del operador.
  @Override
  public String obtenerResumen() {
    return codigo + " - " + getNombreCompleto() + " (" + turno + ") ";
  }

  // Devuelve el resumen con codigo, nombre y turno como representacion en texto.
  @Override
  public String toString() {
    return obtenerResumen();
  }

  // Compara dos operadores por su codigo unico y misma clase exacta
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operador operador = (Operador) o;
    return Objects.equals(codigo, operador.codigo);
  }

  // Genera el codigo hash a partir del codigo del operador para colecciones.
  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }
}
  
  
    
      

