package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instrucao {

    public STCH() {
        super("STCH", "54");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Obtém o valor do registrador A
        int valorRegistradorA = registradores.getRegistradorPorNome("A").getValor();

        // Obtém o byte menos significativo do valor do registrador A
        int byteMenosSignificativo = valorRegistradorA & 0xFF;

        // Armazena o byte menos significativo na posição de memória especificada
        memoria.setPosicaoMemoria(enderecoMem, Integer.toHexString(byteMenosSignificativo));

        // Incrementa o contador de programa
        registradores.incrementarPC();
    }
}
