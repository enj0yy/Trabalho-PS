package TrabalhoPS;


import Interface.ExecutorInterface;
import Montador.Montador;
import java.io.IOException;

public class TrabalhoPS {
    static ExecutorInterface executor;
    
    public static void main(String[] args) throws IOException {
        Montador montador = new Montador();
        montador.montarPrograma(System.getProperty("user.dir")+ "\\inputMontador.txt");

        executor = new ExecutorInterface();
    }
    
}
