package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDX extends Instrucao {
    public LDX() {
        super("LDX", (byte)0x04, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("X").setValorInt(TA); // seta o registrador X para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}