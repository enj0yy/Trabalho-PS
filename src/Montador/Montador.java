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
import Executor.Registradores;

/* 
MONTADOR:
    Livro Página 44.

TO-DO: 
    Tratar armazenamento de byte 
        ex: BYTE C'EOF' vai de string(EOF) para HEX(454F46)
            BYTE X'10' vai de decimal(10) para HEX(A), esse já está funcionando
    Fazer interface gráfica
*/ 

public class Montador {
    private String errorMessage = "";

    private Instrucoes OPTAB;               // Tabela de instrucoes
    private Map<String, String> POPTAB;     // Tabela de pseudo-instrucoes
    private Map<String, Integer> SYMTAB;    // Tabela de simbolos

    private List<String> input = new ArrayList<String>();
    private List<String> output = new ArrayList<String>();

    public Montador()
    {
        OPTAB = new Instrucoes();

        POPTAB = new HashMap<String, String>();
        POPTAB.put("START", "0");
        POPTAB.put("END", "0");
        POPTAB.put("RD", "D8");
        POPTAB.put("WD", "DC");
        POPTAB.put("WORD", "0");
        POPTAB.put("BYTE", "0");
        POPTAB.put("RESW", "0");
        POPTAB.put("RESB",  "0");

        SYMTAB = new HashMap<String, Integer>();
    }

    public String montarPrograma(String codigoAssembly)
    {
        limpaListas();
        setPrograma(codigoAssembly);
        passoUm();    
        passoDois();
        String output = gerarTXTOutput();
        mostrarMensagem();
        return output;
    }

    public void limpaListas() {
        input.clear();
        output.clear();
        errorMessage = "";
        SYMTAB.clear();
    }

    private void setPrograma(String codigoAssembly)
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        for (String linha : linhas){
            input.add(linha);
        }
    }


    private void passoUm()
    {
        int LocationCounter = 0;

        String primeiraLinha = input.get(0);

        String label = getLabel(primeiraLinha);
        String opcode = getOpcode(primeiraLinha);
        List<String> operands = getOperands(primeiraLinha);

        if (opcode != null && opcode.equals("START")) 
            LocationCounter = Integer.parseInt(operands.get(0));
        else
            LocationCounter = 0;    
  
        
        for (int i = 1; i < input.size(); i++) 
        {
            String linha = input.get(i);

            if (linha.isEmpty() || Character.compare(linha.charAt(0), '.') == 0)
                continue;

            label = getLabel(linha);
            opcode = getOpcode(linha);
            operands = getOperands(linha);

            if (opcode != null && opcode.equals("END")) 
                break;
            
            if(label != null)
                SYMTAB.put(label, LocationCounter);    

            if (OPTAB.getInstrucaoPorNome(opcode) != null) // Instrucao
            {
                LocationCounter++;

                for (String operand : operands)
                {
                    if (isSymbol(operand))
                        if (SYMTAB.get(operand) == null)
                            SYMTAB.put(operand, null);

                    LocationCounter++;
                }
            }
            else if (POPTAB.get(opcode) != null) // Pseudo-instrucao
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
                        break;
                }
            }
            else 
                errorMessage = errorMessage + "\nERRO - Instrucao invalida: " + linha;
        }  
    }

    private void passoDois()
    {       
        for(String linha : input)
        {
            if (linha.isEmpty() || Character.compare(linha.charAt(0), '.') == 0) // Pula linhas que começam com . (comentários)
                continue;

            String opcode = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if (opcode != null && opcode.equals("START")) 
                continue;

            if (opcode != null && opcode.equals("END")) 
                break;

            if (OPTAB.getInstrucaoPorNome(opcode) != null) // Instrucao
            {
                output.add(OPTAB.getInstrucaoPorNome(opcode).getOpcode());

                for (String operand : operands)
                {
                    if (isSymbol(operand))
                        if (SYMTAB.get(operand) == null)
                        {
                            output.add(Integer.toHexString(0).toUpperCase());
                            errorMessage = errorMessage + "\nERRO - Simbolo nao definido: " + linha;
                        }
                        else
                            output.add(Integer.toHexString(SYMTAB.get(operand)).toUpperCase());     
                    else
                        output.add(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());      
                }   
            }
            else if (POPTAB.get(opcode) != null) // Pseudo-instrucao
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
                                output.add("0");   
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private String gerarTXTOutput() {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMontador.txt")) 
            {
                for (String str : output) {
                    fileWriter.write(str + System.lineSeparator());
                }
                fileWriter.close();
            } catch (IOException e) {
                errorMessage = errorMessage + "\nERRO - Erro ao gerar arquivo de saida.";
                return errorMessage;
            }
        StringBuilder outputString = new StringBuilder();
        outputString.setLength(0);
        for (String str : output) {
            outputString.append(str).append(System.lineSeparator());
        }
        return outputString.toString();
    }


    private void mostrarMensagem()
    {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Arquivo de entrada: ").append(System.getProperty("user.dir")).append("\\txtFiles\\inputMontador.txt").append("\n");
        mensagem.append("Arquivo de saida: ").append(System.getProperty("user.dir")).append("\\txtFiles\\outputMontador.txt").append("\n\n");
        if (errorMessage.isEmpty())
            mensagem.append("Programa montado com sucesso.");
        else
            mensagem.append("Programa montado com erros. Erro(s): \n").append(errorMessage);
            JOptionPane.showMessageDialog(null, mensagem, "Montador", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getLabel(String linha)
    {
        String[] splited = linha.split("\\s+");
        try 
        {
            if ( (OPTAB.getInstrucaoPorNome(splited[0]) != null) || (POPTAB.get(splited[0]) != null) )  // Nao Possui Label
                return null;
            else // Possui Label
                return splited[0];

        } catch ( Exception e) {
            return null;
        }
    }
    
    private String getOpcode(String linha)
    {
        String[] splited = linha.split("\\s+");
        try
        {
            if ( (OPTAB.getInstrucaoPorNome(splited[0]) != null) || (POPTAB.get(splited[0]) != null) )  // Nao Possui Label
                return splited[0];
            else // Possui Label
                return splited[1];
                
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
            if ( (OPTAB.getInstrucaoPorNome(splited[0]) != null) || (POPTAB.get(splited[0]) != null) )  // Nao Possui Label
            {
                // Split operandos por virgula (ex: ADDR S,T)
                splited = splited[1].split(",");

                for (int i = 0; i < splited.length; i++)
                    if (Registradores.getChaveRegistradorPorNome(splited[i]) != -1) // Registrador
                        operands.add(Integer.toString(Registradores.getChaveRegistradorPorNome(splited[i])));
                    else // Simbolo ou Constante
                        operands.add(splited[i]);  
            }
            else // Possui Label
            {
                // Split operandos por virgula (ex: ADICIONAR ADDR S,T)
                splited = splited[2].split(",");
                
                for (int i = 0; i < splited.length; i++)
                    if(Registradores.getChaveRegistradorPorNome(splited[i]) != -1) // Registrador
                        operands.add(Integer.toString(Registradores.getChaveRegistradorPorNome(splited[i])));
                    else // Simbolo ou Constante
                        operands.add(splited[i]);
            }

            return operands;
        } catch ( Exception e ) {
            return null;
        }
    }

    private static boolean isSymbol(String strNum)
    {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }
}
