import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe ConverterInfixa
 * realiza a conversao de infixa para posfixa
 * @author Grupo Extensionista
 * @version 1.0 23/09/2024
 */
public class ConverterInfixa {
    private ArrayList<Stack<Character>> estadosPilha = new ArrayList<>(); // Armazena os estados da pilha ao longo do processo

    // Método principal que realiza a conversão de infixa para posfixa
    public ArrayList<String> converterInfixaPosfixa(Gui gui){
        String infixa = gui.tfExpressao.getText(); // Obtém a expressão infixa da interface
        String posfixa = ""; // Variável para armazenar a expressão posfixa
        char charInfixa;
        char charTopo;
        int k = 0;
        int i = 1; // Contador das operações
        boolean ok;
        Token token;

        if(infixa.length() > 0){ // Verifica se a expressão não está vazia
            do{
                charInfixa = infixa.charAt(k); // Obtém o caractere atual da expressão infixa
                token = obterToken(charInfixa); // Obtém o tipo do token (parêntese, operador, etc.)

                if(token == Token.PAR_ESQ){ // Se for um parêntese esquerdo
                    gui.pilha.push(charInfixa); // Empilha o parêntese
                    gui.salvarEstadoPilha(); // Salva o estado da pilha
                    gui.passos.add("Operação " + i + " \n- Empilhando '('\nPosfixa = " + posfixa);
                    i++;
                }
                else if(token == Token.OP_ADD){ // Se for um operador de soma ou subtração
                    ok = false;
                    do{
                        if(gui.pilha.isEmpty() == true){ // Se a pilha estiver vazia, empilha o operador
                            gui.pilha.push(charInfixa);
                            gui.salvarEstadoPilha();
                            gui.passos.add("Operação " + i + " \n- Empilhando '" + charInfixa + "'\nPosfixa = " + posfixa);
                            i++;
                            ok = true;
                        }
                        else{
                            charTopo = gui.pilha.peek(); // Verifica o topo da pilha
                            if(charTopo == '('){ // Se o topo for '(', empilha o operador
                                gui.pilha.push(charInfixa);
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Empilhando '('\nPosfixa = " + posfixa);
                                i++;
                                ok = true;
                            }
                            else{ // Caso contrário, desempilha e adiciona ao posfixa
                                posfixa = posfixa + charTopo;
                                gui.pilha.pop();
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Desempilhando '" + charTopo + "'\nPosfixa = " + posfixa);
                                i++;
                            }
                        }
                    } while(ok == false);
                }
                else if(token == Token.OP_MULT){ // Se for um operador de multiplicação ou divisão
                    ok = false;
                    do{
                        if(gui.pilha.isEmpty() == true){ // Se a pilha estiver vazia, empilha o operador
                            gui.pilha.push(charInfixa);
                            gui.salvarEstadoPilha();
                            gui.passos.add("Operação " + i + " \n- Empilhando " + charInfixa + "'\nPosfixa = " + posfixa);
                            i++;
                            ok = true;
                        }
                        else{
                            charTopo = gui.pilha.peek(); // Verifica o topo da pilha
                            if((charTopo == '(') || (charTopo == '+') || (charTopo == '-')){ // Se o topo for um operador inferior, empilha
                                gui.pilha.push(charInfixa);
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Empilhando " + charInfixa + "'\nPosfixa = " + posfixa);
                                i++;
                                ok = true;
                            }
                            else{ // Caso contrário, desempilha e adiciona ao posfixa
                                posfixa = posfixa + charTopo;
                                gui.pilha.pop();
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Desempilhando '" + charTopo + "'\nPosfixa = " + posfixa);
                                i++;
                            }
                        }
                    } while(ok == false);
                }
                else if(token == Token.PAR_DIR){ // Se for um parêntese direito
                    if(gui.pilha.isEmpty() == false){
                        ok = false;
                        do{
                            charTopo = gui.pilha.peek(); // Verifica o topo da pilha
                            if(charTopo == '('){ // Se o topo for '(', desempilha
                                gui.pilha.pop();
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Desempilhando '" + charTopo + "'\nPosfixa = " + posfixa);
                                i++;
                                ok = true;
                            }
                            else{ // Caso contrário, desempilha e adiciona ao posfixa
                                posfixa = posfixa + charTopo;
                                gui.pilha.pop();
                                gui.salvarEstadoPilha();
                                gui.passos.add("Operação " + i + " \n- Desempilhando '" + charTopo + "'\nPosfixa = " + posfixa);
                                i++;
                            }
                        } while(ok == false);
                    }
                }
                else if(token == Token.TERMINAL){ // Se for o caractere terminal ';'
                    while(gui.pilha.isEmpty() == false){ // Desempilha todos os operadores restantes
                        charTopo = gui.pilha.peek();
                        posfixa = posfixa + charTopo;
                        gui.pilha.pop();
                        gui.salvarEstadoPilha();
                        gui.passos.add("Operação " + i + " \n- Desempilhando '" + charTopo + "'\nPosfixa = " + posfixa);
                        i++;
                    }
                }
                else{ // Se for um operando, adiciona diretamente ao posfixa
                    posfixa = posfixa + charInfixa;
                }
                k++; // Avança para o próximo caractere da expressão
            } while((k < infixa.length()) && (token != Token.TERMINAL));
        }
        posfixa = posfixa + ";"; // Finaliza a expressão posfixa com ';'
        gui.passos.add("FINAL: Posfixa = " + posfixa); // Adiciona o resultado final nos passos
        
        return gui.passos; // Retorna os passos realizados na conversão
    }

    // Retorna o estado da pilha em um índice específico
    public Stack<Character> getEstadoPilha(int indice) {
        return estadosPilha.get(indice);
    }

    // Método que identifica o tipo de token baseado no caractere
    public Token obterToken(char ch) {
        switch (ch) {
            case '(': return Token.PAR_ESQ;
            case ')': return Token.PAR_DIR;
            case '+': case '-': return Token.OP_ADD;
            case '*': case '/': return Token.OP_MULT;
            case ';': return Token.TERMINAL;
            default: return Token.LETRA;
        }
    }
}
