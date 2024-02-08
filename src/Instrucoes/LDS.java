package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instrucao {
    public LDS() {
        super("LDS", (byte)0x6C, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("S").setValorInt(TA); // seta o registrador S para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}