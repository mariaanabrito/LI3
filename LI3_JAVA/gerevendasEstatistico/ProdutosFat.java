import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.io.*;
public class ProdutosFat implements Serializable
{
    private Map<Integer, Produtos> produtosfat; /** Chave é filial */
    
    public ProdutosFat()
    {
        produtosfat = new TreeMap<>();
    }
    
    public ProdutosFat(ProdutosFat pf)
    {
        produtosfat = pf.getProdutosFat();
    }
    
    public ProdutosFat(Map<Integer, Produtos> ps)
    {
        setProdutosFat(ps);
    }
    
    /** Vai ser usado quando o map anterior não tiver uma entrada existente com o mês, logo para esse mês vamos ter de criar map novo com a filial recebida*/
    public ProdutosFat(int filial, String produto, double preco, int quantos)
    {
        produtosfat = new TreeMap<>();
        
        produtosfat.put(filial, new Produtos(produto, preco, quantos));
    }
    
    public Map<Integer, Produtos> getProdutosFat()
    {
        Map<Integer, Produtos> novo = new TreeMap<>();
       
        produtosfat.forEach((k,v) -> novo.put(k, v.clone()));
        
        return novo;
    }
    
    public Produtos getProdutosFilial(int f)
    {
        Produtos p = produtosfat.get(f).clone();
        
        if(p == null)
            return null;
            
        return p;
    }
    
    public void setProdutosFat(Map<Integer, Produtos> ps)
    {
        produtosfat = new TreeMap<>();
       
        ps.forEach((k,v) -> produtosfat.put(k, v.clone()));
    }
    
    public void adicionaProdutoFat(int filial, String produto, double preco, int quantos)
    {
        /** Neste ponto, existe uma entrada no map anterior com o mês. Agora temos de verificar se neste map atual existe uma entrada com a filial ou não.*/
        if (produtosfat.containsKey(filial) == false)
        {
            /** Não existe entrada com a filial, logo temos de criar um map de produtos de raiz para esta nova entrada*/
            produtosfat.put(filial, new Produtos(produto, preco, quantos));
        }
        else
        {
            /** Existe já uma entrada com a filial, por isso temos de adicionar o produto ao map desta entrada*/
            produtosfat.get(filial).adicionaProduto(produto, preco, quantos);
        }
    }
    
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{produtosfat});
    }
    
    public ProdutosFat clone()
    {
        return new ProdutosFat(this);
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        ProdutosFat pf = (ProdutosFat) o;
        
        if (pf.getProdutosFat().equals(produtosfat) == false) return false;
        
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(produtosfat.toString());
        
        return sb.toString();
    }
}
