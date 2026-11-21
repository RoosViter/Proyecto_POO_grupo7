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

  // Constructor que arma el cod, pone estado inicial en "Registrada" y crea la lista de notas
  public Llamada(String fechaHora, Operador operador, Ciudadano ciudadano,
                 String tipoEmergencia, String ciudad, String barrio, String detalle) {
    contadorLlamadas++;
    this.codigo = generarCodigo(contadorLlamadas);
    this.fechaHora = fechaHora;
    this.operador = operador;
    this.ciudadano = ciudadano;
    this.tipoEmergencia = tipoEmergencia;
    this.ciudad = ciudad;
    this.barrio = barrio;
    this.detalle = detalle;
    this.estado = "Registrada";
    this.notas = new ArrayList<NotaSeguimiento>();
  }

  // Le ponemosceros adelante para que quede tipo LL-001, LL-010, etc.
  private static String generarCodigo(int numero) {
    if (numero < 10) {
      return "LL-00" + numero;
    } else if (numero < 100) {
      return "LL-0" + numero;
    }
    return "LL-" + numero;
  }
  // Getters para leer los datos de llamada
  public String getCodigo() {
    return codigo;
  }

  public String getFechaHora() {
    return fechaHora;
  }

  public Operador getOperador() {
    return operador;
  }

  public Ciudadano getCiudadano() {
    return ciudadano;
  }

  public String getTipoEmergencia() {
    return tipoEmergencia;
  }

  public String getCiudad() {
    return ciudad;
  }

  public String getBarrio() {
    return barrio;
  }

  public String getDetalle() {
    return detalle;
  }

  public String getEstado() {
    return estado;
  }

  public ArrayList<NotaSeguimiento> getNotas() {
    return notas;
  }

  // Setters pr si toca cambiar algun dato después
  public void setFechaHora(String fechaHora) {
    this.fechaHora = fechaHora;
  }

  public void setOperador(Operador operador) {
    this.operador = operador;
  }

  public void setCiudadano(Ciudadano ciudadano) {
    this.ciudadano = ciudadano;
  }

  public void setTipoEmergencia(String tipoEmergencia) {
    this.tipoEmergencia = tipoEmergencia;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public void setBarrio(String barrio) {
    this.barrio = barrio;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  // Para obtener fecha antes del espacio
  public String getSoloFecha() {
    String[] partes = fechaHora.split(" ");
    return partes[0];
  }

  // Agrega una nota nueva a la lista 
  public void agregarNota(NotaSeguimiento nota) {
    notas.add(nota);
  }

  // Revisa cual es el siguiente estado permitido
  public String obtenerSiguienteEstado() {
    if (estado.equals("Registrada")) {
      return "En atnción";
    } else if (estado.equals("En atención")) {
      return "Atendida";
    }
    return null;
  }

  // Cambia el estado al siguiente paso si todavia se puede avanza
  public boolean avanzarEstado() {
    String siguiente = obtenerSiguienteEstado();
    if (siguiente != null) {
      this.estado = siguiente;
      return true;
    }
    return false;
  }
}
