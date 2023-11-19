package Executor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Executor {
    private Memoria memoria;
    private Registradores registradores;

    public Executor() {
        this.memoria = new Memoria();
        this.registradores = new Registradores();
    }
    
    public void setPrograma(String caminho)
    {
        limparMemoria();
        // pega txt 
        // le e joga na memoria
    }
    
    public void executarPrograma()
    {
        int pc = this.registradores.getRegistrador("PC").getValor();
        
        while (!"000".equals(memoria.getPosicaoMemoria(pc)))
        {
            //executa instrucao
            this.registradores.incrementarPC();
            pc = this.registradores.getRegistrador("PC").getValor();
        }
    }
    
    public void limparMemoria()
    {
        // setar memoria pra 000
    }
}
