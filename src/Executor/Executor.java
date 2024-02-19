package Executor;


import Instrucoes.Instrucoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
    
    
    public void setProgramaold(String caminho) 
    {
        memoria.limparMemoria();
        registradores.limparRegistradores();

        File file = new File(caminho);

        int posMem = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String binaryString = br.readLine();

            int tamanhoByte = 8;
            int tamanho = binaryString.length();
    
            for (int i = 0; i < tamanho; i += tamanhoByte) {
                int endIndex = Math.min(i + tamanhoByte, tamanho);
                String pedaco = binaryString.substring(i, endIndex);

                byte pedacoByte = (byte) Integer.parseInt(pedaco, 2);

                memoria.setByte(posMem++, pedacoByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao ler o arquivo.");
        }
    }


    public void setPrograma(String caminho) {
        memoria.limparMemoria();
        registradores.limparRegistradores();

        int posMem = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(caminho));
            String line;
            StringBuilder binaryString = new StringBuilder();

            while ((line = br.readLine()) != null) {
                binaryString.append(line.trim());
            }

            br.close();

            // Lê de 8 em 8 caracteres
            for (int i = 0; i < binaryString.length(); i += 8) {
                String pedaco = binaryString.substring(i, Math.min(i + 8, binaryString.length()));

                byte pedacoByte = (byte) Integer.parseInt(pedaco, 2);

                memoria.setByte(posMem++, pedacoByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
