package ProcessadorDeMacros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Montador.Line;

public class ProcessadorDeMacros {
    
    private Map<String, Tupla> NAMTAB;          // Tabela com os nomes dos macros e seus ponteiros
    private Map<String, String> DEFTAB;         // Tabela com os nomes dos macros e seus códigos
    private Map<String, List<String>> ARGTAB;   // Tabela com os nomes dos macros e seus argumentos

    private ArrayList<String> input = new ArrayList<String>(); 


    public ProcessadorDeMacros() {
        NAMTAB = new HashMap<String, Tupla>();
        DEFTAB = new HashMap<String, String>();
        ARGTAB = new HashMap<String, List<String>>();
    }

    public void macroProcessor(){
        
        int lineCounter = 0;
        boolean expanding = false;
        
        Line line = new Line();
        line.parser(input.get(lineCounter));
        
        while (!line.opcode.equals("END")){
            if (line.opcode.equals("MACRO")){
                StringBuffer macroCode = new StringBuffer();
                String macroName = line.label;
                Tupla tupla = new Tupla(lineCounter, 0);
                NAMTAB.put(macroName, tupla);
                ARGTAB.put(macroName, new ArrayList<String>());
                lineCounter++;
                line.parser(input.get(lineCounter));
                while (!line.label.equals("MEND")){
                    macroCode.append(line.line + "\n");
                    lineCounter++;
                    line.parser(input.get(lineCounter));
                }
                NAMTAB.get(macroName).setEndPointer(lineCounter);
                DEFTAB.put(macroName, macroCode.toString());
                ARGTAB.put(macroName, line.macroArguments);
                        
            }
        //     else if (NAMTAB.containsKey(line.operator)){
        //         String macroName = line.operator;
        //         List<String> arguments = ARGTAB.get(macroName);
        //         String[] macroArguments = line.operand.split(",");
        //         for (String argument : macroArguments){
        //             arguments.add(argument);
        //         }
        //         ARGTAB.put(macroName, arguments);
        //         lineCounter++;
        //         line.parser(input.get(lineCounter));
        //         while (!line.operator.equals("MEND")){
        //             String macroLine = DEFTAB.get(macroName);
        //             for (int i = 0; i < arguments.size(); i++){
        //                 macroLine = macroLine.replace("&" + arguments.get(i), line.operand.split(",")[i]);
        //             }
        //             input.add(lineCounter, macroLine);
        //             lineCounter++;
        //             line.parser(input.get(lineCounter));
        //         }
        //     }
        
        //     lineCounter++;
        // }
        }
    }

    public void setPrograma(String codigoAssembly) 
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        for (String linha : linhas){
            input.add(linha);
        }
    }

}
