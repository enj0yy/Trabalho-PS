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
    private Instrucoes intrucoes;

    public Executor() {
        this.memoria = new Memoria();
        this.registradores = new Registradores();
        this.intrucoes = new Instrucoes();
    }
    
    public void setPrograma(String caminho) throws FileNotFoundException, IOException
    {
        
        memoria.limparMemoria();
        limparRegistradores();
        
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
    
    public void limparRegistradores(){
        registradores.getRegistradorPorNome("A").setValor(0);
        registradores.getRegistradorPorNome("X").setValor(0);
        registradores.getRegistradorPorNome("L").setValor(0);
        registradores.getRegistradorPorNome("B").setValor(0);
        registradores.getRegistradorPorNome("S").setValor(0);
        registradores.getRegistradorPorNome("T").setValor(0);
        registradores.getRegistradorPorNome("PC").setValor(0);
    }
    
    public void executarPrograma()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValor();
        String opcode = memoria.getPosicaoMemoria(pc);
               
        while (!"00".equals(opcode))
        {
            registradores.incrementarPC();
            intrucoes.getInstrucao(opcode).executar(memoria, registradores);
            
            pc = this.registradores.getRegistradorPorNome("PC").getValor();
            opcode = memoria.getPosicaoMemoria(pc);  
        }   
    }

    public boolean executarPasso()
    {
        int pc = this.registradores.getRegistradorPorNome("PC").getValor();
        String opcode = memoria.getPosicaoMemoria(pc);
        
        if ("00".equals(opcode))
            return false;

        registradores.incrementarPC();
        intrucoes.getInstrucao(opcode).executar(memoria, registradores);
        return true;
    }
    
    public ArrayList<String> getMemoria() {
        return memoria.getMemoria();
    }
    
    public void setMemoria(int pos, String valor) {
        memoria.setPosicaoMemoria(pos, valor);
    }

    public Registrador getRegistrador(String nome) {
        return registradores.getRegistradorPorNome(nome);
    }
}
