package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instrucao {

    public TIXR() {
        super("TIXR", "B8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValor()) + 1;
        registradores.getRegistradorPorNome("X").setValor(valorRegistradorX);
        
        int registradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorRegistradorA = registradores.getRegistrador(registradorA).getValor();
            
        if(valorRegistradorX == valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if(valorRegistradorX > valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValor(1);
        } else {
            registradores.getRegistradorPorNome("SW").setValor(-1);
        }
    }  
}
