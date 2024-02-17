package Instrucoes;

import java.util.HashMap;
import java.util.Map;

import Executor.Memoria;
import Executor.Registradores;

public abstract class Instrucao {
    private final String nome;
    private final byte opcode;
    private final String formato;
    private final int length;

    Instrucao(String nome, byte opcode, String formato, int length) {
        this.nome = nome;
        this.opcode = opcode;
        this.formato = formato;
        this.length = length;
    }

    public abstract void executar(Memoria memoria, Registradores registradores);

    public String getNome() {
        return nome;
    }

    public byte getOpcode() {
        return opcode;
    }

    public String getFormato() {
        return formato;
    }

    public int getLength() {
        return length;
    }

    public Map<String,Boolean> getFlags(byte[] bytes) { // retorna um map com os flags em booleano, deve ser usado com formato 3 ou 4
        Map<String, Boolean> flags = new HashMap<>();

        flags.put("n", (bytes[0] & 0b00000010) != 0); 
        flags.put("i", (bytes[0] & 0b00000001) != 0);

        flags.put("x", (bytes[1] & 0b10000000) != 0);
        flags.put("b", (bytes[1] & 0b01000000) != 0);
        flags.put("p", (bytes[1] & 0b00100000) != 0);
        flags.put("e", (bytes[1] & 0b00010000) != 0);

        return flags; 
    }

    public int getFormato(byte[] bytes) {
        Map<String, Boolean> flags = getFlags(bytes);

        if(formato != "1" || formato != "2") {
            if(!(flags.get("i") || flags.get("n"))) { // se ambos forem 0, deve-se considerar as flags b,p,e como parte do deslocamento (somente aconteçe com n e i sendo iguais a 0)
                return 3; // tipo de instrução 3
            } else if (flags.get("e")) {
                return 4; // tipo de instrução 4
            } else { 
                return 3; // tipo de instrução 3
            }
        }

        return Integer.parseInt(formato); // tipo de instrução 1 ou 2

    }

    public int[] getRegistradores(byte[] bytes) { // retorna o número dos registradores para o formato de instrução 2
        int[] registradores = new int[2];

        registradores[0] = (int)(bytes[1] & 0b11110000) >>> 4; // primeiros 4 bits do segundo byte
        registradores[1] = (int)(bytes[1] & 0b00001111);// ultimos 4 bits do segundo byte

        return registradores;
    }

    public int getDisp(byte[] bytes) { // retorna os 12 ultimos bits dentre 3 bytes, usado para o formato de instrução 3
        int byte1 = bytes[2];
        int byte2 = (bytes[1] & 0b00001111)<<8;

        return byte1+byte2;
    }

    public int getDispbpe(byte[] bytes) { // retorna os 15 ultimos bits entre 3 bytes, usado para o formato de instrução 3 onde os flags i e n são iguais a 0, e os flags b,p,e são parte do disp
        int byte1 = bytes[2];
        int byte2 = (bytes[1] & 0b01111111)<<8;

        return byte1+byte2;
    }

    public int getAddr(byte[] bytes) { // retorna os 20 ultimos bits dentre 4 bytes, usado para o formato de instrução 4
        int byte1 = bytes[3];
        int byte2 = bytes[2]<<8;
        int byte3 = (bytes[1] & 0b00001111)<<16;

        return byte1+byte2+byte3;
    }

    public int calcularTA(Registradores registradores, Memoria memoria) { // usado para buscar o TA para instruções do tipo 3 ou 4
        int base = 0; // base para ser somada para o enderecamento relativo a base
        int x = 0; // endereçamento indexado que é somado com o TA caso o flag x = 1
        int m = 0; // onde o operando vai ser armazenado para ser retornado
        int tamanhom = 0;
        int pc = registradores.getValorPC();
        
        Map<String, Boolean> flags = getFlags(memoria.getBytes(pc, 2));

        if(!(flags.get("i") || flags.get("n"))) { // se ambos forem 0, deve-se considerar as flags b,p,e como parte do deslocamento (somente aconteçe com n e i sendo iguais a 0)
            m = getDispbpe(memoria.getBytes(pc, 3)); // tipo de instrução 3, com disp sendo os ultimos 15 bits da instrução
            tamanhom = 15;
        } else if (flags.get("e")) {
            m = getAddr(memoria.getBytes(pc, 4)); // tipo de instrução 4, com addr sendo os ultimos 20 bits da instrução
            tamanhom = 20;
        } else { 
            m = getDisp(memoria.getBytes(pc, 3)); // tipo de instrução 3, com disp sendo os ultimos 12 bits da instrução
            tamanhom = 12;
        }
        // b e p são flags para modo de endereçamento relativo a base TA = ((B) ou (PC)) + disp

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2)));
        
        if(flags.get("b")) { // b = 1 caso a soma seja com o registrador base
            base += registradores.getRegistradorPorNome("B").getValorIntSigned();
        } else if (flags.get("p")) { // p = 1 caso a soma seja com o program counter
            base += registradores.getValorPC();
            m = (int) (m << (32 - tamanhom)) >> (32 - tamanhom); // extende o sinal para ter interpretado como um inteiro com sinal
        }

        // ambos são 0 caso não seja modo de endereçamento relativo a base (endereçamento direto, normalmente sendo formato de instrução 4)
    
        if(flags.get("x")) { // flag indicando se o valor do registrador X deve ser somado com o TA
            x = registradores.getRegistradorPorNome("X").getValorIntSigned();
        }

        if(flags.get("i") && !flags.get("n")) { // endereçamento imediato, o valor calculado é o operando
            return m + base; // endereçamento indexado não pode ser usado com endereçamento imediato, então não soma o valor do registrador X
        } else if (flags.get("n") && !flags.get("i")) { // endereçamento indireto, o valor calculado é onde o endereço do operando está armazenado na memória
            int endereco = memoria.getWord(m+base);// endereçamento indexado não pode ser usado com endereçamento indireto, então não soma o valor do registrador X
            return memoria.getWord(endereco); 
        }

        return memoria.getWord(m+base+x);// se i e n são 0, endereçamento simples: o valor calculado é o endereço do operando
    }
}
