package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instrucao {
    
    public RSUB() {
        super("RSUB", "4C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int valorL = registradores.getRegistradorPorNome("L").getValor(); // Obtém o valor do registrador L

        // Atualiza o contador de programa (PC) com o valor do registrador L
        registradores.getRegistradorPorNome("PC").setValor(valorL);

        // Incrementa o contador de programa
        registradores.incrementarPC();

    }

}
