package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class DIVR extends Instrucao {

    public DIVR() {
        super("DIVR", "9C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador A (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int idRegistradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador B (parametro 2)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor(); // valor no reg B

        registradores.getRegistrador(idRegistradorB).setValor(valorRegistradorA/valorRegistradorB); // reg B <- reg A / reg B
        // NOTA: em java, quando se divide um int por um int, o resultado vai ser um int também. Somente retorna a parte inteira da divisao
    }
    
}