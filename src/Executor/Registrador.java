package Executor;

import java.util.Arrays;

public class Registrador {
    private String nome;
    private int id;
    private byte[] valor;

    Registrador(String nome, int id, byte[] valor) {
        this.nome = nome;
        this.id = id;
        this.valor = valor;
    }

    Registrador(String nome, int id) { // se não é fornecido valor, default é 0
        this.nome = nome;
        this.id = id;
        this.valor = new byte[3];
        Arrays.fill(this.valor, (byte) 0);
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
    public int getValorIntUnsigned() {
        int byte1 = valor[2];
        int byte2 = valor[1] << 8;
        int byte3 = valor[0] << 16;

        return byte1+byte2+byte3;
    }   
    public int getValorIntSigned() {
        int byte1 = valor[2];
        int byte2 = valor[1] << 8;
        int byte3 = valor[0] << 16;

        int n = byte1+byte2+byte3;
        n = (int) (n << (32 - 24)) >> (32 - 24);
        return n;
    }   
    public void setValorInt(int n) {
        valor[2] = (byte)((n) & 0xFF);
        valor[1] = (byte)((n >>> 8) & 0xFF);
        valor[0] = (byte)((n >>> 16) & 0xFF);
    }
    public byte[] getValor() {
        return valor;
    }

    public void setValor(byte[] valor) {
        this.valor = valor;
    }

    public void incrementarValor(int valor) { // para Program Counter
        int pc = getValorIntSigned();
        pc += valor;
        setValorInt(pc);
    }

}
