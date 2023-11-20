/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class ADD extends Instrucao {

    public ADD() {
        super("ADD", "18");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // pegando o endereço de memória (parametro 1)
        registradores.incrementarPC(); // apos ler o parametro, incrementar o PC
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16); // valor armazenado na posição de memoria lida anteriormente

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor(); // valor que está no acumulador
        valorAcumulator += valorMem;

        registradores.getRegistradorPorNome("A").setValor(valorAcumulator); // Acumulador recebe Acumulador + Valor na memória
    }
    
}
