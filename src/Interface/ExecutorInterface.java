package Interface;

import java.awt.event.ActionEvent;
import java.io.File;

import java.awt.Color;

import Executor.*;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ExecutorInterface extends javax.swing.JFrame {
    private Executor executor;
    private javax.swing.JPanel backgroundPane;
    private javax.swing.JLabel sicLabel;
    private javax.swing.JTextField inputField;
    private javax.swing.JTextField outputField;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton stepButton;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel executeLabel;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JLabel registersLabel;
    private javax.swing.JLabel memoryLabel;
    private javax.swing.JScrollPane memoryPane;
    private javax.swing.JScrollPane registersPane;
    private javax.swing.JTable registerTable;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JList<String> memoryList;
    
    public ExecutorInterface() {
        super("Executor SIC/XE");
        initComponents();
    }

    private void initComponents() {
        executor = new Executor();
        backgroundPane = new javax.swing.JPanel();
        sicLabel = new javax.swing.JLabel();
        
        registersPane = new javax.swing.JScrollPane();
        memoryPane = new javax.swing.JScrollPane();
        
        registerTable = new javax.swing.JTable();
        registersLabel = new javax.swing.JLabel();
        
        executeLabel = new javax.swing.JLabel();
        executeLabel.setText("teste");
        
        inputField = new javax.swing.JTextField();
        outputField = new javax.swing.JTextField();
        inputLabel = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();
        
        memoryList = new javax.swing.JList<>();
        memoryLabel = new javax.swing.JLabel();
        
        loadButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        
        fileChooser = new javax.swing.JFileChooser();

        //Background
        backgroundPane.setBackground(ColorPalette.BG.getColor());
        sicLabel.setForeground(ColorPalette.TITLE.getColor());
        sicLabel.setFont(new java.awt.Font("Arial", 1, 24)); 
        sicLabel.setText("Executor SIC/XE");
        
        // Registradores
        attRegistradores();
        
        registerTable.setForeground(Color.white);
        registerTable.setAlignmentY(1.0F);
        registerTable.setEnabled(false);
        registerTable.setRowHeight(30);
        registerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        registerTable.setShowGrid(true);
        registerTable.setShowVerticalLines(false);
        registerTable.setGridColor(ColorPalette.GRID.getColor());
        registerTable.setFillsViewportHeight(true);
        registerTable.setBackground(ColorPalette.BG_GRID.getColor());
        
        DefaultTableCellRenderer MyHeaderRender = new DefaultTableCellRenderer();
        MyHeaderRender.setBackground(Color.white);
        MyHeaderRender.setForeground(ColorPalette.BG.getColor());
        registerTable.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(MyHeaderRender);
        registerTable.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(MyHeaderRender);        
        
        registersPane.setViewportView(registerTable);
        registersPane.setBorder(BorderFactory.createLineBorder(Color.white));
        
        if (registerTable.getColumnModel().getColumnCount() > 0) {
            registerTable.getColumnModel().getColumn(0).setMinWidth(50);
            registerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            registerTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        registersLabel.setFont(new java.awt.Font("Arial", 0, 20)); 
        registersLabel.setText("Registradores");
        registersLabel.setForeground(ColorPalette.TEXT.getColor());
        

        // Memória
        attMemoria(memoryList);
        memoryList.setBackground(ColorPalette.BG_GRID.getColor());
        memoryList.setForeground(Color.white);
        memoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        memoryList.setSelectionBackground(Color.white);
        memoryList.setSelectionForeground(ColorPalette.BG.getColor());
        memoryList.setFixedCellHeight(25);    
        memoryPane.setViewportView(memoryList);
        memoryPane.setBorder(BorderFactory.createLineBorder(Color.white, WIDTH));
        
        memoryLabel.setFont(new java.awt.Font("Arial", 0, 20)); 
        memoryLabel.setText("Memória");
        memoryLabel.setForeground(ColorPalette.TEXT.getColor());

        // Input e Output
        inputLabel.setFont(new java.awt.Font("Arial", 0, 14)); 
        inputLabel.setText("Input:");
        inputLabel.setForeground(ColorPalette.TEXT.getColor());
        inputField.setText("");
        inputField.setEnabled(false);
        inputField.setBackground(Color.white);
        inputField.setForeground(Color.black);
        inputField.addActionListener((java.awt.event.ActionEvent evt) -> {
            inputFieldActionPerformed(evt);
        });
        
        outputLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        outputLabel.setText("Output:");
        outputLabel.setForeground(ColorPalette.TEXT.getColor());
        outputField.setText("");
        outputField.setEditable(false);

        // Botoes
        loadButton.setText("Carregar Programa");
        loadButton.setBackground(ColorPalette.GRID.getColor());
        loadButton.setForeground(ColorPalette.TEXT.getColor());
        loadButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            loadButtonActionPerformed(evt, memoryList);
        });

        runButton.setText("Run");
        runButton.setEnabled(false);
        runButton.setBackground(ColorPalette.GRID.getColor());
        runButton.setForeground(ColorPalette.TEXT.getColor());
        runButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            runButtonActionPerformed(evt, memoryList);
        });

        stepButton.setText("Step");
        stepButton.setEnabled(false);
        stepButton.setBackground(ColorPalette.GRID.getColor());
        stepButton.setForeground(ColorPalette.TEXT.getColor());
        stepButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            stepButtonActionPerformed(evt, memoryList);
        });      

        // Layout
        javax.swing.GroupLayout backgroundPaneLayout = new javax.swing.GroupLayout(backgroundPane);
        backgroundPane.setLayout(backgroundPaneLayout);
        backgroundPaneLayout.setHorizontalGroup(
            backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPaneLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPaneLayout.createSequentialGroup()
                        .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(memoryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(memoryLabel))
                        .addGap(58, 58, 58)
                        .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registersLabel))
                        .addGap(68, 68, 68)
                        .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(outputLabel)
                                    .addComponent(inputLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(stepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(sicLabel))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        backgroundPaneLayout.setVerticalGroup(
            backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPaneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(sicLabel)
                .addGap(28, 28, 28)
                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundPaneLayout.createSequentialGroup()
                        .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(memoryLabel)
                                    .addComponent(registersLabel)
                                    .addComponent(inputLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(memoryPane)
                                    .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(outputLabel))))
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(ColorPalette.BG.getColor());
        setSize(800,550);
        setResizable(false); 
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // Action Listeners
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        String enteredText = inputField.getText();
        try {
            int value = Integer.parseInt(enteredText);
            if ( value >= 0 && value <= 255 ) {
                    executor.getRegistradores().getRegistradorPorNome("A").setValorInt(value);
                    attRegistradores();
                    stepButton.setEnabled(true);
                    runButton.setEnabled(true);
                    loadButton.setEnabled(true);
                    inputField.setEnabled(false);
                    inputField.setBackground(Color.white);
                    inputField.setForeground(Color.black);
            } else {
                JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void runButtonActionPerformed(ActionEvent evt, JList<String> memoryList) {
        stepButton.setEnabled(false);
        loadButton.setEnabled(false);

        executor.executarPrograma();
        
        inputField.setText("");
        
        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
            inputField.setEnabled(true);
            inputField.setBackground(ColorPalette.TITLE.getColor());  
        }
        attRegistradores();
        attMemoria(memoryList);
        if( executor.getOutput() != -1 ) {
            outputField.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);   
        }
        
        stepButton.setEnabled(false);
        runButton.setEnabled(false);
        loadButton.setEnabled(true);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memoryList) {
        loadButton.setEnabled(false);
        inputField.setText("");
        if ( !executor.executarPasso() ) {
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
        }

        if( executor.getOutput() != -1 ) {
            outputField.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }
        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
            inputField.setEnabled(true);
            inputField.setBackground(ColorPalette.TITLE.getColor());  
        }
        attRegistradores();
        attMemoria(memoryList);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memoryList) {
        executor.setOutput(-1);
        outputField.setText("");
        inputField.setEnabled(false);
        inputField.setBackground(Color.white);
        inputField.setForeground(Color.black);
        
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        try {
            if( fileChooser.showSaveDialog(rootPane) == javax.swing.JFileChooser.APPROVE_OPTION ) {
                File selectedFile = fileChooser.getSelectedFile();
                executor.setPrograma(selectedFile.getAbsolutePath());
                runButton.setEnabled(true);
                stepButton.setEnabled(true);
                inputField.setText("");
                memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
                attMemoria(memoryList);
                attRegistradores();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
    

    // Auxiliary Functions
    private void attRegistradores() {   
        registerTable.setModel(new DefaultTableModel(
            new Object [][] {
                {"PC", executor.getRegistradores().getValorPC()},
                {"A", executor.getRegistradores().getRegistradorPorNome("A").getValorIntSigned()},
                {"X", executor.getRegistradores().getRegistradorPorNome("X").getValorIntSigned()},
                {"L", executor.getRegistradores().getRegistradorPorNome("L").getValorIntSigned()},
                {"B", executor.getRegistradores().getRegistradorPorNome("B").getValorIntSigned()},
                {"S", executor.getRegistradores().getRegistradorPorNome("S").getValorIntSigned()},
                {"T", executor.getRegistradores().getRegistradorPorNome("T").getValorIntSigned()},
                {"SW", executor.getRegistradores().getRegistradorPorNome("SW").getValorIntSigned()}
            },
            new String [] {
                "Nome", "Valor"
            }
        ));
    } 

    private void attMemoria(JList<String> memoryList) {
        memoryList.setModel(new javax.swing.AbstractListModel<String>() {
            byte[] bytes = executor.getMemoria().getMemoria();
            
            @Override
            public int getSize() { return bytes.length; }
            
            @Override
            public String getElementAt(int i) { return String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'); } // transforma caad byte em uma string contendo o valor binário armazenado no byte
        });
    }
}