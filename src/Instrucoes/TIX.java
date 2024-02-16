package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instrucao {

    public TIX() {
        super("TIX", (byte)0x2C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando
        
        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValorIntSigned()) + 1;

        registradores.getRegistradorPorNome("X").setValorInt(valorRegistradorX);
        
        int valorMem = memoria.getWord(TA);

        if (valorRegistradorX == valorMem) {
            registradores.getRegistradorPorNome("SW").setValorInt(0);
        } else if (valorRegistradorX < valorMem) {
            registradores.getRegistradorPorNome("SW").setValorInt(-1);
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(1);
        }
        
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }  
}
