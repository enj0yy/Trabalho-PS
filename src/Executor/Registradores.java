package Executor;

import java.util.HashMap;
import java.util.Map;

public class Registradores {
    private final Map<String, Registrador> registradores;

    Registradores() {
        Map<String, Registrador> regs = new HashMap<>();

        regs.put("A", new Registrador("A")); // Acumulador - Armazena os dados (carregados e resultantes) das operações da Unid. de Lógica e Aritmética
        regs.put("X", new Registrador("X")); // Registrador de Índice - Usado para endereçamento.
        regs.put("L", new Registrador("L")); // Registrador de Ligação - A instrução Jump to Subrotine (JSUB) armazena o endereço de retorno nesse registrador.
        regs.put("B", new Registrador("B")); // Registrador Base - Usado para endereçamento.
        regs.put("S", new Registrador("S")); // Registrador de Uso Geral
        regs.put("T", new Registrador("T")); // Registrador de Uso Geral
        regs.put("PC", new Registrador("PC")); //Program Counter - Mantém o endereço da próxima instrução a ser executada
        regs.put("SW", new Registrador("SW")); // Palavra de Status - Contém várias informações, incluindo código condicional (CC)

        registradores = regs;
    }

    public Registrador getRegistrador(String nome) {
        return registradores.get(nome);
    }

    public void incrementarPC() {
        registradores.get("PC").incrementarValor(1);
    }

    public int getValorPC() {
        return registradores.get("PC").getValor();
    }
}