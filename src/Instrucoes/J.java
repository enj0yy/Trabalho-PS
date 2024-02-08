package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class J extends Instrucao {
    
    public J() {
        super("J", (byte)0x3C, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
    }
    
}
