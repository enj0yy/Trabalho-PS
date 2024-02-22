package ProcessadorDeMacros;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Montador.Line;


// TODO: PERMITIR MACROS DENTRO DE MACROS NA DEFINIÇÃO

public class ProcessadorDeMacros {
    
    public Map<String, Tupla> NAMTAB;          // CHANGE PRIVATE // Tabela com os nomes dos macros e seus ponteiros
    public Map<String, String> DEFTAB;         // CHANGE PRIVATE // Tabela com os nomes dos macros e seus códigos
    public Map<String, List<String>> ARGTAB;   // CHANGE PRIVATE // Tabela com os nomes dos macros e seus argumentos

    private ArrayList<String> input = new ArrayList<String>();
    private ArrayList<String> output = new ArrayList<String>();
    private String errorMessage = "";


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
                if (!expanding){
                    StringBuffer macroCode = new StringBuffer();
                    ArrayList<String> macroArgs = new ArrayList<String>();
                    String macroName = line.label;
                    Tupla tupla = new Tupla(lineCounter, 0);
                    NAMTAB.put(macroName, tupla);
                    lineCounter++;
                    line.parser(input.get(lineCounter));

                    while (!line.opcode.equals("MEND")){
                        macroCode.append(line.line + "\n");
                        lineCounter++;
                        line.parser(input.get(lineCounter));
                    }

                    NAMTAB.get(macroName).setEndPointer(lineCounter);
                    DEFTAB.put(macroName, macroCode.toString());
                    macroArgs.addAll(line.macroArguments);
                    ARGTAB.put(macroName, macroArgs);
                    lineCounter++;
                    line.parser(input.get(lineCounter));  
                }
            }
            else if (DEFTAB.containsKey(line.label)){
                String macroBody = DEFTAB.get(line.label);
                List<String> macroArgs = ARGTAB.get(line.label);
                List<String> macroParams = line.macroArguments;
                for (int i = 0; i < macroArgs.size(); i++){
                    macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i));
                }
                String[] linhas = macroBody.split("\\r?\\n");
                for (String linha : linhas){
                    output.add(linha);
                }
                lineCounter++;
                line.parser(input.get(lineCounter));
            }     
            else if (line.opcode.equals("START")){
                output.add(line.line);
                lineCounter++;
                line.parser(input.get(lineCounter));
                expanding = true;
            }
            else{
                output.add(line.line);
                lineCounter++;
                line.parser(input.get(lineCounter));
            }
        }

        output.add(line.line);
        gerarTXTOutput();
        return;
    }


    public void setPrograma(String codigoAssembly) 
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        for (String linha : linhas){
            input.add(linha);
        }
    }

    private void gerarTXTOutput() {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMacro.txt")) 
            {
                fileWriter.write(String.join("\n", output));
                fileWriter.close();
            } catch (IOException e) {
                errorMessage = errorMessage + "\nERRO - Erro ao gerar arquivo de saida.";
            }
    }

}
