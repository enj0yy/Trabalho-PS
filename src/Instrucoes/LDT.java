package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDT extends Instrucao {
    public LDT() {
        super("LDT", (byte)0x74, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("T").setValorInt(TA); // seta o registrador T para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}