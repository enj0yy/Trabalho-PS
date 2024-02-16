package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STB extends Instrucao {

    public STB() {
        super("STB", (byte)0x78, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int bytesRegA = registradores.getRegistradorPorNome("B").getValorIntSigned(); // retorna o valor armazenado no registrador B
        
        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
        
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}