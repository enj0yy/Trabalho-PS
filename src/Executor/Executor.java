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
        limparMemoria();
        // pega txt 
        File file = new File(caminho);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        int pos = 0;
        // le e joga na memoria
        while ((str = br.readLine()) != null){
            String[] splited = str.split("\\s+");
            for (int i = 0; i < splited.length; i++){
                memoria.setPosicaoMemoria(pos, splited[i]);
                pos++;
            }
        }
    }
    
    public void executarPrograma()
    {
        int pc = this.registradores.getRegistrador(8).getValor();
        String conteudoMemoria = memoria.getPosicaoMemoria(pc);
                
        while (!"000".equals(conteudoMemoria))
        {
            intrucoes.getIntrucao(conteudoMemoria).executar(memoria, registradores);
            
            this.registradores.incrementarPC();
            pc = this.registradores.getRegistrador(8).getValor();
            conteudoMemoria = memoria.getPosicaoMemoria(pc);
        }
    }
    
    public void limparMemoria()
    {
        // setar memoria pra 000
    }
}
