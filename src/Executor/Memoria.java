package Executor;

import java.util.ArrayList;
import java.util.Collections;

public class Memoria {
    private ArrayList<String> memoria;

    Memoria() {
        memoria = new ArrayList<String>(Collections.nCopies(350, "000")); // faz 350 celulas de memória com o valor "000", representando um hexadecimal de 3 digitos, ou seja 24 bits
    }

    public String getPosicaoMemoria(int posicao) {
        return memoria.get(posicao);
    }
    
    public void setPosicaoMemoria(int posicao, String valor){
        memoria.set(posicao, valor);
    }

}
