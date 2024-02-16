package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR  extends Instrucao {

    public SHIFTR() {
        super("SHIFTL", (byte)0xA8, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned() + 1; // valor no reg A, +1 pois r1 = n-1
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned() + 1; // valor no reg B, +1 pois r1 = n-1

        int resultado = ((valorRegistradorA >> valorRegistradorB) & 0xFFFFFF); // Deslocamento circular a direita preservando o sinal

        registradores.getRegistrador(registradoresID[0]).setValorInt(resultado);

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução

    }



}
