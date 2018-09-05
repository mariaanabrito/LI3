import java.io.*;
/**
 * Classe que representa um par que contem um código de produto e o número de
 * clientes que o compraram.
 */
public class ParProdutoClientes implements Serializable
{
    private String produto;
    private int clientes;
    
    /**
     * Construtor vazio.
     */
    public ParProdutoClientes()
    {
        produto = "";
        clientes = 0;
    }
    
    /**
     * Construtor por parâmetro
     */
    public ParProdutoClientes(String p, int c)
    {
        produto = p;
        clientes = c;
    }
    
    /**
     * Construtor de cópia
     */
    public ParProdutoClientes(ParProdutoClientes p)
    {
        produto = p.getProduto();
        clientes = p.getNumClientes();
    }
    
    /**
     * Método que devolve o código do produto.
     */
    public String getProduto()
    {
        return produto;
    }
    
    /**
     * Método que devolve o número de clientes.
     */
    public int getNumClientes()
    {
        return clientes;
    }
    
    /**
     * Método que define o código do produto através do parâmetro recebido pelo método.
     */
    public void addProduto(String p)
    {
        produto = p;
    }
    
    /**
     * Método que adiciona um número de clientes comprados para um produto.
     */
    public void addClientes(int n)
    {
        clientes = n;
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public ParProdutoClientes clone()
    {
        return new ParProdutoClientes(this);
    }
    
    /**
     * Método que testa a igualdade entre objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParProdutoClientes p = (ParProdutoClientes) obj;
        if(produto.equals(p.getProduto()) == false) return false;
        if(clientes != p.getNumClientes()) return false;
        return true;
    }
    
    /**
     * Representação textual de ParProdutoClientes.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto " + produto + " com " + clientes+ " clientes distintos.\n");
        return sb.toString();
    }
}
    

