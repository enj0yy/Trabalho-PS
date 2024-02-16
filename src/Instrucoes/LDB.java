package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDB extends Instrucao {
    public LDB() {
        super("LDB", (byte)0x68, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.getRegistradorPorNome("B").setValorInt(TA); // seta o registrador B para o valor do operando
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}
