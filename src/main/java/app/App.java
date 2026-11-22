
import controller.SistemaController;
import view.ConsolaView;


public class App {
  public static void main(String[] args){
    SistemaController controller= new SistemaController();
    ConsolaView vista= new ConsolaView(controller);
    vista.iniciar();
  }
}
