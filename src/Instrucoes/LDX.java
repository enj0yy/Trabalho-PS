package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDX extends Instrucao {
    public LDX() {
        super("LDX", "04");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        registradores.getRegistradorPorNome("X").setValor(valorMem);

        registradores.incrementarPC();
    }
}