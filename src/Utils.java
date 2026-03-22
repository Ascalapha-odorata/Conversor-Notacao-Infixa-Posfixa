import javax.swing.*;
import java.util.Stack;
/**
 * Escreva uma descrição da classe Utils aqui.
 * metodos uteis para a compilacao do programa
 * @author Grupo Extensionista
 * @version 2.0 19/11/2024
 */
public class Utils{
    /**
     * Method verificarInfixaValida verifica se a expressão infixa é válida
     *
     * @param infixa A parameter
     * @return The return value
     */
    public boolean verificarInfixaValida(String infixa){
        boolean v = false;
        
        if(infixa.length() > 0){
            boolean anteriorFoiOperador = false;
            boolean possuiOperador = false;
            char primeiro = infixa.charAt(0);
            char ultimo = infixa.charAt(infixa.length() - 1);
            char atual, anterior;
            int k = 0;
            while(k < infixa.length()){ //verificar se tem espacos
                if(Character.isWhitespace(infixa.charAt(k)) == true){
                    JOptionPane.showMessageDialog(null, "Erro: A infixa não pode conter espaços em branco!");
                    return false;
                }
                k++;
            }
            
            anterior = infixa.charAt(0);
            for(int j = 0; j < infixa.length(); j++){
                atual = infixa.charAt(j);
                if(Character.isLetterOrDigit(atual) || (atual == '(' ) || (atual == ')' ) ){
                    anteriorFoiOperador = false;
                }
                else if((atual == '+') || (atual == '-') || (atual == '*') || (atual == '/') || (atual == ';')){
                    if(anteriorFoiOperador == true && (atual != '(' ) && (atual != ')') && (anterior != '(' ) && (anterior != ')') ){
                        JOptionPane.showMessageDialog(null, "Erro: A infixa não pode conter dois operadores consecutivos!");
                        return false;
                    }
                    else{
                        possuiOperador = true;
                        if( (anterior != '(' ) && (anterior != ')') ){
                            anteriorFoiOperador = true;
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Erro: A infixa contém caracteres não permitidos!");
                    return false;
                }
                anterior = atual;
            }
            
            if(Character.isLetterOrDigit(primeiro) == false && ( primeiro != '(' ) ){
                JOptionPane.showMessageDialog(null, "Erro: A infixa deve começar com um operando!");
            } else if(ultimo != ';'){
                JOptionPane.showMessageDialog(null, "Erro: A infixa deve terminar com ';'!");
            } else if(infixa.length() < 3){
                JOptionPane.showMessageDialog(null, "Erro: A infixa deve conter no mínimo três caracteres!");
            } else if(possuiOperador == false){
                JOptionPane.showMessageDialog(null, "Erro: A infixa deve conter no mínimo um operador!");
            } else if(verificaParenteses(infixa) == false){
                JOptionPane.showMessageDialog(null, "Erro: inserção incorreta de parênteses !");
            } else {
                v = true;
            }
        }
        
        return v;
    }
    
    /**
     * Method verificaParenteses verifica se o numero de parenteses '(' e ')' é igual.
     *
     * @param infixa A parameter
     * @return The return value
     */
    public boolean verificaParenteses(String infixa){
        boolean ok = false, continua = true;
        Stack<Character> pilhaPar = new Stack<>();
        int k = 0;
        while(k < infixa.length() && continua == true){
            if(infixa.charAt(k) == '('){
                pilhaPar.push(infixa.charAt(k));
            } else if(infixa.charAt(k) == ')'){
                if(pilhaPar.empty() == false){
                    pilhaPar.pop();
                } else {
                    continua = false;
                }
            }
            k++;
        }
        
        if(pilhaPar.empty() && continua == true){
            ok = true;
        }

        return ok;
    }
    
    public void sobre(){
        JOptionPane.showMessageDialog(null, "Programa de conversão de expressões infixas para posfixas (operadores são adicionados após os operandos)" + 
                                      "\nRegras:\n1. A notação infixa não pode conter espaços em branco.\n2. A notação infixa deve começar com um operando e terminar com ';'." + 
                                      "\n3. A notação infixa deve conter no mínimo 3 caracteres (nesse caso, dois operandos e um operador)." + 
                                      "\n4. A notação infixa não pode conter dois operadores consecutivos.\n5. Os operandos aceitos são as letras do alfabeto e números, " + 
                                      "e os operadores aceitos são +, -, * e /. Parênteses também são aceitos.");
    }
}
