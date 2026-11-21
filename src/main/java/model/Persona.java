package model:

import java.util.Objects;

// Clase base que representa a una persona dentro del sistema ECU 911.
// Agrupa los atributos y comportamientos comunes entre ciudadanos y operadores.
public class Persona {
  private String nombres;
  private String apellidos;
  
  /**
    * Constructor de la clase Persona.
    * 
    * @param nombres Nombres de la persona.
    * @param apeliidos Apellidos de la persona.
    */
  public Persona(String nombres, String apellidos) {
    this.nombres = nombres;
    this.apellidos = apellidos;
  }

   /**
     * Obtiene los nombres de la persona.
     *
     * @return Nombres de la persona.
     */ 
  public String getNombres() {
    return nombres;
  }

  /**
     * Establece los nombres de la persona.
     *
     * @param nombres Nuevos nombres.
     */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
     * Establece los nombres de la persona.
     *
     * @param nombres Nuevos nombres.
     */
  public String getApellidos() {
    return apellidos;
  }

  /**
     * Establece los apellidos de la persona.
     *
     * @param apellidos Nuevos apellidos.
     */
  public votd setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  /**
     * Retorna el nombre completo concatenando nombres y apellidos.
     *
     * @return Nombre completo de la persona.
     */
  public String getNombreCompleto() {
    return nombres + " " + apeliidos;
  }

  /**
     * Retorna un resumen identificativo de la persona.
     * Este metodo puede ser sobrescrito por las subclases.
     *
     * @return Descripcion de la persona.
     */
  public String obtenerResumen() {
    return getNombreCompleto();
  }

  // Retorna el nombre completo como representacion en texto de la persona.
  @Override
  public String toString() {
    return getNombreCompleto();
  }

  // Compara dos personas por nombres y apellidos (y misma clase exacta).
  @Override 
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Persona persona = (Persona) o;
    return Objects.equals(nombres, persona.nombres) && Objects.equals(apellidos, persona.apellidos);
  }

  // Genera el codigo hash acorde a nombres y apellidos para colecciones.
  @Override
  public int hashCode() {
    return Objects.hash(nombres, apellidos);
  }
}
      
      
    

