package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class COMP extends Instrucao {

    public COMP() {
        super("COMP", "28");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o endereço de memória (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16); // valor armazenado na posição de memoria lida anteriormente

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor(); // valor do acumulador

        if (valorAcumulator == valorMem) {
            registradores.getRegistradorPorNome("SW").setValor(0); // SW recebe "igual", pois ValorRegA == valorMem
        } else if (valorAcumulator < valorMem) {
            registradores.getRegistradorPorNome("SW").setValor(-1); // SW recebe "menor", pois ValorRegA < valorMem
        } else {
            registradores.getRegistradorPorNome("SW").setValor(1); // SW recebe "maior", pois ValorRegA > valorMem
        }

        
    }
    
}