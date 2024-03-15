package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MULR extends Instrucao {

    public MULR() {
        super("MULR", (byte)0x98, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // valor no reg B

        int resultado = valorRegistradorA * valorRegistradorB;

        registradores.getRegistrador(registradoresID[1]).setValorInt(resultado);

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }

}