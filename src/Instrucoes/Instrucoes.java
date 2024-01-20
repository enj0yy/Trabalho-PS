package Instrucoes;

import java.util.HashMap;
import java.util.Map;


public class Instrucoes {
    private final Map<String, Instrucao> instrucoes;

    public Instrucoes() {
        instrucoes = new HashMap<>();

        instrucoes.put("18", new ADD());
        instrucoes.put("90", new ADDR());
        instrucoes.put("40", new AND());
        instrucoes.put("4", new CLEAR());
        instrucoes.put("28", new COMP());
        instrucoes.put("A0", new COMPR());
        instrucoes.put("24", new DIV());
        instrucoes.put("9C", new DIVR());
        
        instrucoes.put("3C", new J());
        instrucoes.put("30", new JEQ());
        instrucoes.put("34", new JGT());
        instrucoes.put("38", new JLT());
        instrucoes.put("48", new JSUB());
        instrucoes.put("0", new LDA());
        instrucoes.put("68", new LDB());
        instrucoes.put("50", new LDCH());
        
        instrucoes.put("8", new LDL());
        instrucoes.put("6C", new LDS());
        instrucoes.put("74", new LDT());
        instrucoes.put("04", new LDX());
        instrucoes.put("20", new MUL());
        instrucoes.put("98", new MULR());
        instrucoes.put("44", new OR());
        instrucoes.put("AC", new RMO());
        
        instrucoes.put("4C", new RSUB());
        instrucoes.put("A4", new SHIFTL());
        instrucoes.put("A8", new SHIFTR());
        instrucoes.put("0C", new STA());
        instrucoes.put("78", new STB());
        instrucoes.put("54", new STCH());
        instrucoes.put("14", new STL());
        
        instrucoes.put("7C", new STS());
        instrucoes.put("84", new STT());
        instrucoes.put("10", new STX());
        instrucoes.put("1C", new SUB());
        instrucoes.put("94", new SUBR());
        instrucoes.put("2C", new TIX());
        instrucoes.put("B8", new TIXR());
    }

    public Instrucao getInstrucao(String opcode) {
        return instrucoes.get(opcode);
    }

    public Instrucao getInstrucaoPorNome(String nome)
    {
        for (Instrucao instrucao : instrucoes.values()) {
            if (instrucao.getNome().equals(nome))
                return instrucao;
        }
        return null;
    }
}
