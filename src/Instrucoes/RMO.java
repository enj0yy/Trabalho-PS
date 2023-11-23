package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instrucao {

    public RMO() {
        super("RMO", "AC");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC();

        int idRegistradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC();

        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor();

        registradores.getRegistrador(idRegistradorB).setValor(valorRegistradorA);
    }
}