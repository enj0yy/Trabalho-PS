package Montador;

import Instrucoes.Instrucoes;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Montador {
    private String errorMessage = "";

    private Map<String, Integer> SYMTAB;    // Tabela de simbolos
    private Instrucoes OPTAB;               // Tabela de instrucoes

    private ArrayList<String> input = new ArrayList<String>(); 
    public Output output = new Output();

    ArrayList<Line> intermediateFile = new ArrayList<>();

    public Montador(){
        OPTAB = new Instrucoes();

        SYMTAB = new HashMap<String, Integer>();
        SYMTAB.put("A", 0);
        SYMTAB.put("X", 1);
        SYMTAB.put("L", 2);
        SYMTAB.put("B", 3);
        SYMTAB.put("S", 4);
        SYMTAB.put("T", 5);
        SYMTAB.put("PC", 8);
        SYMTAB.put("SW", 9);
    }
    public Map<String, Integer> getSYMTAB() {
        return SYMTAB;
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
        intermediateFile.clear();
        errorMessage = "";
        SYMTAB.put("A", 0);
        SYMTAB.put("X", 1);
        SYMTAB.put("L", 2);
        SYMTAB.put("B", 3);
        SYMTAB.put("S", 4);
        SYMTAB.put("T", 5);
        SYMTAB.put("PC", 8);
        SYMTAB.put("SW", 9);
    }

    public String Montar(String codigoAssembly){
        limpaListas();
        setPrograma(codigoAssembly);
        passoUm();
        passoDois();
        gerarTXTOutput();
        mostrarMensagem();

        return String.join("\n", output.machineCode);
    }

    private void gerarTXTOutput() {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+ "/txtFiles/outputMontador.txt")) 
            {
                fileWriter.write(String.join("\n", output.machineCode));
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
        int lineCounter = 0;
        int LOCCTR = 0;
        
        Line line = new Line();
        line.parser(input.get(lineCounter));

        if(line.opcode.equals("START"))
        {
            LOCCTR = Integer.parseInt(line.operands[0]);
            intermediateFile.add(line); 

            lineCounter +=1; 
            line = new Line();
            line.parser(input.get(lineCounter));
        }
        else
        {
            LOCCTR = 0;
        }

        output.startingAddress = LOCCTR;

        while( !(line.opcode.equals("END")) )
        {
            if( !(line.label.isEmpty()) )
            {  
                if( SYMTAB.containsKey(line.label) )
                {
                    errorMessage = errorMessage + "\nERRO - Multipla definição: " + input.get(lineCounter);
                }
                else
                {
                    SYMTAB.put(line.label,LOCCTR);
                }
            }

            if( OPTAB.getInstrucaoPorNome(line.opcode) != null )
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
                errorMessage = errorMessage + "\nERRO - Opcode Inválido: " + input.get(lineCounter);
            }

            intermediateFile.add(line);

            lineCounter +=1;
            line = new Line();
            line.parser(input.get(lineCounter));
        }
        
        intermediateFile.add(line);
    }

    public void passoDois()
    {
        int lineCounter = 0;
        int LOCCTR = 0;

        String obj = "";

        Line line = intermediateFile.get(lineCounter);

        if ( line.opcode.equals("START") )
        {
            LOCCTR = Integer.parseInt(line.operands[0]);
            lineCounter +=1;
        }
        else
        {
            LOCCTR = 0;
        }

        line = intermediateFile.get(lineCounter);

        while ( !line.opcode.equals("END") )
        {
            if( OPTAB.getInstrucaoPorNome(line.opcode) != null )
            {
                LOCCTR += line.tamanho_instr;

                // Nao possuimos instruçoes formato 1 (Alem de RD e WD que são tratadas a parte)

                if(line.tamanho_instr == 2)
                {
                    obj = montarF2(line);
                    output.machineCode.add(hexToBinary(obj));
                }

                else if(line.tamanho_instr > 2)
                {
                    obj = montarF3F4(line,LOCCTR);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else if (line.opcode.equals("RD"))
            {
                LOCCTR +=1;
                output.machineCode.add("11011000");
            }
            else if (line.opcode.equals("WD"))
            {
                LOCCTR +=1;
                output.machineCode.add("11011100");
            }
            else if(line.opcode.equals("BYTE"))
            {
                LOCCTR +=1;
                char c = line.operands[0].charAt(0);

                if (c == 'C') // ASCII ex: C'EOF'
                {
                    String aux = line.operands[0].substring(2,line.operands[0].length()-1);
                    for(int i=0; i < aux.length(); i++)
                    {
                        obj = String.format("%1$02X",(int)aux.charAt(i) & 0xFF);
                    }
                } else if (c == 'X') // Hexadecimal ex: X'05'
                {
                    obj = line.operands[0].substring(2,line.operands[0].length()-1);
                } else // Numero ex: 5
                {
                    obj = line.operands[0]; 
                }

                output.machineCode.add(hexToBinary(obj));
                
            }
            else if(line.opcode.equals("WORD"))
            {
                LOCCTR +=3;
                int word = Integer.parseInt(line.operands[0]);
                obj = String.format("%1$06X",word & 0xFFFFFF);
                output.machineCode.add(hexToBinary(obj));
            }
            else if(line.opcode.equals("RESW"))
            {
                LOCCTR += line.tamanho_instr;
                int numero_palavras = (line.tamanho_instr)/3;

                for(int i=0; i < numero_palavras; i++)
                {
                    obj = String.format("%1$06X",0x0 & 0xFFFFFF);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else if(line.opcode.equals("RESB"))
            {
                LOCCTR += line.tamanho_instr;
                int numero_bytes = line.tamanho_instr;

                for(int i=0; i < numero_bytes;i++)
                {
                    obj = String.format("%1$02X",0x0 & 0xFF);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else
            {
                errorMessage = errorMessage + "\nERRO - Opcode Inválido: " + input.get(lineCounter);
            }

            lineCounter+=1;
            line = intermediateFile.get(lineCounter);
        }

        output.endAddress = LOCCTR;
        output.set_length();
    }

    public String montarF2(Line line){

        String opCode = String.format("%1$02X", OPTAB.getInstrucaoPorNome(line.opcode).getOpcode()& 0xFF);
        String operando1 = line.operands[0];
        String operando2 = line.operands[1];

        String r1 = "0";
        String r2 = "0";

        if( SYMTAB.containsKey(operando1) ) 
        {
            r1 = String.format("%1$01X", SYMTAB.get(operando1) & 0xF);
        }
        else
        {
            r1 = operando1;
        }

        if( SYMTAB.containsKey(operando2) )
        {
            r2 = String.format("%1$01X", SYMTAB.get(operando2) & 0xF);
        }
        else
        {
            r2 = operando2;
        }

        return opCode + r1 + r2;
    }

    public String montarF3F4(Line line, int PC)
    {
        byte opcode = OPTAB.getInstrucaoPorNome(line.opcode).getOpcode();
        int operand = 0;

        int ni = 0;
        int xbpe = 0;
        int disp = 0;

        int obj = 0;

        String firstByte = "";
        String hexAddress = "";
        
        if( line.prefix.isEmpty() ){
            ni = 0x03;
        }
        else if( line.prefix.equals("#") ) {
            ni = 0x01;
        }
        else if( line.prefix.equals("@") ) {
            ni = 0x02;
        }
        else {
            errorMessage = errorMessage + "\nERRO - Prefixo inválido: " + line.line;
        }


        if( !SYMTAB.containsKey(line.operands[0]) ) // Constante
        {
            try {
                disp = Integer.parseInt(line.operands[0]);
                
            } catch (NumberFormatException e) {
                errorMessage = errorMessage + "\nERRO - Nao foi possivel converter para inteiro: " + line.line;
            }

            xbpe = 0;
            obj = ((opcode & 0xFC) <<16) + (ni<< 16) + (xbpe << 12)+ disp;

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$04X",obj & 0xFFFF);
        }
        else if( line.extended == true ) // Formato 4
        {
            operand = SYMTAB.get(line.operands[0]);
            xbpe = 0x01;
            disp = operand;
            obj = ((opcode & 0xFC) <<24) + (ni<< 24) + (xbpe << 20)+ disp;

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$04X",obj & 0xFFFF);
        }
        else // Formato 3
        {
            operand = SYMTAB.get(line.operands[0]);

            if(line.operands[1].equals("X")) // Indexado
            {
                disp = operand - PC + SYMTAB.get("X");
                xbpe = 0xA;
            }
            else
            {
                disp = operand - PC;
                xbpe = 0x2;
            }
                
            String string_disp = "";

            if(disp < 0)
            {
                string_disp = String.format("%1$01X", disp & 0xFFF);
            }
            else if(disp >=2048) 
            {
                ni = 0x0;
                disp +=PC;
                
                firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
                hexAddress = String.format("%1$04X", disp & 0x7FFF);
            }
            else
            {
                string_disp = String.format("%1$03X", disp & 0xFFF);
            }

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$01X", xbpe & 0xF) + string_disp;
        }

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

    public Output geOutput(){
        return this.output;
    }
    
}
