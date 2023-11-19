package Executor;

import Instrucoes.Instrucoes;

public class Executor {
    private Memoria memoria;
    private Registradores registradores;
    private Instrucoes intrucoes;

    public Executor() {
        this.memoria = new Memoria();
        this.registradores = new Registradores();
        this.intrucoes = new Instrucoes();
    }
    
    public void setPrograma(String caminho)
    {
        limparMemoria();
        // pega txt 
        // le e joga na memoria
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
