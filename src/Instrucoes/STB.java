package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STB extends Instrucao {

    public STB() {
        super("STB", "78");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        
        int valorB = registradores.getRegistradorPorNome("B").getValor();
        String valorBHex = Integer.toHexString(valorB);

        memoria.setPosicaoMemoria(enderecoMem, valorBHex);
        
        registradores.incrementarPC();
    }
}