package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instrucao {
    
    public RSUB() {
        super("RSUB", (byte)0x4C, "1");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] valorL = registradores.getRegistradorPorNome("L").getValor(); // Obtém o valor do registrador L

        registradores.getRegistradorPorNome("PC").setValor(valorL); // Atualiza o contador de programa (PC) com o valor do registrador L

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução

    }

}
