import java.io.*;
/**
 * Write a description of class ParProdutoClientes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParProdutoClientes implements Serializable
{
    private String produto;
    private int clientes;
    
    public ParProdutoClientes()
    {
        produto = "";
        clientes = 0;
    }
    public ParProdutoClientes(String p, int c)
    {
        produto = p;
        clientes = c;
    }
    public ParProdutoClientes(ParProdutoClientes p)
    {
        produto = p.getProduto();
        clientes = p.getNumClientes();
    }
    
    public String getProduto()
    {
        return produto;
    }
    
    public int getNumClientes()
    {
        return clientes;
    }
    
    public void addProduto(String p)
    {
        produto = p;
    }
    
    public void addClientes(int n)
    {
        clientes = n;
    }
    
    public ParProdutoClientes clone()
    {
        return new ParProdutoClientes(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParProdutoClientes p = (ParProdutoClientes) obj;
        if(produto.equals(p.getProduto()) == false) return false;
        if(clientes != p.getNumClientes()) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto " + produto + " com " + clientes+ " clientes distintos.\n");
        return sb.toString();
    }
}
    

