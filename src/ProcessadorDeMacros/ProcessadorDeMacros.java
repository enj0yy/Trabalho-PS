package ProcessadorDeMacros;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Montador.Line;


/* MACRO nomeMacro parametro1,parametro2, ... */   // Macro com parâmetros
/* MACRO nomeMacro                            */   // Macro sem parâmetros
/* MEND                                       */   // Fim da definição do macro
/* nomeMacro parametro1,parametro2, ...       */   // Chamada de macro
/* nomeMacro                                  */   // Chamada de macro sem parâmetros

// Chamadas de macro dentro de um macro devem ter como prefixo &



public class ProcessadorDeMacros {
    
    private Map<String, Tupla> NAMTAB;          // Tabela com os nomes dos macros e seus ponteiros
    private Map<String, String> DEFTAB;         // Tabela com os nomes dos macros e seus códigos
    private Map<String, List<String>> ARGTAB;   // Tabela com os nomes dos macros e seus argumentos
    public Line line;

    private ArrayList<String> input = new ArrayList<String>();
    private ArrayList<String> output = new ArrayList<String>();
    private String errorMessage = "";


    public ProcessadorDeMacros() {
        NAMTAB = new HashMap<String, Tupla>();
        DEFTAB = new HashMap<String, String>();
        ARGTAB = new HashMap<String, List<String>>();
        line = new Line();
    }

    public void macroProcessor(String moduloIndex){           // Função principal e única do processador de macros
        
        int lineCounter = 0;
        boolean expanding = false;
        
        line.parser(input.get(lineCounter));
        
        while (!line.opcode.equals("END")){
            if (line.opcode.equals("MACRO")){           // Se a linha for um macro, define
                if (!expanding){
                    StringBuffer macroCode = new StringBuffer();
                    ArrayList<String> macroArgs = new ArrayList<String>();
                    String macroName = line.label;
                    macroArgs.addAll(line.macroArguments);          
                    ARGTAB.put(macroName, macroArgs);               
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
                    lineCounter++;
                    line.parser(input.get(lineCounter));  
                }
            }
            else if (DEFTAB.containsKey(line.label)){       // Se a linha for uma chamada de macro, expande
                String macroBody = DEFTAB.get(line.label);
                List<String> macroArgs = ARGTAB.get(line.label);
                List<String> macroParams = line.macroArguments;

                for (int i = 0; i < macroArgs.size(); i++){
                    macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i));
                }

                String[] linhas = macroBody.split("\\r?\\n");
                for (String linha : linhas){
                    Line tmpLine = new Line();
                    tmpLine = line;
                    tmpLine.parser(linha);
                    if (DEFTAB.containsKey(tmpLine.label)){
                        expandNestedMacros(linha);          // Se a linha expandida for um macro, expande o macro interno
                    }
                    else{
                        output.add(linha);
                    }
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
            else{                                         // Se a linha não for um macro, apenas copia
                output.add(line.line);
                lineCounter++;
                line.parser(input.get(lineCounter));
            }
        }

        output.add(line.line);
        gerarASMOutput(moduloIndex);
        return;
    }

    public void expandNestedMacros(String linha) {
        line.parser(linha);

        String macroBody = DEFTAB.get(line.label);
        List<String> macroArgs = ARGTAB.get(line.label);
        List<String> macroParams = line.macroArguments;
        for (int i = 0; i < macroArgs.size(); i++) {
            macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i));
        }
        String[] lines = macroBody.split("\\r?\\n");
        for (String macroLine : lines) {
            if (DEFTAB.containsKey(macroLine)) {
                expandNestedMacros(macroLine);              // se a linha expandida for um macro, expande o macro interno
            } else {
                output.add(macroLine);
            }
        }
        return;
    }


    public void setPrograma(String codigoAssembly) 
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        for (String linha : linhas){
            input.add(linha);
        }
    }

    private void gerarASMOutput(String moduloIndex) {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMacroModulo" + moduloIndex + ".asm"))
            {
                fileWriter.write(String.join("\n", output));
                fileWriter.close();
            } catch (IOException e) {
                errorMessage = errorMessage + "\nERRO - Erro ao gerar arquivo de saida.";
            }
    }

    public void limpar() {
        ARGTAB.clear();
        DEFTAB.clear();
        NAMTAB.clear();
        output.clear();
        input.clear();
    }

    public String getOutput() {
        return String.join("\n", output);
    }
}
