package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STX extends Instrucao {
    
    public STX() {
        super("STX", "10");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorX = registradores.getRegistradorPorNome("X").getValor();
        String valorXHex = Integer.toHexString(valorX);
        
        memoria.setPosicaoMemoria(enderecoMem, valorXHex);
    }
}
