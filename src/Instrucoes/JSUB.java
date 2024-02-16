package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JSUB extends Instrucao {
    public JSUB() {
        super("JSUB", (byte)0x48, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução

        int enderecoRetorno = registradores.getValorPC();
        registradores.getRegistradorPorNome("L").setValorInt(enderecoRetorno); // seta L para o endereço de retorno

        registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
    }
}
