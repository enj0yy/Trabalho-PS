package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JLT extends Instrucao {
    public JLT() {
        super("JLT", (byte)0x38, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        if (registradores.getRegistradorPorNome("SW").getValorIntSigned() == -1) {
            registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
        } else {
            registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
        }
    }
}
