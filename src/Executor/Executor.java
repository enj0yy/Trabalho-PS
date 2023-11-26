package Executor;


import Instrucoes.Instrucoes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Executor {
    private Memoria memoria;
    private Registradores registradores;
    private Instrucoes instrucoes;
    private int output;
    private boolean stop;

    public Executor() {
        this.memoria = new Memoria();
        this.registradores = new Registradores();
        this.instrucoes = new Instrucoes();
        this.output = -1;
    }
    
    public void setPrograma(String caminho) throws FileNotFoundException, IOException
    {
        
        memoria.limparMemoria();
        registradores.limparRegistradores();
        
        File file = new File(caminho);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str;
            int pos = 0;
            while ((str = br.readLine()) != null){
                String[] splited = str.split("\\s+");
                for (String splited1 : splited) {
                    memoria.setPosicaoMemoria(pos, splited1);
                    pos++;
                }
            }
        } catch(Exception e)
        {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
    
    
    
    public void executarPrograma()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValor();
        String opcode = memoria.getPosicaoMemoria(pc);
        stop = false;
               
        while (!"00".equals(opcode))
        {
            if (opcode.equals("00")){
                return;
            }
            if (opcode.equals("D8")){ // Read
                registradores.incrementarPC();
                stop = true;
                return;
            }
            registradores.incrementarPC();
            if ("DC".equals(opcode)) { // Write
                setOutput(registradores.getRegistradorPorNome("A").getValor());
                registradores.incrementarPC();
            } else {
                instrucoes.getInstrucao(opcode).executar(memoria, registradores);
            }
            
            pc = this.registradores.getRegistradorPorNome("PC").getValor();
            opcode = memoria.getPosicaoMemoria(pc);  
        }   
    }

    public boolean executarPasso()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValor();
        String opcode = memoria.getPosicaoMemoria(pc);
        stop = false;
        
        if (opcode.equals("00")){
            return false;
        }
        
        if (opcode.equals("D8")){
            registradores.incrementarPC();
            stop = true;
            return true;
        }

        registradores.incrementarPC();
        if ("DC".equals(opcode)) {
            setOutput(registradores.getRegistradorPorNome("A").getValor());
            registradores.incrementarPC();
        } else {
            instrucoes.getInstrucao(opcode).executar(memoria, registradores);
        }

        pc = this.registradores.getRegistradorPorNome("PC").getValor();
        opcode = memoria.getPosicaoMemoria(pc);
        
        if ("00".equals(opcode))
            return false;

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
