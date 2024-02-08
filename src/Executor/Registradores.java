package Executor;

import java.util.HashMap;
import java.util.Map;

public class Registradores {
    private final Map<Integer, Registrador> registradores;

    Registradores() {
        Map<Integer, Registrador> regs = new HashMap<>();

        regs.put(0, new Registrador("A", 0)); // Acumulador - Armazena os dados (carregados e resultantes) das operações da Unid. de Lógica e Aritmética
        regs.put(1, new Registrador("X",1)); // Registrador de Índice - Usado para endereçamento.
        regs.put(2, new Registrador("L",2)); // Registrador de Ligação - A instrução Jump to Subrotine (JSUB) armazena o endereço de retorno nesse registrador.
        regs.put(3, new Registrador("B",3)); // Registrador Base - Usado para endereçamento.
        regs.put(4, new Registrador("S",4)); // Registrador de Uso Geral
        regs.put(5, new Registrador("T",5)); // Registrador de Uso Geral
        regs.put(8, new Registrador("PC",8)); //Program Counter - Mantém o endereço da próxima instrução a ser executada
        
        regs.put(9, new Registrador("SW",9)); 
        // Palavra de Status - Contém várias informações, incluindo código condicional (CC)
        // -1 -> <
        // 0 ->  =
        // 1 ->  >

        registradores = regs;
    }

    public Registrador getRegistrador(int id) {
        return registradores.get(id);
    }

    public Registrador getRegistradorPorNome(String nome) {
        switch(nome) {
            case "A":
                return registradores.get(0);
            case "X":
                return registradores.get(1);
            case "L":
                return registradores.get(2);
            case "B":
                return registradores.get(3);
            case "S":
                return registradores.get(4);
            case "T":
                return registradores.get(5);
            case "PC":
                return registradores.get(8);
            case "SW":
                return registradores.get(9);
        }
        return null;
    }

    public static int getChaveRegistradorPorNome(String nome) {
        switch(nome) {
            case "A":
                return 0;
            case "X":
                return 1;
            case "L":
                return 2;
            case "B":
                return 3;
            case "S":
                return 4;
            case "T":
                return 5;
            case "PC":
                return 8;
            case "SW":
                return 9;
        }
        return -1;
    }

    public void incrementarPC(int valor) {
        registradores.get(8).incrementarValor(valor);
    }

    public int getValorPC() {
        return registradores.get(8).getValorIntSigned();
    }
    
    public void limparRegistradores(){
        getRegistradorPorNome("A").setValorInt(0);
        getRegistradorPorNome("X").setValorInt(0);
        getRegistradorPorNome("L").setValorInt(0);
        getRegistradorPorNome("B").setValorInt(0);
        getRegistradorPorNome("S").setValorInt(0);
        getRegistradorPorNome("T").setValorInt(0);
        getRegistradorPorNome("PC").setValorInt(0);
    }
}