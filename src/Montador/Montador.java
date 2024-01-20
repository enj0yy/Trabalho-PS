package Montador;

import Instrucoes.Instrucoes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Montador {
    private String errorMessage = "";
    private Instrucoes instrucoes = new Instrucoes();
    private Map<String, Integer> SYMTAB = new HashMap<>();

    List<String> input = new ArrayList<String>();
    List<String> output = new ArrayList<String>();

    public void montarPrograma(String caminho)
    {
        setPrograma(caminho);
        passoUm();
        passoDois();
        gerarTXTOutput();
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Arquivo de entrada: " + System.getProperty("user.dir")+ "\\txtFiles\\inputMontador.txt").append("\n");
        mensagem.append("Arquivo de saida: " + System.getProperty("user.dir")+ "\\txtFiles\\outputMontador.txt").append("\n\n");
        if (errorMessage.isEmpty())
            mensagem.append("Programa montado com sucesso.");
        else
            mensagem.append("Programa montado com erros. Erro(s): \n" + errorMessage);
            
        JOptionPane.showMessageDialog(null, mensagem, "Montador", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setPrograma(String caminho)
    {
        File file = new File(caminho);  
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = br.readLine()) != null){
                input.add(str);
            }
        } catch(Exception e)
        {
            errorMessage = errorMessage + "\nErro ao ler o arquivo de entrada.";
        }
    }

    private void passoUm()
    {
        int LocationCounter = 0;         
        
        for(String linha : input)
        {
            if (linha.isEmpty())
                continue;

            String label = getLabel(linha);
            String opcode = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if(label != null)
                SYMTAB.put(label, LocationCounter);    

            if (instrucoes.getInstrucaoPorNome(opcode) != null) // Instrucao
            {
                LocationCounter++;

                for (String operand : operands)
                {
                    if (!isNumeric(operand))
                        if (SYMTAB.get(operand) == null)
                            SYMTAB.put(operand, null);

                    LocationCounter++;
                }
            }
            else // Pseudo-instrucao
            {
                switch (opcode) 
                {
                    case "RD":
                    case "WD":
                    case "WORD": 
                    case "BYTE":
                        LocationCounter++;
                        break;

                    case "RESW":
                    case "RESB":
                        for (String operand : operands)
                            LocationCounter += Integer.parseInt(operand);
                        break;

                    default:
                        errorMessage = errorMessage + "\nERRO - Instrucao invalida: " + linha;
                        break;
                }
            }
        }  
    }

    private void passoDois()
    {       
        for(String linha : input)
        {
            if (linha.isEmpty())
                continue;

            String opcode = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if (instrucoes.getInstrucaoPorNome(opcode) != null) // Instrucao
            {
                output.add(instrucoes.getInstrucaoPorNome(opcode).getOpcode());

                for (String operand : operands)
                {
                    if (isNumeric(operand))
                        output.add(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                    else
                        if (SYMTAB.get(operand) == null)
                        {
                            output.add(Integer.toHexString(0).toUpperCase());
                            errorMessage = errorMessage + "\nERRO - Label nao definida: " + linha;
                        }
                        else
                            output.add(Integer.toHexString(SYMTAB.get(operand)).toUpperCase());           
                }
            }
            else // Pseudo-instrucao
            {
                switch (opcode) 
                {
                    case "RD":
                        output.add("D8");
                        break;

                    case "WD":
                        output.add("DC");
                        break;
                        
                    case "WORD":
                    case "BYTE":
                        for (String operand : operands)
                            output.add(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                        break;

                    case "RESW":
                    case "RESB":
                        for (String operand : operands)
                            for (Integer i = 0; i < Integer.parseInt(operand); i++)
                                output.add("XX");   
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private void gerarTXTOutput()
    {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "\\txtFiles\\outputMontador.txt")) 
        {
            for (String str : output) {
                fileWriter.write(str + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLabel(String linha)
    {
        String[] splited = linha.split("\\s+");
        try 
        {
            if (instrucoes.getInstrucaoPorNome(splited[0]) == null) // Possui Label
                return splited[0];
            else // Nao possui Label
                return null;
        } catch ( Exception e) {
            return null;
        }
    }
    
    private String getOpcode(String linha)
    {
        String[] splited = linha.split("\\s+");
        try
        {
            if (instrucoes.getInstrucaoPorNome(splited[0]) == null) // Possui Label
                return splited[1];
            else // Nao possui Label
                return splited[0];
        } catch ( Exception e ) {
            return null;
        }
        
    }

    private List<String> getOperands(String linha)
    {
        String[] splited = linha.split("\\s+");
        List<String> operands = new ArrayList<String>();

        try
        {
            if (instrucoes.getInstrucaoPorNome(splited[0]) == null) // Possui Label
            {
                for (int i = 2; i < splited.length; i++)
                    operands.add(splited[i]);
            }
            else // Nao possui Label
            {
                for (int i = 1; i < splited.length; i++)
                    operands.add(splited[i]);
            }
            return operands;
        } catch ( Exception e ) {
            return null;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
