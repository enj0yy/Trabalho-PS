package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class OR extends Instrucao {

    public OR() {
        super("OR", "44");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        registradores.incrementarPC();
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor();
        valorAcumulator = valorAcumulator | valorMem;

        registradores.getRegistradorPorNome("A").setValor(valorAcumulator);
    }

}