/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

/**
 *
 * @author Graziele
 */
public class JGT extends Instrucao {
    public JGT() {
        super("JGT", "34");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        if (registradores.getRegistradorPorNome("SW").getValor() == 1)
        {
            int enderecoJump = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
            registradores.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        registradores.incrementarPC();
    }
}
