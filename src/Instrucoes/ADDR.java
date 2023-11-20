package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class ADDR extends Instrucao {

    public ADDR() {
        super("ADDR", "90");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador A (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int idRegistradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador B (parametro 2)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor(); // valor no reg B

        registradores.getRegistrador(idRegistradorB).setValor(valorRegistradorA + valorRegistradorB); // reg B <- reg A + reg B
    }
    
}