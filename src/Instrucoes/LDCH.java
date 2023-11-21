package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDCH extends Instrucao {

    public LDCH() {
        super("LDCH", "50");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Primeira parte: quebrar o registrador A em 3 bytes (8 bits)
        int registradorA = registradores.getRegistradorPorNome("A").getValor();
        byte [] dataA = new byte [3];  
        dataA[0] = (byte) ((registradorA >> 16) & 0xFF);
        dataA[1] = (byte) ((registradorA >> 8) & 0xFF);
        dataA[2] = (byte) ( registradorA & 0xFF);       // byte menos significativo
        
        // Segunda parte: quebrar o valor de memória em 3 bytes (8 bits)
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        byte [] dataMem = new byte [3]; 
        dataMem[0] = (byte) ((valorMem >> 16) & 0xFF); // byte mais significativo
        dataMem[1] = (byte) ((valorMem >> 8) & 0xFF);
        dataMem[2] = (byte) ( valorMem & 0xFF);   
        
        // Terceira parte: A[byte mais à direita] ← (m) 
        // (como o restante das instruções possuem m...m+2, assume-se que m é o byte mais a esquerda/byte mais significativo)
        dataA[2] = dataMem[0];
        
        // Quarta parte: montar valor do registrador A (bytes para inteiro/decimal)
        registradorA = 0;
        for (byte b : dataA) {
            registradorA = (registradorA << 8) + (b & 0xFF);
        }
        
        registradores.getRegistradorPorNome("A").setValor(registradorA);

        registradores.incrementarPC();
    }
    
}
