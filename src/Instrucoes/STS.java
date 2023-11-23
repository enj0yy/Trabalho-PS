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
public class STS extends Instrucao {

    public STS(String nome, String opcode) {
        super("STS", "7C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorS = registradores.getRegistradorPorNome("S").getValor();
        String valorSHex = Integer.toHexString(valorS);
        
        memoria.setPosicaoMemoria(enderecoMem, valorSHex);
    }  
}
