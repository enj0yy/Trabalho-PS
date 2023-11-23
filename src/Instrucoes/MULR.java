package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MULR extends Instrucao {

    public MULR() {
        super("MULR", "98");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC();

        int idRegistradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC();

        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor();
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor();

        registradores.getRegistrador(idRegistradorB).setValor(valorRegistradorA * valorRegistradorB);
    }

}