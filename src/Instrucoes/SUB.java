/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

/**
 *
 * @author mlima
 */
public class SUB extends Instrucao {

    public SUB(String nome, String opcode) {
        super("SUB", "1C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor();
        valorAcumulator -= valorMem;
        registradores.getRegistradorPorNome("A").setValor(valorAcumulator);
    }
}
