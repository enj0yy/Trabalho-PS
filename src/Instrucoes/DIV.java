package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class DIV extends Instrucao {

    public DIV() {
        super("DIV", "24");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o endereço de memória (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16); // valor armazenado na posição de memoria lida anteriormente

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor(); // valor que está no acumulador
        

        registradores.getRegistradorPorNome("A").setValor(valorAcumulator/valorMem); // regA = regA/valorMem 
        // NOTA: em java, quando se divide um int por um int, o resultado vai ser um int também. Somente retorna a parte inteira da divisao
    }
    
}