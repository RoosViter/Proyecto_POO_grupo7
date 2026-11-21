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
  * @param nombres; Nombres de la persona.
  * @param apeliidos; Apellidos de la persona.
  */

  public Persona(String nombres, String apellidos) {
    this.nombres = nombres;
    this.apellidos = apellidos;
  }
  
