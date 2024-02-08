package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instrucao {

    public STCH() {
        super("STCH", (byte)0x54, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int byteA = registradores.getRegistradorPorNome("A").getValor()[2];

        memoria.setWord(TA, byteA); // guarda o primeiro byte do registrador A na posição de memória espeçificada por TA

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}
