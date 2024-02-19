package Instrucoes;

import java.util.Map;

import Executor.Memoria;
import Executor.Registradores;

public class JSUB extends Instrucao {
    public JSUB() {
        super("JSUB", (byte)0x48, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0       
            TA = memoria.getWord(memoria.getWord(TA)); 

        int enderecoRetorno = registradores.getValorPC();
        registradores.getRegistradorPorNome("L").setValorInt(enderecoRetorno); // seta L para o endereço de retorno

        registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
    }
}
