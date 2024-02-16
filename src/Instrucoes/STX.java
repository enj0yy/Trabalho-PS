package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STX extends Instrucao {
    
    public STX() {
        super("STX", (byte)0x10, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int bytesRegA = registradores.getRegistradorPorNome("X").getValorIntSigned(); // retorna o valor armazenado no registrador X
        
        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
        
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}
