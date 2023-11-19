package Executor;

public class Registrador {
    private String nome;
    private int id;
    private int valor;

    Registrador(String nome, int id, int valor) {
        this.nome = nome;
        this.id = id;
        this.valor = valor;
    }

    Registrador(String nome, int id) { // se não é fornecido valor, default é 0
        this.nome = nome;
        this.id = id;
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
