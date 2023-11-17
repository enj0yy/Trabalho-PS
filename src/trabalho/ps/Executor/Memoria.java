package trabalho.ps.Executor;

import java.util.ArrayList;
import java.util.Collections;

public class Memoria {
    private ArrayList<String> memoria;

    Memoria() {
        memoria = new ArrayList<String>(Collections.nCopies(100, "000")); // faz 100 celulas de memória com o valor "000", representando um hexadecimal de 3 digitos, ou seja 24 bits
    }
}
