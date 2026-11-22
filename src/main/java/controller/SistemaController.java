package controller;
import java.util.ArrayList;
import java.util.LocalDateTime;
import java.util.format.DateTimeFormatter;
import model.Ciudadano;
import model.Operador;
import model.LLamada;
import model.NotaSeguimiento;

public class SistemaController{
  private ArrayList<Operador> operadores;
  private ArrayList<Ciudadano> ciudadanos;
  private ArrayList<Llamada> llamadas;
  private static final DateTimeFormatter FORMATO_FECHA_HORA =
          DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
  private static final DateTimeFormatter FORMATO_SOLO_FECHA =
          DateTimeFormatter.ofPattern("dd/MM/yyyy";


  private void cargarDatosIniciales(){
    String fechaHoy=getFechaActualSistema();
    Operador opInicial= new Operador("Laura", "Mendoza", "Matutino", "l.mendoza@ecu.911.example");
    operadores.add(opInicial);

    Ciudadano ciuInicial=new Ciudadano ("0912345678", "Marcos Andrés", "Villacís Torres");
    ciudadanos.add(ciuInicial);

    Llamada llamInicial= new Llamada(fechaHoy + " 07:50", opInicial, ciuInicial, "Tránsito", "Guayaquil", "Urdesa", "Choque entre un bus y un automóvil en la av. Víctor Emilio Estrada.");
    NotaSeguimiento notaInicial= new NotaSeguimiento (fechaHoy + "07:50", opInicial; "Se despacha unidad de tránsito y ambulancia al sector.");
    llamInicial.agregarNota(notaInicial);
    llamadas.add(llamInicial);

    
  }
  public SistemaController(){
    this.operadores= new Arraylist<Operador>();
    this.ciudadanos= new ArrayList<Ciudadano>();
    this.llamadas= new ArrayList<Llamada>();
    cargarDatosIniciales();

    
  }
  public String obtenerFechaHoraSistema(){
    return LocalDateTime.now().format(FORMATO_FECHA_HORA);
    
    
  } 
  public String getFechaActualSistema(){
    return LocalDateTime.now().format(FORMATO_SOLO_FECHA);
    
  }
  public ArrayList<Llamada> obtenerLlamadasOrdenadasReciente() {
    ArrayList<Llamada> resultado= new ArrayList<Llamada>();
    for (int= llamadas.size() - 1; i>=0; i--){
      resultado.add(llamadas.get(i));
    }
    return resultado;
  }
  public ArrayList<Llamada> obtenerLlamadasPendientes(){
    ArrayList<Llamada> pendientes= new Arraylist<Llamada>();
    for (int=llamadas.size() -1; i>=0; i--){
      Llamada ll= llamadas.get(i);
      if(!ll.getEstado().equals("Atentida"){
        pendientes.add(ll);
        
      }
    }
    return pendientes;
  }
  public ArrayList<Llamada> obtenerLlamadasAnulablesHoy(){
    String fechaHoy= getFechaActualSistema();
    ArrayList<Llamada> anulables = new ArrayList<Llamada>();
    for (int i=llamadas.size() -1; i>=0; i--){
      Llamada ll=llamadas.get(i);
      if (ll.getEstado().equals("Registrada") && ll.getSoloFecha().equals(fechaHoy)) {
        anulables.add(ll);
      }
    }
    return anulables;
  }
  public Ciudadano buscarCiudadanoPorCedula(String cedula){
    for (Ciudadano c: ciudadanos){
      if (c.getCedula().equals(cedula)){
        return c;
      }
    }
    return null;
    
  }
  public Ciudadano registrarCiudadanoSiNoExiste(String cedula, String nombres, String apellidos){
    Ciudadano existente= buscarCiudadanoPorCedula(cedula);
    if (existente != null){
      return existente;
      
    }
    Ciudadano nuevo= new Ciudadano(cedula, nombres, apellidos);
    ciudadanos.add(nuevo);
    return nuevo;
    
  }
  public Llamada registrarLlamada(Operador operador, Ciudadano ciudadano, String tipoEmergencia, String ciudad, String barrio, String detalle){
    String fechaHora= obtenerFechaHoraSistema();
    LLamada nueva= new Llamada (fechaHora, operador, ciudadano, tipoEmergencia, ciudad, barrio, detalle);
    llamadas.add(nueva);
    return nueva;
  }
  public Llamada buscarLlamadaPorCodigo(String codigo){
    for (Llamada ll: llamadas){
      if (ll.getCodigo().equalsIgnoreCase(codigo)){
        return ll;
      }
    }
    return null;
  }
  public boolean avanzarEstadoLlamada(Llamada llamada){
    if (llamada==null){
      return false;
    }
    return llamada.avanzarEstado();
  }
  public boolean anularLlamada(Llamada llamada){
    if (llamada==null){
      return false;
    }
    String fechaHoy= getFechaActualSistema();
    if (llamada.getEstado().equals("Registrada") && llamada.getSoloFecha().equals(fechaHoy)){
      return llamadas.remove(llamada);
    }
    return false;
  }
  public ArrayList<Operador> getOperadores(){
    return operadores;
  }
  public Operador agregarOperador (String nombres, String apellidos, String turno, String email){
    Operador nuevo= new Operador (nombres, apellidos, turno, email);
    operadores.add(nuevo);
    return nuevo;
  }
  public Operador buscarOperadorPorCodigo(String codigo){
    for (Operador op: operadores){
      if (op.getCodigo().equalsIgnoreCase(codigo)){
        return op;
        
      }
    }
    return null;
  }
  public int contarLlamadasDeOperador(Operador operador){
    int total=0;
    for (Llamada ll: llamadas){
      if (ll.getOperador().equals(operador)){
        total++;
      }
    }
    return total;
  }
  public boolean eliminarOperador(Operador operador){
    if (operador==null){
      return false;
    }
    if (contarLlamadasDeOperador(operador)>0){
      return false;
    }
    return operadores.remove(operador);
  }
  public ArrayList<Ciudadano> getCiudadanos(){
    return ciudadanos; 
  }
  public int contarLlamadasDeCiudadano(Ciudadano ciudadano){
    int total=0;
    for (Llamada ll: llamadas){
      if(ll.getCiudadano().equals(ciudadano)){
        total++;
      }
    }
    return total;
  }
  public ArrayList<Llamada> obtenerHistorialCiudadano(Ciudadano ciudadano){
    ArrayList<Llamada> historial=new ArrayList<Llamada>();
    for (int i=llamadas.size() -1; i>=0; i--){
      Llamada ll= llamadas.get(i);
      if(ll.getCiudadano().equals(ciudadano)){
        historial.add(ll);
      }
    }
    return historial;
  }
  public NotaSeguimiento agregarNotaSeguimiento(Llamada llamada, Operador operador, String texto){
    String fechaHora= obtenerFechaHoraSistema();
    NotaSeguimiento nota= new NotaSeguimiento(fechaHora, operador, texto);
    llamada.agregarNota(nota);
    return nota;
  }
  public ArrayList<NotaSeguimiento> obtenerNotasOrdenadasRecientes(Llamada llamada){
    ArrayList<NotaSeguimiento> origen= llamada.getNotas();
    ArrayList<NotaSeguimiento> ordenadas= new ArrayList<NotaSeguimiento>();
    for (int i=origen.size() -1; i>=0; i--){
      ordenadas.add(origen.get(i));
    }
    return ordenadas;
  }
  
  
  

  
}
