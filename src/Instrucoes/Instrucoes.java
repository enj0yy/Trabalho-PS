/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucoes;

import java.util.HashMap;
import java.util.Map;


public class Instrucoes {
    private final Map<String, Instrucao> intrucoes;

    public Instrucoes() {
        Map<String, Instrucao> inst = new HashMap<>();

        inst.put("18", new ADD());

        intrucoes = inst;
    }

    public Instrucao getIntrucao(String opcode) {
        return intrucoes.get(opcode);
    }
}
