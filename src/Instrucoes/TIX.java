package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instrucao {

    public TIX() {
        super("TIX", "2C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValor()) + 1;
        registradores.getRegistradorPorNome("X").setValor(valorRegistradorX);
        
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        valorRegistradorX = registradores.getRegistradorPorNome("X").getValor();

        if (valorRegistradorX == valorMem) {
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if (valorRegistradorX < valorMem) {
            registradores.getRegistradorPorNome("SW").setValor(-1);
        } else {
            registradores.getRegistradorPorNome("SW").setValor(1);
        }
    }  
}
