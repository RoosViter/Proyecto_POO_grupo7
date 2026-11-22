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

      int opcion= leerOpcion("> Seleccione una opción (1-5): ", 1, 5);
      switch (opcion) {
        case 1:
          listarLlamadas();
          break;
        case 2:
          registrarLlamada();
          break;
        case 3:
          actualizarEstadoLlamada();
          break;
        case 4:
          anularLlamada();
          break;
        case 5:
          regresar=true;
          break;
        default:
          break;
      }
    }
  }
  private void listarLlamadas(){
    System.out.println();
    System.out.println("[Menú Principal > 1. Llamadas > 1.1. Lista de llamadas]");
    ArrayList<Llamada> lista = controller.obtenerLlamadasOrdenadasRecientes();
    System.out.println("Se encontraron " + lista.size() + " llamadas registradas.");

    if (lista.isEmpty()) {
      System.out.println("No hay llamadas registradas en el sistema.");
    } else{
      mostrarSeparador();
      System.out.println("CÓDIGO | FECHA Y HORA | CIUDADANO | TIPO | CIUDAD | ESTADO");
      mostrarSeparador();
      for (Llamada ll : lista) {
        System.out.println(ll.toString());
      }
      mostrarSeparador();
    }
    pausar("Presione ENTER para regresar al Menú de Llamadas...");
  }
  private void registrarLlamada(){
    System.out.println();
    System.out.println("[Menú Principal > 1. Llamadas > 1.2. Registrar llamada]");

    ArrayList<Operador> ops = controller.getOperadores();
    if (ops.isEmpty()) {
      System.out.println("¡Error! No existen operadores registrados para atender la llamada.");
      pausar("Presione ENTER para regresar al Menú de Llamadas...");
      return;
    }
    //se elige el operador de la lista que sale en pantalla
    System.out.println("Seleccione el operador que atiende la llamada:");
    for (int i = 0; i < ops.size(); i++) {
      System.out.println("[" + (i + 1) + "] " + ops.get(i).getNombreCompleto());
    }
    int indiceOp = leerOpcion("Seleccione el número de operador: ", 1, ops.size());
    Operador operadorSeleccionado = ops.get(indiceOp - 1);
    System.out.println();
    System.out.println("DATOS DEL CIUDADANO");
    String cedula = leerCedulaValida();
    //se busca si el ciudadano ya existe
    Ciudadano existente = controller.buscarCiudadanoPorCedula(cedula);
    Ciudadano ciudadano;

    if (existente != null) {
      System.out.println("Ciudadano ya registrado en el sistema: " + existente.getNombreCompleto() + ".");
      System.out.println("Se utilizarán los datos existentes. Continuando con el registro de la emergencia...");
      ciudadano = existente;
    }else{
      //si no existen se piden los datos
      String nombres = leerTextoObligatorio("1. Nombres: ");
      String apellidos = leerTextoObligatorio("2. Apellidos: ");
      ciudadano = controller.registrarCiudadanoSiNoExiste(cedula, nombres, apellidos);
    }

    System.out.println();
    System.out.println("DATOS DE LA EMERGENCIA");
    System.out.println("4. Tipo de emergencia:");
    System.out.println("[1] Energía");
    System.out.println("[2] Civil");
    System.out.println("[3] Tránsito");
    System.out.println("[4] Medio ambiente");
    System.out.println("[5] Otros");
    //se saca el tipo de emergencia segun lo que marcan
    int opTipo = leerOpcion("Seleccione una opción (1-5): ", 1, 5);
    String tipoEmergencia = obtenerTextoTipoEmergencia(opTipo);

    String ciudad = leerTextoObligatorio("5. Ciudad: ");
    String barrio = leerTextoObligatorio("6. Barrio: ");
    String detalle = leerTextoObligatorio("7. Detalle: ");

    //se manda a registrar la llamada con todo lo que se pidio
    Llamada nuevaLlamada = controller.registrarLlamada(
      operadorSeleccionado,
      ciudadano,
      tipoEmergencia,
      ciudad,
      barrio,
      detalle
    );

    Persona pCiudadano = ciudadano;
    Persona pOperador = operadorSeleccionado;

    System.out.println();
    System.out.println("PROCESANDO REGISTRO...");
    System.out.println("¡Éxito! La llamada ha sido registrada correctamente:");
    System.out.println("-> Código: " + nuevaLlamada.getCodigo());
    System.out.println("-> Fecha y hora: " + nuevaLlamada.getFechaHora());
    System.out.println("-> Ciudadano: " + pCiudadano.obtenerResumen());
    System.out.println("-> Emergencia: " + tipoEmergencia + " - " + ciudad + ", " + barrio);
    System.out.println("-> Operador: " + pOperador.getNombreCompleto());
    System.out.println("-> Estado: " + nuevaLlamada.getEstado());
    pausar("Presione ENTER para regresar al Menú de Llamadas...");
  }
}
