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
public class TIXR extends Instrucao {

    public TIXR(String nome, String opcode) {
        super("TIXR", "B8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValor()) + 1;
        registradores.getRegistradorPorNome("X").setValor(valorRegistradorX);
        
        int registradorA = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        registradores.incrementarPC();
        int valorRegistradorA = registradores.getRegistrador(registradorA).getValor();
            
        if(valorRegistradorX == valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if(valorRegistradorX > valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValor(1);
        } else {
            registradores.getRegistradorPorNome("SW").setValor(-1);
        }
    }  
}
