package model;

import java.util.Objects;

/**
 * Representa a un operador del servicio integrado de seguridad ECU 911.
 * Hereda de la clase Persona y genera su codigo secuencial usando un atributo estatico.
 */
public class Operador extends Persona {
  private static int contadorOperadores = 0;

  private String codigo:
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
    
      

