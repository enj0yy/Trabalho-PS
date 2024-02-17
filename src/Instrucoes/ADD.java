package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class ADD extends Instrucao {

    public ADD() {
        super("ADD", (byte)0x18, "3/4",3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        int resultado = TA + valorAcumulator; // faz a soma

        registradores.getRegistradorPorNome("A").setValorInt(resultado); // armazena o resultado no acumulador
    }
    
}
