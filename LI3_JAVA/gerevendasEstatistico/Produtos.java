import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.io.*;
public class Produtos implements Serializable
{
    private Map<String, ComponentesProduto> produtos;

    public Produtos()
    {
        produtos = new TreeMap<>();
    }
    
    public Produtos(Produtos ps)
    {
        produtos = ps.getProdutos();
    }
    
    public Produtos(Map<String, ComponentesProduto> ps)
    {
        setProdutos(ps);
    }
    
    /** Neste ponto, não existia nenhuma entrada com a filial, por isso para essa nova entrada temos de criar um novo map de produtos */
    public Produtos(String produto, double preco, int unid)
    {
        produtos = new TreeMap<>();

        produtos.put(produto, new ComponentesProduto(preco, unid));
    }
    
    public Map<String, ComponentesProduto> getProdutos()
    {
        Map<String, ComponentesProduto> novo = new TreeMap<>();
        
        produtos.forEach((k,v) -> novo.put(k, v.clone()));
        
        return novo;
    }
   
    public ComponentesProduto getComponentesCodigo(String cod)
    {
        for(Map.Entry<String, ComponentesProduto> e: produtos.entrySet())
        {
            if (e.getKey().equals(cod) == true)
            {
                return e.getValue().clone();
            }
        }
        
        return null;
    }
    
    public void setProdutos(Map<String, ComponentesProduto> ps)
    {
        produtos = new TreeMap<>();
       
       ps.forEach((k,v) -> produtos.put(k, v.clone()));
    }
    
    public void adicionaProduto(String produto, double preco, int quantos)
    {
        /** Aqui sabemos que existia uma entrada com a filial anterior, por isso temos de verificar se já existe uma entrada neste map com o produto recebido */
        if (produtos.containsKey(produto) == false)
        {
            /** Não existe, por isso temos de adicionar o produto */
            produtos.put(produto, new ComponentesProduto(preco, quantos));
        }
        else
        {
            /** Existe, por isso temos de atualizar os valores */
            produtos.get(produto).adicionaComponentesProduto(preco, quantos);
        }
    }
    

    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{produtos});
    }
    
    public Produtos clone()
    {
        return new Produtos(this);
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Produtos ps = (Produtos) o;
        
        if (ps.getProdutos().equals(produtos) == false) return false;
        
       return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(produtos.toString());
        
        return sb.toString();
    }
}
