package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class J extends Instrucao {
    
    public J() {
        super("J", "3C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoJump = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.getRegistradorPorNome("PC").setValor(enderecoJump);
    }
    
}
