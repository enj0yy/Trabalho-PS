package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUB extends Instrucao {

    public SUB() {
        super("SUB", "1C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        
        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor();  
        valorAcumulator -= valorMem;
        
        registradores.getRegistradorPorNome("A").setValor(valorAcumulator);
        
        registradores.incrementarPC();
    }
}
