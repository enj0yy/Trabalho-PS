package TrabalhoPS;


import Interface.*;
import java.io.IOException;

public class TrabalhoPS {
    static ExecutorInterface executor;
    static MontadorInterface montador;
    static ProcessadorMacrosInterface processadorMacros;
    
    public static void main(String[] args) throws IOException {
        //executor = new ExecutorInterface();
        // montador = new MontadorInterface();
        processadorMacros = new ProcessadorMacrosInterface();
    }
    
}