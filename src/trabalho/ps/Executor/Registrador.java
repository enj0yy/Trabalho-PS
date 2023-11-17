package trabalho.ps.Executor;

public class Registrador {
    private String nome;
    private int valor; // int pode armazenar hexadecimal, por exemplo int i = 0x10

    Registrador(String nome, int valor) {
        this.nome = nome;
        this.valor = valor;
    }

    Registrador(String nome) { // se não é fornecido valor, default é 0
        this.nome = nome;
        this.valor = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void incrementarValor(int valor) { // para Program Counter
        this.valor += valor;
    }

}
