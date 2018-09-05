import java.io.*;

/**
 * Classe que representa um par que contem um código de produto e as quantidades
 * vendidas desse mesmo produto.
 */
public class ParProdutoUnidades implements Serializable
{
    private String produto;
    private int quantos;
    
    /**
     * Construtor vazio.
     */
    public ParProdutoUnidades()
    {
        produto = "";
        quantos = 0;
    }
    
    /**
     * Construtor por parâmetro.
     */
    public ParProdutoUnidades(String p, int q)
    {
        produto = p;
        quantos = q;
    }
    
    /**
     * Construtor de cópia
     */
    public ParProdutoUnidades(ParProdutoUnidades p)
    {
        produto = p.getProduto();
        quantos = p.getQuantos();
    }
    
    /**
     * Método que devolve o código de produto.
     */
    public String getProduto()
    {
        return produto;
    }
    
    /**
     * Método que devolve as quantidades.
     */
    public int getQuantos()
    {
        return quantos;
    }
    
    /**
     * Método que adiciona um número às quantidades.
     */
    public void adicionaQuantidade(int n)
    {
        quantos += n;
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public ParProdutoUnidades clone()
    {
        return new ParProdutoUnidades(this);
    }
    
    /**
     * Método que testa a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParProdutoUnidades p = (ParProdutoUnidades) obj;
        if(produto.equals(p.getProduto()) == false) return false;
        if(quantos != p.getQuantos()) return false;
        return true;
    }
    
    /**
     * Representação textual de ParProdutoUnidades
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto " +produto +" com quantidade " +quantos +" \n");
        return sb.toString();
    }
    
}
