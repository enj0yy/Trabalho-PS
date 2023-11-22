package Executor;

import java.util.ArrayList;
import java.util.Collections;

public class Memoria {
    private ArrayList<String> memoria;

    Memoria() {
        memoria = new ArrayList<String>(Collections.nCopies(350, "00")); // faz 350 celulas de memória
    }

    public String getPosicaoMemoria(int posicao) {
        return memoria.get(posicao);
    }
    
    public void setPosicaoMemoria(int posicao, String valor){
        memoria.set(posicao, valor);
    }

    public void limparMemoria()
    {
        for (int i = 0; i < memoria.size(); i++)
            this.memoria.set(i, "00");
    }

    public ArrayList<String> getMemoria() {
        return memoria;
    }
    
}
