package Executor;


import Instrucoes.Instrucoes;

public class Executor {
    private Memoria memoria;
    private Registradores registradores;
    private Instrucoes instrucoes;
    private int output;
    private boolean stop;

    public Executor() {
        this.memoria = new Memoria(1024); // 1KB de memória
        this.registradores = new Registradores();
        this.instrucoes = new Instrucoes();
        this.output = -1;
    }
    
    public void limpar() {
        memoria.limparMemoria();
        registradores.limparRegistradores();
        output = -1;
    }

    public void setPrograma(String programaObjeto) {
        memoria.limparMemoria();
        registradores.limparRegistradores();

        int posMem = 0;

        StringBuilder binaryString = new StringBuilder();

        String[] lines = programaObjeto.split("\\r?\\n");

        for (String l : lines) {
            binaryString.append(l.trim());
        }

        // Lê de 8 em 8 caracteres
        for (int i = 0; i < binaryString.length(); i += 8) {
            String pedaco = binaryString.substring(i, Math.min(i + 8, binaryString.length()));

            byte pedacoByte = (byte) Integer.parseInt(pedaco, 2);

            memoria.setByte(posMem++, pedacoByte);
        }
    }

    
    public void executarPrograma()
    {
        int pc = registradores.getValorPC();
        stop = false;

        while (memoria.getWord(pc) != 0) // para de executar se a proxima palavra for vazia
        {

            byte opcode = memoria.getOpcode(pc);
            if (opcode == (byte)0xD8){ // Read
                stop = true;
                registradores.incrementarPC(1);
                return;
            }
            
            if (opcode == (byte)0xDC) { // Write
                setOutput(registradores.getRegistradorPorNome("A").getValorIntSigned());
                registradores.incrementarPC(1);
            } else {
                instrucoes.getInstrucao(opcode).executar(memoria, registradores);
            }
            
            pc = this.registradores.getRegistradorPorNome("PC").getValorIntSigned();
        }   
    }

    public boolean executarPasso()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValorIntSigned();

        if (memoria.getWord(pc) == 0) // para de executar se a proxima palavra for vazia
        {
            return false;
        }
        
        byte opcode = memoria.getOpcode(pc);
        stop = false;
        
        if (opcode == (byte)0xD8)
        {
            stop = true;
            registradores.incrementarPC(1);
            return true;
        }

        if (opcode == (byte)0xDC) {
            setOutput(registradores.getRegistradorPorNome("A").getValorIntSigned());
            registradores.incrementarPC(1);
        } else {
            instrucoes.getInstrucao(opcode).executar(memoria, registradores);
        }

        pc = this.registradores.getValorPC();

        return true;
    }
    
    public Memoria getMemoria() {
        return memoria;
    }
    
    public Registradores getRegistradores() {
        return registradores;
    }
    
    public Instrucoes getInstrucoes() {
        return instrucoes;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int getOutput() {
        return output;
    }
    
    public boolean getStop(){
        return stop;
    }
}
