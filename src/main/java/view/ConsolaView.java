  package view;

import controller.SistemaController;
import java.util.ArrayList;
import java.util.Scanner;
import model.Ciudadano;
import model.Llamada;
import model.NotaSeguimiento;
import model.Operador;
import model.Persona;

public class ConsolaView{
  private Scanner sc;
  private SistemaController controller;
  //Constructor de la clase vista
  //se usa el controller del sistema
  public ConsolaView(SistemaController controller){
    this.sc = new Scanner(System.in);
    this.controller = controller;
  }
  //inicia el ciclo principal de la interaccion por consola
  public void iniciar(){
    boolean salir=false;
    while(!salir){
      System.out.println();
      System.out.println("=========================================================================");
      System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ");
      System.out.println("[Menu Principal]");
      System.out.println("[1] Administrar llamadas de emergencia");
      System.out.println("[2] Administrar Operadores");
      System.out.println("[3] Administrar ciudadanos");
      System.out.println("[4] Administrar notas de seguimiento");
      System.out.println("[5] Salir");
      System.out.println("Fecha y hora actuales del sistema: "+controller.obtenerFechaHoraSistema());
      //creamos el switch con las posibles respuestas del usuario
      int opcion= leerOpcion("> Seleccione una opcion (1-5) <",1,5);
      switch(opcion){
        case 1:
          menuLlamadas();
          break;
        case 2:
          menuOperadores();
        case 3:
          menuCiudadanos();
        case 4:
          menuNotasSeguimiento();
        case 5:
          System.out.println("Saliendo del sistema ECU 911. Hasta pronto!");
          salir=true;
          break;
        default:
          break;
      }
    }
  }
  private void menuLlamadas(){
    boolean regresar=false;
    while (!regresar){
      System.out.println();
      System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
      System.out.println("[Menú Principal > 1. Administrar llamadas de emergencia]");
      System.out.println("[1] Lista de llamadas registradas");
      System.out.println("[2] Registrar llamada");
      System.out.println("[3] Actualizar estado de una llamada");
      System.out.println("[4] Anular llamada");
      System.out.println("[5] Regresar al Menú Principal");
    }
  }
}
