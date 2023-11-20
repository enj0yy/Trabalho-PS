package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class COMPR extends Instrucao {

    public COMPR() {
        super("COMPR", "A0");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador A (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int idRegistradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o id do registrador B (parametro 2)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC

        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor(); // valor no reg B

        if (valorRegistradorA == valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValor(0); // SW recebe "igual", pois ValorRegA == valorRegB
        } else if (valorRegistradorA < valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValor(-1); // SW recebe "menor", pois ValorRegA == valorRegB
        } else {
            registradores.getRegistradorPorNome("SW").setValor(1); // SW recebe "maior", pois ValorRegA == valorRegB
        }
    }
    
}