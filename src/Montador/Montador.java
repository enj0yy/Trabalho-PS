package Montador;

import Instrucoes.Instrucoes;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/*
 * Todo:
 * Tratar Formato 1
 * Tirar o pc++ das funçoes que chamam o calcular TA, pois já chama lá (Igual fiz com o ADD)
 */

public class Montador {
    private String errorMessage = "";

    private Map<String, Integer> SYMTAB;    // Tabela de simbolos
    private Instrucoes OPTAB;               // Tabela de instrucoes

    private ArrayList<String> input = new ArrayList<String>(); 
    public ListingObject output = new ListingObject();

    ArrayList<ParserLine> medfile = new ArrayList<>();

    public Montador(){
        OPTAB = new Instrucoes();

        SYMTAB = new HashMap<String, Integer>();
        SYMTAB.put("A", 0);
        SYMTAB.put("X", 1);
        SYMTAB.put("L", 2);
        SYMTAB.put("B", 3);
        SYMTAB.put("S", 4);
        SYMTAB.put("T", 5);
        SYMTAB.put("PC", 6);
        SYMTAB.put("SW", 7);
    }

    public void setPrograma(String codigoAssembly) 
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        for (String linha : linhas){
            input.add(linha);
        }
    }

    public void limpaListas() 
    {
        input.clear();
        output.reset();
        SYMTAB.clear();
        medfile.clear();
        errorMessage = "";
        SYMTAB.put("A", 0);
        SYMTAB.put("X", 1);
        SYMTAB.put("L", 2);
        SYMTAB.put("B", 3);
        SYMTAB.put("S", 4);
        SYMTAB.put("T", 5);
        SYMTAB.put("PC", 6);
        SYMTAB.put("SW", 7);
    }

    public String Montar(String codigoAssembly){
        limpaListas();
        setPrograma(codigoAssembly);
        passoUm();
        passoDois();
        gerarTXTOutput();
        mostrarMensagem();

        return String.join("\n", output.TextRecord);
    }

    private void gerarTXTOutput() {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMontador.txt")) 
            {
                fileWriter.write(String.join("", output.TextRecord));
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

    public void passoUm()
    {
        int lc = 0;
        int LOCCTR = 0;
        
        ParserLine line = new ParserLine();
        line.parser(input.get(lc));

        if(line.opcode.equals("START"))
        {
            LOCCTR = Integer.parseInt(line.operands[0]);
            medfile.add(line); 

            lc +=1; 
            line = new ParserLine();
            line.parser(input.get(lc));
        }
        else
        {
            LOCCTR = 0;
        }

        output.startingAddress = LOCCTR;

        while(!(line.opcode.equals("END")))
        {
            if( !(line.label.isEmpty()) )
            {  
                if(SYMTAB.containsKey(line.label))
                {
                    errorMessage = errorMessage + "\nERRO - Multipla definição: " + input.get(lc);
                }
                else
                {
                    SYMTAB.put(line.label,LOCCTR);
                }
            }

            if(OPTAB.getInstrucaoPorNome(line.opcode) != null)
            {
                int tamanhoIntrucao = OPTAB.getInstrucaoPorNome(line.opcode).getLength();
                switch (tamanhoIntrucao) 
                {
                    case 3:
                        if(line.extended)
                        {
                            LOCCTR += 4;
                            line.set_tamanho_instr(4);
                        }
                        else
                        {
                            LOCCTR += 3;
                            line.set_tamanho_instr(3);
                        }
                    break;
                    default:
                        LOCCTR += tamanhoIntrucao;
                        line.set_tamanho_instr(tamanhoIntrucao);
                        break;
                }
            }
            else if (line.opcode.equals("RD") || line.opcode.equals("WD"))
            {
                LOCCTR +=1;
                line.set_tamanho_instr(1);
            }
            else if (line.opcode.equals("WORD"))
            {
                LOCCTR +=3;
                line.set_tamanho_instr(3);
            }
            else if(line.opcode.equals("RESW"))
            {
                int aux = Integer.parseInt(line.operands[0]); 
                LOCCTR = LOCCTR + (3*aux);
                line.set_tamanho_instr(3*aux);
            }
            else if(line.opcode.equals("RESB"))
            {
                int aux = Integer.parseInt(line.operands[0]); 
                LOCCTR += aux;
                line.set_tamanho_instr(aux);
            }
            else if(line.opcode.equals("BYTE"))
            {
                LOCCTR += 1; 
                line.set_tamanho_instr(1);
            }
            else
            {
                errorMessage = errorMessage + "\nERRO - Opcode Inválido: " + input.get(lc);
            }

            medfile.add(line);
            line = new ParserLine();
            lc +=1;
            line.parser(input.get(lc));
        }
        
        medfile.add(line);
    }

    public void passoDois()
    {
        int lc = 0;
        int LOCCTR = 0;
        String obj;
        ArrayList<String> machine_code = new ArrayList<>();

        if (medfile.get(lc).opcode.equals("START"))
        {
            LOCCTR = Integer.parseInt(medfile.get(lc).operands[0]);
            lc +=1;
        }
        else
        {
            LOCCTR = 0;
        }

        while (!medfile.get(lc).opcode.equals("END"))
        {
            if( OPTAB.getInstrucaoPorNome(medfile.get(lc).opcode) != null )
            {
                LOCCTR += medfile.get(lc).tamanho_instr;

                if(medfile.get(lc).tamanho_instr == 2)
                {
                    obj = montarF2(medfile.get(lc));
                    machine_code.add(hexToBinary(obj));
                }

                else if(medfile.get(lc).tamanho_instr > 2)
                {
                    obj = montarF3F4(medfile.get(lc),LOCCTR);
                    machine_code.add(hexToBinary(obj));
                }
            }
            else if (medfile.get(lc).opcode.equals("RD"))
            {
                LOCCTR +=1;
                machine_code.add("11011000");
            }
            else if (medfile.get(lc).opcode.equals("WD"))
            {
                LOCCTR +=1;
                machine_code.add("11011100");
            }
            else if(medfile.get(lc).opcode.equals("BYTE"))
            {
                LOCCTR +=1;
                char c = medfile.get(lc).operands[0].charAt(0);
                obj = c+"";
                machine_code.add(hexToBinary(obj));
                
            }
            else if(medfile.get(lc).opcode.equals("WORD"))
            {
                LOCCTR +=3;
                int word = Integer.parseInt(medfile.get(lc).operands[0]);
                obj = String.format("%1$06X",word & 0xFFFFFF);
                machine_code.add(hexToBinary(obj));
            }
            else if(medfile.get(lc).opcode.equals("RESW"))
            {
                LOCCTR += medfile.get(lc).tamanho_instr;
                int numero_palavras = (medfile.get(lc).tamanho_instr)/3;
                for(int i=0; i < numero_palavras;i++){
                    obj = String.format("%1$06X",0x0 & 0xFFFFFF);
                    machine_code.add(hexToBinary(obj));
                }
            }
            else if(medfile.get(lc).opcode.equals("RESB"))
            {
                LOCCTR += medfile.get(lc).tamanho_instr;
                int numero_bytes = medfile.get(lc).tamanho_instr;
                for(int i=0; i < numero_bytes;i++){
                    obj = String.format("%1$02X",0x0 & 0xFF);
                    machine_code.add(hexToBinary(obj));
                }
            }
            else
            {
                errorMessage = errorMessage + "\nERRO - Opcode Inválido: " + input.get(lc);
            }
            lc+=1;
        }

        output.endAddress = LOCCTR;
        output.set_length();
        output.TextRecord = machine_code;
    }

    public String montarF2(ParserLine line){

        String OpCode = String.format("%1$02X", OPTAB.getInstrucaoPorNome(line.opcode).getOpcode()& 0xFF);
        String operando1 = line.operands[0];
        String operando2 = line.operands[1];

        String r1 = "0";
        String r2 = "0";
        
        String object_code;
        String prefixo = line.prefix;

        // Usando Endereço númerico do Registrador
        if(prefixo.equals("#"))
        {
            try {
                r1 = operando1;
            } catch (NumberFormatException e) {
                errorMessage = errorMessage + "\nERRO - O endereço passsado não pode ser convertido para constante do tipo inteiro";
            }
            if(!operando2.isEmpty()){
                try {
                    r2 = operando2;
                    
                } catch (NumberFormatException e) {
                    errorMessage = errorMessage + "\nERRO - O endereço passado não pode ser convertido para constante do tipo inteiro";
                }
            }
        }

        // Usando nome do Registrador
        else{
            if(SYMTAB.containsKey(operando1)){
                r1 = String.format("%1$01X", SYMTAB.get(operando1) & 0xF);
                if(SYMTAB.containsKey(operando2)){
                    r2 = String.format("%1$01X", SYMTAB.get(operando2) & 0xF);
                }
            }
            else{
                errorMessage = errorMessage + "\nERRO - Simbolo não definido: " + operando1;
            }
        }
        object_code = OpCode + r1+r2;
        return object_code;
    }

    public String montarF3F4(ParserLine line, int PC)
    {
        int ni = 0;
        byte opcode = OPTAB.getInstrucaoPorNome(line.opcode).getOpcode();
        int operand;
        int disp=0;
        int xbpe;
        int obj;
        String prefixo = line.prefix;
        
        if(prefixo.isEmpty()){
            ni = 0x03;
        }
        else if(prefixo.equals("#")){
            ni = 0x01;
        }
        else if(prefixo.equals("@")){
            ni = 0x02;
        }
        else{
            errorMessage = errorMessage + "\nERRO - Prefixo inválido";
        }


        if( !SYMTAB.containsKey(line.operands[0]) ) // Constante
        {
            try {
                disp = Integer.parseInt(line.operands[0]);
                
            } catch (NumberFormatException e) {
                errorMessage = errorMessage + "\nERRO - Input String cannot be parsed to Integer.";
            }
            xbpe = 0;
            obj = ((opcode & 0xFC) <<16) + (ni<< 16) + (xbpe << 12)+ disp;
        }
        else if(line.extended == true) // Extendido - Formato 4
        {
            operand = SYMTAB.get(line.operands[0]);
            xbpe = 0x01;
            disp = operand;
            obj = ((opcode & 0xFC) <<24) + (ni<< 24) + (xbpe << 20)+ disp;
        }
        else // Formato 3
        {
            operand = SYMTAB.get(line.operands[0]);

            if(line.operands[1].equals("X"))
            {
                    disp = operand - PC + SYMTAB.get("X");
                    xbpe = 0xA;
            }
            else
            {
                    disp = operand - PC;
                    xbpe = 0x2;
            }
                
            String string_disp;

            if(disp < 0)
            {
                string_disp = String.format("%1$01X", disp & 0xFFF);
            }
            else if(disp >=2048) 
            {
                errorMessage = errorMessage + "\nERRO - Formato SIC padrão detectado!";
                ni = 0x0;
                disp +=PC;
                string_disp = String.format("%1$04X", disp & 0x7FFF);
                String firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
                return firstByte + string_disp;

            }
            else
            {
                string_disp = String.format("%1$03X", disp & 0xFFF);
            }

            String string_xbpe = String.format("%1$01X", xbpe & 0xF);
            String hexAddress = string_xbpe + string_disp;
            String firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            return firstByte + hexAddress;
        }

        String firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
        String hexAddress = String.format("%1$04X",obj & 0xFFFF);

        return firstByte + hexAddress;
    }

    String hexToBinary(String hex)
    {
        String binary = "";
 
        hex = hex.toUpperCase();

        HashMap<Character, String> hashMap = new HashMap<Character, String>();
 
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");
 
        int i;
        char ch;
 
        for (i = 0; i < hex.length(); i++) {
            ch = hex.charAt(i);
            if (hashMap.containsKey(ch))
                binary += hashMap.get(ch);
            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }
        return binary;
    }
    
}
