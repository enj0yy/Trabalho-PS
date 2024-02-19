package Instrucoes;

import java.util.Map;

import Executor.Memoria;
import Executor.Registradores;

public class LDB extends Instrucao {
    public LDB() {
        super("LDB", (byte)0x68, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0       
            TA = memoria.getWord(memoria.getWord(TA)); 
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i"))) 
            TA = memoria.getWord(TA);
            
        registradores.getRegistradorPorNome("B").setValorInt(TA); // seta o registrador B para o valor do operando
    }
}
