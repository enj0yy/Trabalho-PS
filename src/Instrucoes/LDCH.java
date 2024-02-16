package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDCH extends Instrucao {

    public LDCH() {
        super("LDCH", (byte)0x50, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando

        byte[] bytesA = registradores.getRegistradorPorNome("A").getValor();

        bytesA[2] = (byte)(TA & 0xFF);

        registradores.getRegistradorPorNome("A").setValor(bytesA); // A [byte mais à direita] ← (m) 

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução

    }
    
}
