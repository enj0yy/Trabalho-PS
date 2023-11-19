package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public abstract class Instrucao {
    private final String nome;
    private final String opcode;

    Instrucao(String nome, String opcode) {
        this.nome = nome;
        this.opcode = opcode;
    }

    public abstract void executar(Memoria memoria, Registradores registradores);

    public String getNome() {
        return nome;
    }

    public String getOpcode() {
        return opcode;
    }
}
