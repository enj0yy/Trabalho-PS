package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JEQ extends Instrucao {

    public JEQ() {
        super("JEQ", (byte)0x30, "3/4",3 );
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando

        if (registradores.getRegistradorPorNome("SW").getValorIntSigned() == 0) {
            registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
        } else {
            registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
        }
    }
}
