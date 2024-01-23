package TrabalhoPS;


import Interface.*;
import Montador.Montador;
import java.io.IOException;

public class TrabalhoPS {
    static ExecutorInterface executor;
    
    public static void main(String[] args) throws IOException {
        Montador montador = new Montador();
        montador.montarPrograma(System.getProperty("user.dir")+ "/txtFiles/inputMontador.txt");

        executor = new ExecutorInterface();
    }
    
}
