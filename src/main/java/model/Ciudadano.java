package model;

import.java.util.Objects;

/**
 * Representa a un ciudadano que realiza llamadas de emergencia al ECU 911.
 * Hereda de la clase Personas.
 */
public class Ciudadano extends Persona {
  private String cedula;

  /**
   * Constructor de la clase Ciudadano.
   *
   * @param cedula    Numero de cedula de 10 digitos.
   * @param nombres   Nombres del ciudadano.
   * @param apellidos Apellidos del ciudadano.
   */
  public Ciudadano(String cedula, String nombres, String apellidos) {
    super(nombres, apellidos);
    this.cedula = cedula;
  }
  
  /**
   * Obtiene la cedula del ciudadano.
   *
   * @return Cedula de 10 digitos.
   */
  public String getCedula() {
    return cedula;
  }

  /**
   * Establece la cedula del ciudadano.
   *
   * @param cedula Nueva cedula.
   */
  public void setCedula(String cedula) {
    this.cedula = cedula;
  }
}

