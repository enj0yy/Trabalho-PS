package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class DIV extends Instrucao {

    public DIV() {
        super("DIV", (byte)0x24, "3/4",3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        int resultado = TA / valorAcumulator; // faz a operação de divisão

        registradores.getRegistradorPorNome("A").setValorInt(resultado); // armazena o resultado no acumulador

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
        // NOTA: em java, quando se divide um int por um int, o resultado vai ser um int também. Somente retorna a parte inteira da divisao
    }
    
}