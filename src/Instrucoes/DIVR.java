package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class DIVR extends Instrucao {

    public DIVR() {
        super("DIVR", (byte)0x9C, "2");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // valor no reg B

        int resultado = valorRegistradorA / valorRegistradorB; // realiza a divisão

        registradores.getRegistrador(registradoresID[0]).setValorInt(resultado);

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para 
        // NOTA: em java, quando se divide um int por um int, o resultado vai ser um int também. Somente retorna a parte inteira da divisao
    }
    
}