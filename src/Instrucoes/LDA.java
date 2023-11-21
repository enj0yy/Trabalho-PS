package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDA extends Instrucao {
     public LDA() {
        super("LDA", "0");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        
        registradores.getRegistradorPorNome("A").setValor(valorMem);
        
        registradores.incrementarPC();
    }
}
