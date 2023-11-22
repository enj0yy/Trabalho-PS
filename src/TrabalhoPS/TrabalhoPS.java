package TrabalhoPS;


import Interface.ExecutorInterface;
import java.io.IOException;

public class TrabalhoPS {
    static ExecutorInterface executor;
    
    public static void main(String[] args) throws IOException {
        executor = new ExecutorInterface();
    }
    
}
