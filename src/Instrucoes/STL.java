package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STL extends Instrucao {

    public STL() {
        super("STL", "14");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorL = registradores.getRegistradorPorNome("L").getValor();
        String valorLHex = Integer.toHexString(valorL);

        memoria.setPosicaoMemoria(enderecoMem, valorLHex);
    }
}