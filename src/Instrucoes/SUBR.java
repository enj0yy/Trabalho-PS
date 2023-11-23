package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUBR extends Instrucao {

    public SUBR() {
        super("SUBR", "94");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int registradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        
        int registradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        
        int valorRegistradorA = registradores.getRegistrador(registradorA).getValor();
        int valorRegistradorB = registradores.getRegistrador(registradorB).getValor();
        
        registradores.getRegistrador(registradorB).setValor(valorRegistradorB - valorRegistradorA);
        
        registradores.incrementarPC();
    }
}
