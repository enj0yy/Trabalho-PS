package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JEQ extends Instrucao {

    public JEQ() {
        super("JEQ", "30");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        if (registradores.getRegistradorPorNome("SW").getValor() == 0)
        {
            int enderecoJump = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
            registradores.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        registradores.incrementarPC();
    }
    
}
