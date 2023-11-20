package Executor;
import javax.swing.JFrame;

public class ExecutorInterface extends JFrame {
    private Executor executor;
    
    public ExecutorInterface() { 
        super("Executor");
        executor = new Executor();
        
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        //construir interface
        //perguntar caminho do txt
        //chamar executor quando clicar no botao rodar
        executor.setPrograma("caminho");
        executor.executarPrograma();
    }
}
