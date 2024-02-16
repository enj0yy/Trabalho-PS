package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class COMP extends Instrucao {

    public COMP() {
        super("COMP", (byte)0x28, "3/4",3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        if (valorAcumulator == TA) {
            registradores.getRegistradorPorNome("SW").setValorInt(0); // SW recebe "igual", pois ValorRegA == valorMem
        } else if (valorAcumulator < TA) {
            registradores.getRegistradorPorNome("SW").setValorInt(-1); // SW recebe "menor", pois ValorRegA < valorMem
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(1); // SW recebe "maior", pois ValorRegA > valorMem
        }

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
        
    }
    
}