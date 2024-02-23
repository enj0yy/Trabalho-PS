package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ProcessadorDeMacros.ProcessadorDeMacros;

public class test {
    
    static ProcessadorDeMacros processadorDeMacros;
    
    
    public static void main(String[] args) throws IOException {
        
        String filePath = System.getProperty("user.dir") + "/txtFiles/inputMacroNested.txt/";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        StringBuilder contentBuilder = new StringBuilder();
        for (String line : lines) {
            contentBuilder.append(line).append("\n");
        }
        String input = contentBuilder.toString();
        processadorDeMacros = new ProcessadorDeMacros();

        processadorDeMacros.setPrograma(input);
        processadorDeMacros.macroProcessor();
    }
}
