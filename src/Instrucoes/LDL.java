package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDL extends Instrucao {
    public LDL() {
        super("LDL", "8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        registradores.getRegistradorPorNome("L").setValor(valorMem);

        registradores.incrementarPC();
    }
}