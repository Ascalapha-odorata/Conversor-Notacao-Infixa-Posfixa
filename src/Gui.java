import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe Gui
 * classe principal que define a interface grafica
 * @author Grupo Extensionista
 * @version 1.0 23/09/2024
 */
public class Gui extends JFrame {
    private ConverterInfixa converter;
    public ArrayList<String> passos = new ArrayList<>();

    // Componentes principais da interface
    private JToolBar barraComandos = new JToolBar();
    private JToolBar barraEntrada = new JToolBar();
    
    // Botões de controle
    private JButton bExecutar = new JButton("Executar");
    private JButton bEditar = new JButton("Editar");
    private JButton bReiniciar = new JButton("Reiniciar");
    private JButton bSobre = new JButton("Sobre");
    private JButton bSair = new JButton("Sair");

    // Botões de navegação entre os passos
    private JButton bPassar = new JButton("Passar"); 
    private JButton bVoltar = new JButton("Voltar"); 
    
    // Entrada e exibição de informações
    private JLabel msgExp = new JLabel("Expressao infixa: ");
    public JTextField tfExpressao = new JTextField(20);
    private JTextArea areaPassos = new JTextArea(15, 30);
    private JScrollPane scrollPassos = new JScrollPane(areaPassos);
    
    // Estruturas para armazenar passos e estados da pilha
    private ArrayList<Stack<Character>> estadosPilha = new ArrayList<>();
    private int indicePassoAtual = 0; 

    // Pilha principal usada para a conversão infixa para posfixa
    Stack<Character> pilha = new Stack<>();

    // Painel para representar visualmente a pilha
    private PainelPilha painelPilha = new PainelPilha();
    Utils utils = new Utils(); // Classe utilitária (assumida já implementada)

    // Construtor da classe GUI
    public Gui(int larg, int alt) {
        super("Conversor de Notacao Infixa para Posfixa (Polonesa Reversa)"); 
        converter = new ConverterInfixa();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        setResizable(false); 

        // Adiciona botões à barra de comandos
        barraComandos.add(bExecutar);
        barraComandos.add(bEditar);
        barraComandos.add(bReiniciar);
        barraComandos.add(bSobre);
        barraComandos.add(bSair);
        
        // Adiciona componentes de entrada
        barraEntrada.add(msgExp);
        barraEntrada.add(tfExpressao);
        
        areaPassos.setEditable(false); // Torna a área de passos somente leitura

        // Adiciona as barras à janela
        add(barraComandos, BorderLayout.SOUTH);
        add(barraEntrada, BorderLayout.NORTH);
        
        // Configuração do painel central        
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(scrollPassos, BorderLayout.CENTER);
        
        painelPilha.setPreferredSize(new Dimension(300, 200));
        centro.add(painelPilha, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        // Painel de navegação
        JPanel painelNavegacao = new JPanel();
        painelNavegacao.add(bVoltar);
        painelNavegacao.add(bPassar);
        add(painelNavegacao, BorderLayout.EAST);

        // Configurações iniciais
        tfExpressao.setText("A+B*A/C+D;");
        tfExpressao.setEnabled(false);
        bPassar.setVisible(false); 
        bVoltar.setVisible(false);
        
        // Listener do botão "Executar"
        bExecutar.addActionListener(e -> {
            try {
                String entrada = tfExpressao.getText();
                if (utils.verificarInfixaValida(entrada)) { // Verifica validade da expressão
                    passos = converter.converterInfixaPosfixa(this); // Realiza a conversão
                    indicePassoAtual = 0;
                    mostrarPassoAtual(); // Exibe o primeiro passo
                    bPassar.setVisible(true);
                    bVoltar.setVisible(true);
                    bEditar.setEnabled(false);
                    tfExpressao.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Erro: Expressão vazia!");
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Erro!");
            }
        });
        
        // Listener para avançar nos passos
        bPassar.addActionListener(e -> {
            if (indicePassoAtual < passos.size() - 1) {
                indicePassoAtual++;
                mostrarPassoAtual();
            }
        });

        // Listener para voltar nos passos
        bVoltar.addActionListener(e -> {
            if (indicePassoAtual > 0) {
                indicePassoAtual--;
                mostrarPassoAtual();
            }
        });

        // Listener do botão "Editar"
        bEditar.addActionListener(e -> tfExpressao.setEnabled(true)); // Permite edição da expressão
        
        // Listener do botão "Reiniciar"
        bReiniciar.addActionListener(e -> {
            bEditar.setEnabled(true);
            passos.clear();
            estadosPilha.clear();
            areaPassos.setText(""); 
            indicePassoAtual = 0;
            bPassar.setVisible(false);
            bVoltar.setVisible(false);
            painelPilha.setPilha(new Stack<>()); // Limpa a pilha
            painelPilha.repaint();
        });
        
        // Listener do botão "Sobre"
        bSobre.addActionListener(e -> utils.sobre()); // Exibe informações sobre o programa
        
        // Listener do botão "Sair"
        bSair.addActionListener(e -> System.exit(0)); // Fecha a aplicação

        setVisible(true); // Torna a janela visível
    }
    
    // Exibe o passo atual e atualiza a representação da pilha
    private void mostrarPassoAtual() {
        areaPassos.setText(passos.get(indicePassoAtual));
        if(indicePassoAtual < estadosPilha.size()){
            painelPilha.setPilha(estadosPilha.get(indicePassoAtual));
            painelPilha.repaint();
        }
    }
    
    public void salvarEstadoPilha() {
        Stack<Character> copiaPilha = (Stack<Character>) pilha.clone();
        estadosPilha.add(copiaPilha);
    }
}
