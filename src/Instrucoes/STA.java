package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


public class STA extends Instrucao {

    public STA() {
        super("STA", "0C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorA = registradores.getRegistradorPorNome("A").getValor();
        String valorAHex = Integer.toHexString(valorA);

        memoria.setPosicaoMemoria(enderecoMem, valorAHex);
    }
}