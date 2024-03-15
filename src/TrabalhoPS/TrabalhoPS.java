package TrabalhoPS;


import Interface.*;
import java.io.IOException;

public class TrabalhoPS {
    static ExecutorInterface executor;
    static MontadorInterface montador;
    static ProcessadorMacrosInterface processadorMacros;
    static SICXE sicXE;
    
    public static void main(String[] args) throws IOException {
        sicXE = new SICXE();
    }
    
}