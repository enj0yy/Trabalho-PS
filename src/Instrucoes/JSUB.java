package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JSUB extends Instrucao {
    public JSUB() {
        super("JSUB", "48");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoRetorno = registradores.getValorPC(); 
        registradores.getRegistradorPorNome("L").setValor(enderecoRetorno);
        
        int enderecoJump = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.getRegistradorPorNome("PC").setValor(enderecoJump);
        
        registradores.incrementarPC();
    }
}
