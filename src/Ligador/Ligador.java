package Ligador;

import Montador.*;
import java.util.ArrayList;
import java.util.List;

import Instrucoes.Instrucoes;



public class Ligador {
    private ArrayList<Montador> programas  = new ArrayList<>(); // Lista de programas a serem ligados
    List<Object[]> tabelaDeSimbolosGlobal = new ArrayList<Object[]>();// Tabela de símbolos global
    private Output output = new Output();
    private Instrucoes instrucoes = new Instrucoes();

    public void adicionarPrograma(Montador montador) {
        programas.add(montador);
    }

    public String ligarProgramas() {

        primeiraPassagem();
        segundaPassagem();
        return output.getMachineCodeAsString();
    }

    private void primeiraPassagem() {

        // Adiciona os símbolos do programa 1 à tabela de símbolos global
        for (Object[] entrada : programas.get(0).getDEFTAB()) 
        {
            tabelaDeSimbolosGlobal.add(entrada);
        }

        // Adiciona os símbolos do programa 2 à tabela de símbolos global, somando o tamanho do programa 1
        for (Object[] entrada : programas.get(1).getDEFTAB()) 
        {
            entrada[1] = (int)entrada[1] + programas.get(0).getOutput().getLength();
            tabelaDeSimbolosGlobal.add(entrada);
        }

        // Atualiza a tabela de uso do programa 2, adicionando o tamanho do programa 1
        for (Object[] entrada : programas.get(1).getUSETAB()) 
        {
            entrada[1] = (int)entrada[1] + programas.get(0).getOutput().getLength();
        }
    }

    private void segundaPassagem() {
        // Adiciona o código do programa 1 ao código de saída
        for (String codigo : programas.get(0).getOutput().machineCode) 
        {
            output.machineCode.add(codigo);
        }

        // Adiciona o código do programa 2 ao código de saída
        for (String codigo : programas.get(1).getOutput().machineCode) 
        {
            output.machineCode.add(codigo);
        }

    }

}
