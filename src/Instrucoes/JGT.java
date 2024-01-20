package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JGT extends Instrucao {
    public JGT() {
        super("JGT", "34");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        if (registradores.getRegistradorPorNome("SW").getValor() == 1)
        {
            int enderecoJump = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
            registradores.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        else
        {
            registradores.incrementarPC();
        }
    }
}
