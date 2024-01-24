package TrabalhoPS;


import Interface.*;
import java.io.IOException;

public class TrabalhoPS {
    //static ExecutorInterface executor;
    static MontadorInterface montador;
    
    public static void main(String[] args) throws IOException {
        montador = new MontadorInterface();
    }
    
}