package Executor;

import Instrucoes.Instrucoes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Executor {
    private Memoria memoria;
    private Registradores registradores;
    private Instrucoes intrucoes;

    public Executor() {
        this.memoria = new Memoria();
        this.registradores = new Registradores();
        this.intrucoes = new Instrucoes();
    }
    
    public void setPrograma(String caminho) throws FileNotFoundException, IOException
    {
        memoria.limparMemoria();
        
        File file = new File(caminho);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        int pos = 0;
        while ((str = br.readLine()) != null){
            String[] splited = str.split("\\s+");
            for (int i = 0; i < splited.length; i++){
                memoria.setPosicaoMemoria(pos, splited[i]);
                pos++;
            }
        }
        br.close();
    }
    
    public void executarPrograma()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValor();
        String opcode = memoria.getPosicaoMemoria(pc);
               
        while (!"00".equals(opcode))
        {
            registradores.incrementarPC(); // incrementa PC
            intrucoes.getInstrucao(opcode).executar(memoria, registradores); // executa a instrução com o opcode lido
            
            pc = this.registradores.getRegistradorPorNome("PC").getValor();
            opcode = memoria.getPosicaoMemoria(pc);  
        } 
    }
    
}
