package trabalho.ps.Instrucoes;

public abstract class Instrucao {
    private final String nome;
    private final byte opcode;
    //private final int tamanho;

    Instrucao(String nome, byte opcode) {
        this.nome = nome;
        this.opcode = opcode;
    }

    public abstract void executar(); //TODO: definir quais parametros são necessarios para execução

    public String getNome() {
        return nome;
    }

    public byte getOpcode() {
        return opcode;
    }
}
