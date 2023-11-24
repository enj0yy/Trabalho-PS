package Interface;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JList;

import Executor.*;

public class ExecutorInterface extends javax.swing.JFrame {

    public ExecutorInterface() {
        executor = new Executor();
        initComponents();
    }

    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        registerTable = new javax.swing.JTable();
        registerLabel = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        outputField = new javax.swing.JTextField();
        inputLabel = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();
        memoryLabel = new javax.swing.JLabel();
        loadButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        javax.swing.JList<String> memoryList = new javax.swing.JList<>();
        fileChooser = new javax.swing.JFileChooser();


        // REGISTER TABLE AND LABEL 
        registerTable.setBackground(new java.awt.Color(176, 179, 184));
        attRegistradores();
        registerTable.setAlignmentY(1.0F);
        registerTable.setEnabled(false);
        registerTable.setGridColor(new java.awt.Color(0, 0, 0));
        registerTable.setRowHeight(30);
        registerTable.setSelectionBackground(new java.awt.Color(204, 204, 255));
        registerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        registerTable.setShowGrid(true);
        jScrollPane2.setViewportView(registerTable);
        if (registerTable.getColumnModel().getColumnCount() > 0) {
            registerTable.getColumnModel().getColumn(0).setMinWidth(50);
            registerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            registerTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        registerLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        registerLabel.setText("Registers");
        registerLabel.setForeground(new java.awt.Color(228, 230, 235));
        // END

        // MEMORY LIST AND LABEL
        attMemoria(memoryList);
        memoryList.setBackground(new java.awt.Color(176, 179, 184));
        memoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        memoryList.setVisibleRowCount(20);
        jScrollPane1.setViewportView(memoryList);

        memoryLabel.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        memoryLabel.setText("Memory");
        memoryLabel.setForeground(new java.awt.Color(228, 230, 235));
        // END

        // INPUT AND OUTPUT
        inputLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        inputLabel.setText("Input:");
        inputLabel.setForeground(new java.awt.Color(228, 230, 235));
        
        inputField.setText("");
        inputField.setEnabled(false);
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        outputField.setText("");
        outputField.setEditable(false);

        outputLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        outputLabel.setText("Output:");
        outputLabel.setForeground(new java.awt.Color(228, 230, 235));
        // END


        // BUTTONS
        loadButton.setText("Load Addresses");
        loadButton.setBackground(new java.awt.Color(58, 59, 60));
        loadButton.setForeground(new java.awt.Color(228, 230, 235));
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt, memoryList);
            }
        });

        runButton.setText("Run");
        runButton.setEnabled(false);
        runButton.setBackground(new java.awt.Color(58, 59, 60));
        runButton.setForeground(new java.awt.Color(228, 230, 235));
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt, memoryList);
            }
        });

        stepButton.setText("Step");
        stepButton.setEnabled(false);
        stepButton.setBackground(new java.awt.Color(58, 59, 60));
        stepButton.setForeground(new java.awt.Color(228, 230, 235));
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt, memoryList);
            }
        });
        // END


        // LAYOUT
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputField, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(outputField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(memoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(memoryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)))))
                .addGap(66, 66, 66))
        );

        pack();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(24, 25, 26));
        setSize(getPreferredSize());
        setResizable(false);
        // END
    }


    // ACTION LISTENERS
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        String enteredText = inputField.getText();
        try {
            int value = Integer.parseInt(enteredText);
            if ( value >= 0 && value <= 255 ) {
                    executor.getRegistrador("A").setValor(value);
                    executor.getRegistrador("PC").setValor(executor.getRegistrador("PC").getValor()+1);
                    attRegistradores();
                    stepButton.setEnabled(true);
                    runButton.setEnabled(true);
                    loadButton.setEnabled(true);
                    inputField.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            System.out.println("Não é um inteiro válido");
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
        }
        attRegistradores();
        attMemoria(memoryList);
    }

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memoryList) {
        executor.setOutput(-1);
        outputField.setText("");
        try {
            if( fileChooser.showSaveDialog(rootPane) == javax.swing.JFileChooser.APPROVE_OPTION ) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
                executor.setPrograma(selectedFile.getAbsolutePath());
                attMemoria(memoryList);
                attRegistradores();
                runButton.setEnabled(true);
                stepButton.setEnabled(true);
                inputField.setText("");
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
    // END


    // AUXILIARY FUNCTIONS
    private void attRegistradores() {
        registerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PC", executor.getRegistrador("PC").getValor()},
                {"A", executor.getRegistrador("A").getValor()},
                {"X", executor.getRegistrador("X").getValor()},
                {"L", executor.getRegistrador("L").getValor()},
                {"B", executor.getRegistrador("B").getValor()},
                {"S", executor.getRegistrador("S").getValor()},
                {"T", executor.getRegistrador("T").getValor()},
                {"SW", executor.getRegistrador("SW").getValor()}
            },
            new String [] {
                "Name", "Value"
            }
        ));
    } 

    private void attMemoria(JList<String> memoryList) {
        memoryList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = executor.getMemoria().toArray(new String[0]);
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
    }
    // END


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExecutorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExecutorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExecutorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExecutorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExecutorInterface().setVisible(true);
            }
        });
    }

    // VARIABLES
    private Executor executor;
    private javax.swing.JTextField inputField;
    private javax.swing.JTextField outputField;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton stepButton;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JLabel registerLabel;
    private javax.swing.JLabel memoryLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable registerTable;
    private javax.swing.JFileChooser fileChooser;
}