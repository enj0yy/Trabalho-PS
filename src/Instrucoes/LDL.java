package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDL extends Instrucao {
    public LDL() {
        super("LDL", (byte)0x8, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("L").setValorInt(TA); // seta o registrador L para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}