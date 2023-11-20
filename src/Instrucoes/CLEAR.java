package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class CLEAR extends Instrucao {

    public CLEAR() {
        super("CLEAR", "4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC())); // pegando o id do registrador (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        registradores.getRegistrador(idRegistradorA).setValor(0); // reg A <- 0
    }
    
}