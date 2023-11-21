package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDB extends Instrucao {
     public LDB() {
        super("LDB", "68");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        
        registradores.getRegistradorPorNome("B").setValor(valorMem);
        
        registradores.incrementarPC();
    }
}
