package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STS extends Instrucao {

    public STS() {
        super("STS", "7C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorS = registradores.getRegistradorPorNome("S").getValor();
        String valorSHex = Integer.toHexString(valorS);
        
        memoria.setPosicaoMemoria(enderecoMem, valorSHex);
    }  
}
