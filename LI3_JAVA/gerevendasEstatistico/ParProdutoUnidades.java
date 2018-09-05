import java.io.*;
import java.util.Arrays;

public class ParProdutoUnidades implements Serializable
{
    private String produto;
    private int quantos;
    
    public ParProdutoUnidades()
    {
        produto = "";
        quantos = 0;
    }
    
    public ParProdutoUnidades(String p, int q)
    {
        produto = p;
        quantos = q;
    }
    
    public ParProdutoUnidades(ParProdutoUnidades p)
    {
        produto = p.getProduto();
        quantos = p.getQuantos();
    }
    
    public String getProduto()
    {
        return produto;
    }
    
    public int getQuantos()
    {
        return quantos;
    }
    
    public void adicionaQuantidade(int n)
    {
        quantos += n;
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{produto, quantos});
    }
    
    public ParProdutoUnidades clone()
    {
        return new ParProdutoUnidades(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParProdutoUnidades p = (ParProdutoUnidades) obj;
        if(produto.equals(p.getProduto()) == false) return false;
        if(quantos != p.getQuantos()) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto " +produto +" com quantidade " +quantos +" \n");
        return sb.toString();
    }
    
}
