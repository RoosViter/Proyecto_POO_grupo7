package model;

import java.util.ArrayList;
import java.util.Objects;

// Clase para guardar las llamadas de emergencia del ECU 911 y sus notas
public class Llamada {
  // contador para no repetir cod de llamadas
  private static int contadorLlamadas = 0;

  // Datos principales de llamada
  private String codigo;
  private String fechaHora;
  private Operador operador;
  private Ciudadano ciudadano;
  private String tipoEmergencia;
  private String ciudad;
  private String barrio;
  private String detalle;
  private String estado;
  // Lista de notas que se van agregando a llamada
  private ArrayList<NotaSeguimiento> notas;
}
