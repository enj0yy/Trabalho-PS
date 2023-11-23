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
public class STX extends Instrucao {
    public STX(String nome, String opcode) {
        super("STX", "10");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorX = registradores.getRegistradorPorNome("X").getValor();
        String valorXHex = Integer.toHexString(valorX);
        
        memoria.setPosicaoMemoria(enderecoMem, valorXHex);
    }
}
