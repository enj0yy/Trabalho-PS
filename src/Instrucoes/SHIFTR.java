package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR  extends Instrucao {

    public SHIFTR() {
        super("SHIFTL", "A8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();

        int quantidadeDeslocamento = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();

        // Obtém o valor do registrador a ser deslocado à direita
        int valorRegistrador = registradores.getRegistrador(idRegistradorA).getValor();

        // Realiza o deslocamento à direita (shift right) pela quantidade especificada
        int resultado = valorRegistrador >>> quantidadeDeslocamento; // Usando o operador ">>>" para deslocamento à direita lógico

        // Atualiza o valor do registrador com o resultado do deslocamento à direita
        registradores.getRegistrador(idRegistradorA).setValor(resultado);

        // Incrementa o contador de programa
        registradores.incrementarPC();

    }



}
