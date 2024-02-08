package Instrucoes;

import java.util.HashMap;
import java.util.Map;


public class Instrucoes {
    private final Map<Byte, Instrucao> instrucoes;

    public Instrucoes() {
        instrucoes = new HashMap<>();

        instrucoes.put((byte)0x18, new ADD());
        instrucoes.put((byte)0x90, new ADDR());
        instrucoes.put((byte)0x40, new AND());
        instrucoes.put((byte)0x4, new CLEAR());
        instrucoes.put((byte)0x28, new COMP());
        instrucoes.put((byte)0xA0, new COMPR());
        instrucoes.put((byte)0x24, new DIV());
        instrucoes.put((byte)0x9C, new DIVR());
        
        instrucoes.put((byte)0x3C, new J());
        instrucoes.put((byte)0x30, new JEQ());
        instrucoes.put((byte)0x34, new JGT());
        instrucoes.put((byte)0x38, new JLT());
        instrucoes.put((byte)0x48, new JSUB());
        instrucoes.put((byte)0x0, new LDA());
        instrucoes.put((byte)0x68, new LDB());
        instrucoes.put((byte)0x50, new LDCH());
        
        instrucoes.put((byte)0x8, new LDL());
        instrucoes.put((byte)0x6C, new LDS());
        instrucoes.put((byte)0x74, new LDT());
        instrucoes.put((byte)0x04, new LDX());
        instrucoes.put((byte)0x20, new MUL());
        instrucoes.put((byte)0x98, new MULR());
        instrucoes.put((byte)0x44, new OR());
        instrucoes.put((byte)0xAC, new RMO());
        
        instrucoes.put((byte)0x4C, new RSUB());
        instrucoes.put((byte)0xA4, new SHIFTL());
        instrucoes.put((byte)0xA8, new SHIFTR());
        instrucoes.put((byte)0x0C, new STA());
        instrucoes.put((byte)0x78, new STB());
        instrucoes.put((byte)0x54, new STCH());
        instrucoes.put((byte)0x14, new STL());
        
        instrucoes.put((byte)0x7C, new STS());
        instrucoes.put((byte)0x84, new STT());
        instrucoes.put((byte)0x10, new STX());
        instrucoes.put((byte)0x1C, new SUB());
        instrucoes.put((byte)0x94, new SUBR());
        instrucoes.put((byte)0x2C, new TIX());
        instrucoes.put((byte)0xB8, new TIXR());
    }

    public Instrucao getInstrucao(byte opcode) { // 8 primeiros bits da instrução, 6 bits de opcode e 2 de flag
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
