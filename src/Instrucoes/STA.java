package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


public class STA extends Instrucao {

    public STA() {
        super("STA", (byte)0x0C, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando

        int bytesRegA = registradores.getRegistradorPorNome("A").getValorIntSigned(); // retorna o valor armazenado no registrador A
        
        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
        
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}