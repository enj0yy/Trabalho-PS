package Montador;

import Instrucoes.Instrucoes;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import Executor.Registradores;

public class MontadorOld {
    private String errorMessage = "";

    private Instrucoes OPTAB;               // Tabela de instrucoes
    private Map<String, String> POPTAB;     // Tabela de pseudo-instrucoes
    private Map<String, Integer> SYMTAB;    // Tabela de simbolos

    private List<String> input = new ArrayList<String>();
    StringBuilder output = new StringBuilder();

    public MontadorOld()
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
        gerarTXTOutput();
        mostrarMensagem();
        return output.toString();
    }

    public void limpaListas() {
        input.clear();
        output.setLength(0);
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
                output.append(OPTAB.getInstrucaoPorNome(opcode).getOpcode());

                for (String operand : operands)
                {
                    if (isSymbol(operand))
                        if (SYMTAB.get(operand) == null)
                        {
                            output.append(" ").append(Integer.toHexString(0).toUpperCase());
                            errorMessage = errorMessage + "\nERRO - Simbolo nao definido: " + linha;
                        }
                        else
                            output.append(" ").append(Integer.toHexString(SYMTAB.get(operand)).toUpperCase());     
                    else
                        output.append(" ").append(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());      
                }   
            }
            else if (POPTAB.get(opcode) != null) // Pseudo-instrucao
            {
                switch (opcode) 
                {
                    case "RD":
                        output.append("D8");
                        break;

                    case "WD":
                        output.append("DC");
                        break;
                        
                    case "WORD":
                        for (String operand : operands)
                            output.append(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                        break;

                    case "BYTE":
                        for (String operand : operands)
                        {
                            if (operands.get(0).charAt(0) == 'C') // BYTE C'EOF' vai de string(EOF) para HEX(454F46)
                                for (int i = 2; i < operands.get(0).length()-1; i++)
                                    output.append(Integer.toHexString((int)operands.get(0).charAt(i)).toUpperCase());

                            else if (operands.get(0).charAt(0) == 'X') //BYTE X'05' para HEX(05)
                                output.append(operands.get(0).substring(2, operands.get(0).length()-1).toUpperCase());

                            else // BYTE 10 vai de decimal(10) para HEX(0A)
                                output.append(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                        }   
                        break;

                    case "RESW":
                    case "RESB":
                        for (String operand : operands)
                            for (Integer i = 0; i < Integer.parseInt(operand); i++)
                            {
                                output.append("0");
                                if (i<Integer.parseInt(operand)-1)
                                    output.append(System.lineSeparator());
                            }
                        break;


                    default:
                        break;
                }
            }
            output.append(System.lineSeparator());
        }
    }

    private void gerarTXTOutput() {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMontador.txt")) 
            {
                fileWriter.write(output.toString());
                fileWriter.close();
            } catch (IOException e) {
                errorMessage = errorMessage + "\nERRO - Erro ao gerar arquivo de saida.";
            }
    }


    private void mostrarMensagem()
    {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Arquivo de saida: ").append(System.getProperty("user.dir")).append("/txtFiles/outputMontador.txt").append("\n\n");
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
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }
}
