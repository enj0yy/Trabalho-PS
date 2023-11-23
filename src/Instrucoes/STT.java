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
public class STT extends Instrucao {

    public STT(String nome, String opcode) {
        super("STT", "84");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorT = registradores.getRegistradorPorNome("T").getValor();
        String valorTHex = Integer.toHexString(valorT);
        
        memoria.setPosicaoMemoria(enderecoMem, valorTHex);
    }
}
