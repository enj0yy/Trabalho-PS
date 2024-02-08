package Executor;

import java.util.Arrays;
public class Memoria {
    
    private byte[] memoria;
    private int capacidade;

    public Memoria(int capacidade){ // capacidade = 1024 -> 1KB de memória
        this.memoria = new byte[capacidade];
        this.capacidade = capacidade;
    }
    
    public void limparMemoria(){ // Zera a memória
        Arrays.fill(memoria,(byte)0);
    }
    
    public byte[] getMemoria() {
        return memoria;
    }

    public byte getByte(int posicao){ // Retorna o byte no endereço posicao
        return (byte)((memoria[posicao]) & 0xFF);
    }
    public byte getOpcode(int posicao) {
        byte primeiroByte = getByte(posicao);
        return (byte)((primeiroByte & 0b11111100));
    }
    public byte[] getBytes(int posicao, int numero) {
        byte[] bytes = new byte[numero];
        for(int i = 0;i<numero && posicao+i <= capacidade;i++) {
            bytes[i] = getByte(posicao+i);
        }
        return bytes;
    }

    public void setByteInt(int posicao, int value){ // Seta o byte no endereço para o valor inteiro informado
        memoria[posicao] = (byte)(value & 0xFF);
    }
    
    public void setByte(int posicao,byte b){ // Seta o byte no endereço para o valor byte informado
        memoria[posicao] = b;
    }
    public void setWord(int posicao, int value){ // Preenche uma word de 3 bytes com um valor inteiro informado
        setByteInt(posicao, value >>> 16);
        setByteInt(posicao + 1, value >>> 8);
        setByteInt(posicao + 2, value);
    }
    public int getWord(int posicao){
        int MSB = getByte(posicao) << 16; // shift para esquerda de 16 bits
        int MID = getByte(posicao + 1) << 8;  // shift para esquerda de 8 bits
        int LSB = getByte(posicao + 2);
        // Ex: 0xE10000 + 0xE100 + 0xE1 = 0xE1E1E1 (efetivamente, concatena os 3 bytes da word)
        return MSB + MID + LSB;
    }

}
