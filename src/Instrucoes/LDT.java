package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDT extends Instrucao {
    public LDT() {
        super("LDT", "74");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        registradores.getRegistradorPorNome("T").setValor(valorMem);

        registradores.incrementarPC();
    }
}