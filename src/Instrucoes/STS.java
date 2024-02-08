package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STS extends Instrucao {

    public STS() {
        super("STS", (byte)0x7C, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int bytesRegA = registradores.getRegistradorPorNome("S").getValorIntSigned(); // retorna o valor armazenado no registrador S
        
        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
        
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }  
}
