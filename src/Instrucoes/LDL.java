package Instrucoes;

import java.util.Map;

import Executor.Memoria;
import Executor.Registradores;

public class LDL extends Instrucao {
    public LDL() {
        super("LDL", (byte)0x8, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0       
            TA = memoria.getWord(memoria.getWord(TA)); 
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i"))) 
            TA = memoria.getWord(TA);
            
        registradores.getRegistradorPorNome("L").setValorInt(TA); // seta o registrador L para o valor do operando
    }
}