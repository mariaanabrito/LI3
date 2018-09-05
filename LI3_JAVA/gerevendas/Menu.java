import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe Menu é responsável por mostrar e executar os menus da aplicação, bem como ler a opção inserida pelo utilizador.
 */
public class Menu {
    /**
     * Variáveis de instância que representam as opções do menu em vigor e a opção escolhida pela utilizador.
     */
    private List<String> opcoes;
    private int op;
    
    /**
     * Construtor por parâmetro
     */
    public Menu(String[] opcoes) {
        this.opcoes = new ArrayList<String>();
        
        for (String op: opcoes) 
            this.opcoes.add(op);
            
        this.op = 0;
    }

    /**
     * Método que apresenta o menu e lê uma opção.
     */
    public void executarMenu() {
        do
        {
            showMenu();
            this.op = lerOpcao();
        }while(this.op == -1);
    }
    
    /** 
     * Método que apresenta o menu 
     */
    private void showMenu() {
        System.out.println("\n *** Menu *** ");
        
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        
        System.out.println("0 - Sair");
    }
    
    /** 
     * Método que lê uma opção válida 
     */
    private int lerOpcao() {
        int op; 
        Scanner is = new Scanner(System.in);
        
        System.out.print("Opção: ");
        try {
            op = is.nextInt();
        }
        catch (InputMismatchException e) { 
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        is.close();
        return op;
    }
    
    /**
     * Método que obtém a última opção lida
     */
    public int getOpcao() {
        return this.op;
    }
    
    /**
     * Método que insere a opção 0 num menu.
     */
    public void setOpcao(int n)
    {
        this.op = 0;
    }
}
