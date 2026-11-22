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
  //metodo para cambiar el estado de una llamada pendiente
  private void actualizarEstadoLlamada(){
    System.out.println();
    System.out.println("[Menú Principal > 1. Llamadas > 1.3. Actualizar estado]");

    ArrayList<Llamada> pendientes = controller.obtenerLlamadasPendientes();
    if (pendientes.isEmpty()) {
      System.out.println("No hay llamadas pendientes por actualizar.");
      pausar("Presione ENTER para regresar al Menú de Llamadas...");
      return;
    }
    //se muestran las llamadas pendientes
    System.out.println("Llamadas pendientes:");
    mostrarSeparador();
    System.out.println("CÓDIGO | CIUDADANO | TIPO | ESTADO ACTUAL");
    for (Llamada ll : pendientes) {
      System.out.println(ll.getCodigo() + " | " + ll.getCiudadano().getNombreCompleto()
                         + " | " + ll.getTipoEmergencia() + " | " + ll.getEstado());
    }
    mostrarSeparador();

    String codigo = leerTexto("> Ingrese el código de la llamada (o ENTER para volver): ");
    if (codigo.isEmpty()) {
      return;
    }
    //se valida si existe o si ya esta siendo atendida antes de avanzar de estado
    Llamada seleccionada = controller.buscarLlamadaPorCodigo(codigo);
    if (seleccionada == null) {
      System.out.println("¡Error! No se encontró una llamada con el código " + codigo + ".");
    } else if(seleccionada.getEstado().equals("Atendida")){
      System.out.println("¡Error! La llamada ya está en estado Atendida y no puede modificarse.");
    } else{
      String estadoActual = seleccionada.getEstado();
      String nuevoEstado = seleccionada.obtenerSiguienteEstado();
      System.out.println("El estado actual es \"" + estadoActual + "\". El nuevo estado será \"" + nuevoEstado + "\".");
      if (leerConfirmacion("¿Confirmar el cambio? (S/N): ")) {
        controller.avanzarEstadoLlamada(seleccionada);
        System.out.println("¡Éxito! La llamada " + seleccionada.getCodigo() + " ahora está \"" + seleccionada.getEstado() + "\".");
      } else{
        System.out.println("Operación cancelada.");
      }
    }
    pausar("Presione ENTER para regresar al Menú de Llamadas...");
  }
  //esto es para anular una llamada
  private void anularLlamada(){
    System.out.println();
    System.out.println("[Menú Principal > 1. Llamadas > 1.4. Anular llamada]");

    ArrayList<Llamada> anulables = controller.obtenerLlamadasAnulablesHoy();
    if (anulables.isEmpty()) {
      System.out.println("No existen llamadas en estado \"Registrada\" del día de hoy ("
              + controller.getFechaActualSistema() + ") para anular.");
      pausar("Presione ENTER para regresar al Menú de Llamadas...");
      return;
    }
    //se enlistan las que se pueden anular
    System.out.println("Llamadas que se pueden anular (Registradas hoy):");
    mostrarSeparador();
    System.out.println("CÓDIGO | FECHA Y HORA | CIUDADANO | TIPO");
    for (Llamada ll : anulables) {
      System.out.println(ll.getCodigo() + " | " + ll.getFechaHora() + " | "
              + ll.getCiudadano().getNombreCompleto() + " | " + ll.getTipoEmergencia());
    }
    mostrarSeparador();
    String codigo = leerTexto("> Ingrese el código de la llamada que desea anular (o ENTER para volver): ");
    if (codigo.isEmpty()) {
      return;
    }
    Llamada seleccionada = controller.buscarLlamadaPorCodigo(codigo);
    if (seleccionada == null) {
      System.out.println("¡Error! No se encontró una llamada con ese código.");
    } else if (!seleccionada.getEstado().equals("Registrada")|| !seleccionada.getSoloFecha().equals(controller.getFechaActualSistema())) {
      System.out.println("¡Error! Solo se pueden anular llamadas en estado \"Registrada\" con fecha de hoy.");
    } else{
      //se pide confirmacion antes de eliminar
      System.out.println("¡ADVERTENCIA: ESTA ACCIÓN NO SE PUEDE DESHACER!");
      System.out.println("[Llamada seleccionada]: " + seleccionada.getCodigo() + " - "
                         + seleccionada.getTipoEmergencia() + " en " + seleccionada.getCiudad()
                         + " (" + seleccionada.getBarrio() + ")");
                 
      if (leerConfirmacion("¿Confirmar anulación? (S/N): ")) {
        System.out.println("Eliminando registro...");
        controller.anularLlamada(seleccionada);
        System.out.println("¡Éxito! La llamada " + seleccionada.getCodigo() + " ha sido anulada del sistema.");
      } else{
        System.out.println("Anulacion cancelada");
      }
    }
    pausar("Presione ENTER para regresar al Menu de Llamadas...")
  }
  //menu de operadores con su tabla y opciones
  private void menuOperadores() {
    boolean regresar = false;
    while (!regresar){
      System.out.println();
      System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
      System.out.println("[Menú Principal > 2. Administrar operadores]");
      System.out.println("Listado actual de operadores registrados:");
      mostrarSeparador();
      System.out.println("CÓDIGO | NOMBRE COMPLETO | TURNO | LLAMADAS ATENDIDAS");
      for (Operador op : controller.getOperadores()) {
        int totalLlamadas = controller.contarLlamadasDeOperador(op);
        System.out.println(op.getCodigo() + " | " + op.getNombreCompleto() + " | " + op.getTurno() + " | " + totalLlamadas);
      }
      mostrarSeparador();
      System.out.println("¿Qué acción desea realizar?");
      System.out.println("[1] Agregar un nuevo operador");
      System.out.println("[2] Eliminar un operador existente");
      System.out.println("[3] Regresar al Menú Principal");

      int opcion = leerOpcion("> Seleccione una opción (1-3): ", 1, 3);
      //se llama al metodo segun la opcion
      switch (opcion) {
        case 1:
          agregarOperador();
          break;
        case 2:
          eliminarOperador();
          break;
        case 3;
          regresar=true;
          break;
        default:
          break;
      }
    }
  }
  //se pide los datos para crear un operador nuevo
  private void agregarOperador() {
    System.out.println();
    System.out.println("[Administrar operadores > Agregar operador]");
    String nombres = leerTextoObligatorio("1. Nombres: ");
    String apellidos = leerTextoObligatorio("2. Apellidos: ");
    System.out.println("3. Turno:");
    System.out.println("[1] Matutino");
    System.out.println("[2] Vespertino");
    System.out.println("[3] Nocturno");
    int opTurno = leerOpcion("Seleccione una opción (1-3): ", 1, 3);
    String turno = obtenerTextoTurno(opTurno);
    String email = leerTexto("4. Email (opcional, ENTER para omitir): ");

    Operador nuevo = controller.agregarOperador(nombres, apellidos, turno, email);
    System.out.println("¡Éxito! El operador " + nuevo.getNombreCompleto()
                       + " ha sido registrado con el código " + nuevo.getCodigo() + ".");
    pausar("Presione ENTER para actualizar y volver a Administrar operadores...");
  }
  //se borra operador solo si no tiene llamadas atendidad
  private void eliminarOperador() {
    System.out.println();
    System.out.println("[Administrar operadores > Eliminar operador]");

    if (controller.getOperadores().isEmpty()) {
      System.out.println("No hay operadores registrados.");
      pausar("Presione ENTER para continuar...");
      return;
    }
    String codigo = leerTexto("> Ingrese el código del operador que desea eliminar (o ENTER para volver): ");
    if (codigo.isEmpty()) {
      return;
    }
    Operador op = controller.buscarOperadorPorCodigo(codigo);
    if (op == null) {
      System.out.println("¡Error! No se encontró un operador con el código " + codigo + ".");
    } else{
      int atendidas = controller.contarLlamadasDeOperador(op);
      if (atendidas > 0) {
        System.out.println("¡No se puede eliminar! El operador " + op.getNombreCompleto()
                           + " tiene " + atendidas + " llamada(s) registrada(s).");
      } else{
        if (leerConfirmacion("¿Está seguro de que desea eliminar a " + op.getNombreCompleto() + "? (S/N): ")) {
          controller.eliminarOperador(op);
          System.out.println("¡Éxito! El registro del operador ha sido removido.");
        }else{
          System.out.println("Eliminación cancelada.");
        }
      }
    }
    pausar("Presione ENTER para actualizar y volver a Administrar operadores...");
  }
  //menu para ver ciudadanos y su historial
  private void menuCiudadanos() {
    boolean regresar = false;
    while (!regresar) {
      System.out.println();
      System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
      System.out.println("[Menú Principal > 3. Administrar ciudadanos]");
      System.out.println("Listado de ciudadanos registrados:");
      mostrarSeparador();
      System.out.println("CÉDULA | NOMBRE COMPLETO | TOTAL LLAMADAS");
      for (Ciudadano c : controller.getCiudadanos()) {
        int total = controller.contarLlamadasDeCiudadano(c);
        System.out.println(c.getCedula() + " | " + c.getNombreCompleto() + " | " + total);
      }
      mostrarSeparador();
      
      System.out.println("¿Qué acción desea realizar?");
      System.out.println("[1] Consultar historial de llamadas de un ciudadano");
      System.out.println("[2] Regresar al Menú Principal");

      int opcion = leerOpcion("> Seleccione una opción (1-2): ", 1, 2);

      switch (opcion) {
        case 1:
          consultarHistorialCiudadano();
          break;
        case 2:
          regresar=true;
          break;
        default:
          break;
      }
    }
  }
  //muestra las llamadas que ha hecho cada ciudadano por su cedula
  private void consultarHistorialCiudadano() {
    String cedula = leerTexto("> Ingrese la cédula del ciudadano: ");
    Ciudadano ciudadano = controller.buscarCiudadanoPorCedula(cedula);

    if (ciudadano == null) {
      System.out.println("¡Error! No se encontró ningún ciudadano registrado con la cédula " + cedula + ".");
    } else{
      System.out.println();
      System.out.println("HISTORIAL DE LLAMADAS - " + ciudadano.obtenerResumen());
      mostrarSeparador();
      System.out.println("CÓDIGO | FECHA Y HORA | TIPO | BARRIO | ESTADO");
      ArrayList<Llamada> historial = controller.obtenerHistorialCiudadano(ciudadano);
      if (historial.isEmpty()) {
        System.out.println("Este ciudadano no tiene llamadas activas en el historial.");
      } else{
        for (Llamada ll : historial) {
          System.out.println(ll.getCodigo() + " | " + ll.getFechaHora() + " | "
                             + ll.getTipoEmergencia() + " | " + ll.getBarrio() + " | " + ll.getEstado());
        }
      }
      mostrarSeparador();
    }
    pausar("Presione ENTER para regresar a Administrar ciudadanos...");
  }
  //menu para ver y agregar notas de seguimiento de una llamada
  private void menuNotasSeguimiento() {
    System.out.println();
    System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Versión 1.0)");
    System.out.println("[Menú Principal > 4. Notas de seguimiento]");

    String codigo = leerTexto("> Ingrese el código de la llamada (o ENTER para volver): ");
      if (codigo.isEmpty()) {
        return;
      }
    Llamada llamada = controller.buscarLlamadaPorCodigo(codigo);
    if (llamada == null) {
      System.out.println("¡Error! No existe una llamada con el código " + codigo + ".");
      pausar("Presione ENTER para regresar al Menú Principal...");
      return;
    }
    boolean regresar = false;
    while (!regresar) {
      System.out.println();
      System.out.println("Notas de la llamada " + llamada.getCodigo() + " (Ordenadas desde la más reciente):");
      mostrarSeparador();
      //se traen las notas ya ordenadas de la mas nueva a la mas vieja
      ArrayList<NotaSeguimiento> notas = controller.obtenerNotasOrdenadasRecientes(llamada);
      if (notas.isEmpty()) {
        System.out.println("Esta llamada aún no tiene notas de seguimiento.");
      } else{
        System.out.println("FECHA Y HORA | OPERADOR | NOTA");
        for (NotaSeguimiento nota : notas) {
          System.out.println(nota.toString());
        }
      }
      mostrarSeparador();
      
      System.out.println("¿Qué acción desea realizar?");
      System.out.println("[1] Añadir una nueva nota");
      System.out.println("[2] Regresar al Menú Principal");

      int opcion = leerOpcion("> Seleccione una opción (1-2): ", 1, 2);

      switch (opcion) {
        case 1:
          anadirNotaSeguimiento(llamada);
          break;
        case 2:
          regresar=true;
          break;
        default
          break;
      }
    }
  }
  //se guarda una nota nueva ligada a un operador y a la llamada
  private void anadirNotaSeguimiento(Llamada llamada) {
    System.out.println();
        System.out.println("[Notas de seguimiento > Añadir nota]");

        ArrayList<Operador> ops = controller.getOperadores();
        if (ops.isEmpty()) {
          System.out.println("¡Error! No hay operadores registrados.");
          return;
        }
    System.out.println("Seleccione el operador que registra la nota:");
    for (int i = 0; i < ops.size(); i++) {
      System.out.println("[" + (i + 1) + "] " + ops.get(i).getNombreCompleto());
    }
    int indiceOp = leerOpcion("Seleccione el número de operador: ", 1, ops.size());
    Operador operadorSeleccionado = ops.get(indiceOp - 1);

    String textoNota = leerTextoObligatorio("> Ingrese el texto de la nota: ");
    NotaSeguimiento nuevaNota = controller.agregarNotaSeguimiento(llamada, operadorSeleccionado, textoNota);

    System.out.println("GUARDANDO NOTA...");
    System.out.println("¡Éxito! La nota ha sido guardada en la llamada " + llamada.getCodigo() + ":");
    System.out.println("-> Registro: " + nuevaNota.getFechaHora());
    System.out.println("-> Operador: " + operadorSeleccionado.getNombreCompleto());
    pausar("Presione ENTER para actualizar y volver a Notas de seguimiento...");
  }
  // para separar tablas en consola
  private void mostrarSeparador(){
    System.out.println("-------------------------------------------------------------------------");
  }
  // pausa para que den enter
  private void pausar(String mensaje) {
    System.out.print(mensaje);
    sc.nextLine();
  }
  // lee texto normal sin espacios extra
  private String leerTexto(String mensaje) {
    System.out.print(mensaje);
    return sc.nextLine().trim();
  }
  //no deja avanzar si se deja el campo vacio
  private String leerTextoObligatorio(String mensaje) {
    String entrada = "";
    while (entrada.isEmpty()) {
      System.out.print(mensaje);
      entrada = sc.nextLine().trim();
      if (entrada.isEmpty()) {
        System.out.println("¡Error! Este campo es obligatorio y no puede quedar vacío.");
      }
    }
    return entrada;
  }
  //revisa caracter a caracter para ver si los caracteres son numeros
  private boolean esNumeroEntero(String texto) {
    if (texto == null || texto.isEmpty()) {
      return false;
    }
    for (int i = 0; i < texto.length(); i++) {
      char c = texto.charAt(i);
      if (c < '0' || c > '9') {
        return false;
      }
    }
    return true;
  }
  //lee un numero dentro de un rango min y max
  private int leerOpcion(String mensaje, int min, int max) {
    int opcion = -1;
    boolean valido = false;
    while (!valido) {
      System.out.print(mensaje);
      String texto = sc.nextLine().trim();
      if (esNumeroEntero(texto)) {
        opcion = Integer.parseInt(texto);
        if (opcion >= min && opcion <= max) {
          valido = true;
        } else{
          System.out.println("¡Error! Opción fuera de rango (" + min + "-" + max + "). Intente nuevamente.");
        }
      }else{
        System.out.println("¡Error! Debe ingresar un número entero válido.");
      }
    }
    return opcion;
  }
  //pide s o n para confirmar
  private boolean leerConfirmacion(String mensaje) {
    while (true) {
      System.out.print(mensaje);
      String resp = sc.nextLine().trim();
      if (resp.equalsIgnoreCase("S")) {
        return true;
      } else if (resp.equalsIgnoreCase("N")) {
        return false;
      } else{
        System.out.println("¡Error! Respuesta inválida. Ingrese S para Sí o N para No.");
      }
    }
  }
  //valida la cedula revisando que tenga 10 numeros
  private String leerCedulaValida() {
    while (true) {
      String cedula = leerTexto("3. Cédula de identidad (10 dígitos, ej. 0912345678): ");
      if (cedula.length() == 10 && esNumeroEntero(cedula){
        return cedula;
      }
      System.out.println("¡Error! La cédula debe contener exactamente 10 dígitos numéricos. Intente nuevamente.");
    }
  }
  
}
