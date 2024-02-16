package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDA extends Instrucao {
    public LDA() {
        super("LDA", (byte)0x00, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("A").setValorInt(TA); // seta o registrador A para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}
