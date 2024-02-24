package Interface;


import javax.swing.*;

import ProcessadorDeMacros.ProcessadorDeMacros;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class ProcessadorMacrosInterface extends JFrame{
    private final ProcessadorDeMacros processadorDeMacros;
    private String content;
    private JPanel headerPanel;
    private JTextArea codeArea;
    private JTextArea macrosArea;
    private JButton converterButton;
    private JButton selectFileButton;
    private JButton montadorButton;
    private JButton limparButton;
    private JLabel sicLabel;
    private JScrollPane outputScrollPane;
    private JScrollPane inputPane;

    public ProcessadorMacrosInterface() {
        super("Processador de Macros SIC/XE");
        processadorDeMacros = new ProcessadorDeMacros();
        initComponents();
    }

    private void initComponents() {
        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(ColorPalette.BG.getColor());
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 45, 10, 10));
        sicLabel = new JLabel();
        sicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sicLabel.setForeground(ColorPalette.TITLE.getColor());
        sicLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
        sicLabel.setText("Processador de Macros SIC/XE");
        headerPanel.add(sicLabel);

        codeArea = new JTextArea(500, 400);
        codeArea.setBackground(ColorPalette.BG_GRID.getColor());
        codeArea.setForeground(Color.white);
        inputPane = new JScrollPane(codeArea);


        macrosArea = new JTextArea(500, 400);
        macrosArea.setEditable(false);
        macrosArea.setBackground(ColorPalette.BG_GRID.getColor());
        macrosArea.setForeground(Color.white);
        outputScrollPane = new JScrollPane(macrosArea);

        converterButton = new JButton("Converter");
        converterButton.setEnabled(false);
        converterButton.setBackground(ColorPalette.GRID.getColor());
        converterButton.setForeground(ColorPalette.TEXT.getColor());
        converterButton.addActionListener((ActionEvent e) -> {
            converter();
        });

        converterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        converterButton.setPreferredSize(new Dimension(150, 50)); 

        selectFileButton = new JButton("Selecionar");
        selectFileButton.setBackground(ColorPalette.GRID.getColor());
        selectFileButton.setForeground(ColorPalette.TEXT.getColor());
        selectFileButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            selectFileButtonActionPerformed(evt);
        });

        selectFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectFileButton.setPreferredSize(new Dimension(150, 50));

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel innerLeftPanel = new JPanel();
        innerLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerLeftPanel.setBackground(ColorPalette.BG.getColor());

        JLabel codeLabel = new JLabel("Código");
        codeLabel.setFont(new Font("Arial", 0, 20));
        codeLabel.setForeground(ColorPalette.TEXT.getColor());

        innerLeftPanel.add(codeLabel);
        leftPanel.add(innerLeftPanel, BorderLayout.NORTH);
        leftPanel.add(inputPane, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 30, 15));
        leftPanel.setBackground(ColorPalette.BG.getColor());

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(converterButton);
        centerPanel.add(selectFileButton);
        centerPanel.setPreferredSize(new Dimension(200, 600)); 
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));
        centerPanel.setBackground(ColorPalette.BG.getColor());

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel innerRightPanel = new JPanel();
        innerRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerRightPanel.setBackground(ColorPalette.BG.getColor());

        JLabel macrosLabel = new JLabel("Macros");
        macrosLabel.setForeground(ColorPalette.TEXT.getColor());
        macrosLabel.setFont(new Font("Arial", 0, 20));

        montadorButton = new JButton("Montador");
        montadorButton.setBackground(ColorPalette.GRID.getColor());
        montadorButton.setForeground(ColorPalette.TEXT.getColor());
        montadorButton.setPreferredSize(new Dimension(110, 50));
        montadorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            chamaMontador();
        });

        limparButton = new JButton("Limpar");
        limparButton.setBackground(ColorPalette.GRID.getColor());
        limparButton.setForeground(ColorPalette.TEXT.getColor());
        limparButton.setPreferredSize(new Dimension(110, 50));
        limparButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            limpar();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        buttonPanel.setBackground(ColorPalette.BG.getColor());
        buttonPanel.add(montadorButton);
        buttonPanel.add(limparButton);

        innerRightPanel.add(macrosLabel);
        rightPanel.add(innerRightPanel, BorderLayout.NORTH);
        rightPanel.add(outputScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.setPreferredSize(new Dimension(300, 600)); 
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 30, 45));  
        rightPanel.setBackground(ColorPalette.BG.getColor());

        // Body Panel
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(ColorPalette.BG.getColor());
        horizontalPanel.add(leftPanel);
        horizontalPanel.add(centerPanel);
        horizontalPanel.add(rightPanel);

        // Main Panel
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(horizontalPanel, BorderLayout.CENTER);

        pack();

        // Frame settings
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(ColorPalette.BG.getColor());
        setSize(800, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void converter() {
        processadorDeMacros.setPrograma(content);
        processadorDeMacros.macroProcessor();
        codeArea.setText(processadorDeMacros.getOutput());
        converterButton.setEnabled(false);
    }

    private void chamaMontador() {
        setVisible(false);
        new MontadorInterface();
    }

    private void limpar() {
        codeArea.setText("");
        macrosArea.setText("");
        content = "";
        processadorDeMacros.limpar();
    }

    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        limpar();
        converterButton.setEnabled(true);
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                StringBuilder contentBuilder = new StringBuilder();
                for (String line : lines) {
                    contentBuilder.append(line).append("\n");
                }
                content = contentBuilder.toString();
                separaString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void separaString() {
        String macros = "";
        String programa = "START";
        String separada[] = content.split("START");

        if (separada.length > 1) {
            macros = separada[0];
            programa += separada[1];
        } else {
            programa += separada[0];
        }

        codeArea.setText(programa);
        macrosArea.setText(macros);
    }
}