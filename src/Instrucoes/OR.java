package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class OR extends Instrucao {

    public OR() {
        super("OR", (byte)0x44, "3/4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        int resultado = TA | valorAcumulator; // faz a soma

        registradores.getRegistradorPorNome("A").setValorInt(resultado); // armazena o resultado no acumulador

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }

}