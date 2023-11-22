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
public class SUBR extends Instrucao {

    public SUBR(String nome, String opcode) {
        super("SUBR", "94");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int registradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int registradorB = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorRegistradorA = registradores.getRegistrador(registradorA).getValor();
        int valorRegistradorB = registradores.getRegistrador(registradorB).getValor();
        
        registradores.getRegistrador(registradorB).setValor(valorRegistradorB - valorRegistradorA);
    }
}
