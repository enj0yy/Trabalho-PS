package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STT extends Instrucao {

    public STT() {
        super("STT", "84");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        
        int valorT = registradores.getRegistradorPorNome("T").getValor();
        String valorTHex = Integer.toHexString(valorT);
        
        memoria.setPosicaoMemoria(enderecoMem, valorTHex);
        
        registradores.incrementarPC();
    }
}
