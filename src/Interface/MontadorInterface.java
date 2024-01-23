package Interface;

import Montador.Montador;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.io.*;
import java.nio.file.Files;

//@SuppressWarnings("serial")
public class MontadorInterface extends JFrame {
    private final Montador montador;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton montarButton;

    public MontadorInterface() {
        super("Montador SIC/XE");
        montador = new Montador();
        initComponents();
    }

    private void initComponents() {
        inputArea = new JTextArea(500, 400);
        JScrollPane inputScrollPane = new JScrollPane(inputArea);

        outputArea = new JTextArea(500, 400);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        montarButton = new JButton("Montar");
        montarButton.addActionListener((ActionEvent e) -> {
            montarPrograma();
        });

        montarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        montarButton.setPreferredSize(new Dimension(500, 200)); 

        JButton selectFileButton = new JButton("Selecionar");
        selectFileButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            selectFileButtonActionPerformed(evt);
        });

        selectFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectFileButton.setPreferredSize(new Dimension(500, 200));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(inputScrollPane, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, 600));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(montarButton);
        centerPanel.add(selectFileButton);
        centerPanel.setPreferredSize(new Dimension(300, 600)); 

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(outputScrollPane, BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(300, 600)); 

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.add(leftPanel);
        this.add(centerPanel);
        this.add(rightPanel);

        pack();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorPalette.BG.getColor());
        setSize(800, 550);
        setResizable(false);
    }

    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                inputArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void montarPrograma() {
        String input = inputArea.getText();
        try {
            String out = montador.montarPrograma(input);
            outputArea.setText(out);
        } catch (Exception e) {
            outputArea.setText("Erro ao montar o programa: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MontadorInterface montadorInterface = new MontadorInterface();
        });
    }
}
