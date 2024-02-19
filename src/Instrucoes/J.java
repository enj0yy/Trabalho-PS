package Instrucoes;

import java.util.Map;

import Executor.Memoria;
import Executor.Registradores;

public class J extends Instrucao {
    
    public J() {
        super("J", (byte)0x3C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags(memoria.getBytes(registradores.getValorPC(), 2));
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0       
            TA = memoria.getWord(memoria.getWord(TA)); 

        registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
    }
    
}
