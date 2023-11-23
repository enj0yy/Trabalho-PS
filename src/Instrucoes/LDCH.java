package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDCH extends Instrucao {

    public LDCH() {
        super("LDCH", "50");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {     
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        int byteMenosSigMemoria = valorMem & 0xFF;        
        
        // A[byte mais à direita] ← (m) 
        int registradorA = byteMenosSigMemoria;
        registradores.getRegistradorPorNome("A").setValor(registradorA);

        registradores.incrementarPC();
    }
    
}
