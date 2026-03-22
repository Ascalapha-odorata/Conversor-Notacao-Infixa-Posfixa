import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
/**
 * Classe ConverterInfixa
 * herda JPanel para exibicao grafica da pilha
 * @author Grupo Extensionista
 * @version 1.0 23/09/2024
 */

class PainelPilha extends JPanel {
    private Stack<Character> pilha;

    public void setPilha(Stack<Character> pilha) {
        this.pilha = pilha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pilha == null || pilha.isEmpty()) return;

        int x = 50; // posição inicial x
        int y = getHeight() - 50; // posição inicial y (base do painel)
        int largura = 50;
        int altura = 30;
        int margem = 5;

        // Desenhando do primeiro elemento ao último, mantendo o topo da pilha no topo do painel
        for (int i = 0; i < pilha.size(); i++) {
            char valor = pilha.get(i);
            g.drawRect(x, y - i * (altura + margem), largura, altura);
            g.drawString(String.valueOf(valor), x + 15, y - i * (altura + margem) + 20);
        }
    }
}


