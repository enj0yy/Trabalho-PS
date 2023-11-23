package Instrucoes;
import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL  extends Instrucao {

        public SHIFTL() {
            super("SHIFTL", "A4");
        }

        @Override
        public void executar(Memoria memoria, Registradores registradores) {
            int idRegistradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
            registradores.incrementarPC();
            int quantidadeDeslocamento = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
            registradores.incrementarPC();

            // Obtém o valor do registrador a ser deslocado à esquerda
            int valorRegistrador = registradores.getRegistrador(idRegistradorA).getValor();

            // Realiza o deslocamento à esquerda (shift left) pela quantidade especificada
            int resultado = valorRegistrador << quantidadeDeslocamento;

            // Atualiza o valor do registrador com o resultado do deslocamento à esquerda
            registradores.getRegistrador(idRegistradorA).setValor(resultado);

            // Incrementa o contador de programa
            registradores.incrementarPC();

        }



}
