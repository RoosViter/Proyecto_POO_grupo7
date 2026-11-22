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
  
  
  

  
}
