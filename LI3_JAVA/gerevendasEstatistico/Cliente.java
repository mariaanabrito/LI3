import java.util.Vector;
import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class Cliente implements Serializable
{
    private Vector<Double> preco;
    private Vector<Integer> quantidades;
    private Vector<String> produtos;
    
    public Cliente()
    {
        preco = new Vector<>();
        quantidades = new Vector<>();
        produtos = new Vector<>();
    }
    
    public Cliente(Vector<Double> prec, Vector<Integer> quantos, Vector<String> prods)
    {
        setPrecoFil(preco);
        setQuantidadesFil(quantos);
        setProdutosFil(prods);
    }
    
    public Cliente(Cliente c)
    {
        preco = c.getPrecoFil();
        quantidades = c.getQuantidadesFil();
        produtos = c.getProdutosFil();
    }
    
    public void setPrecoFil(Vector<Double> prec)
    {
        preco = new Vector<>();
        
        for(Double p: prec)
            preco.add(p);
    }
    
    public void setQuantidadesFil(Vector<Integer> quantos)
    {
        quantidades = new Vector<>();
        
        for(Integer q: quantos)
            quantidades.add(q);
    }
    
    public void setProdutosFil(Vector<String> prods)
    {
        produtos = new Vector<>();
        
        for(String s: prods)
            produtos.add(s);
    }
    
    public double getPrecoIndice(int i)
    {
        return preco.get(i);
    }
    
    public int getQuantidadesIndice(int i)
    {
        return quantidades.get(i);
    }
    
    public String getProdutoIndice(int i)
    {
        return produtos.get(i);
    }
    
    public Vector<Double> getPrecoFil()
    {
        return preco;
    }
    
    public Vector<Integer> getQuantidadesFil()
    {
        return quantidades;
    }
    
    public Vector<String> getProdutosFil()
    {      
        return produtos;
    } 
    
    public void incrementaQuantidades(int indice, int quantos)
    {
        int quan;
        
        quan = quantidades.get(indice); /** Quantas unidades tem atualmente */
        quantidades.add(indice, quantos + quan); /** Agora vai ter o que tinha antes + a nova quantidade */
    }
    
    public void adicionaCliente(double prec, int quantos, String prod)
    {
            produtos.add(prod);
            quantidades.add(quantos);
            preco.add(prec);
    }
    
    public int getNumVendas()
    {
        return produtos.size();
    }
    
    public double getFaturacao()
    {
        double total = 0.0, aux;
        BigDecimal bd;
        for(int i = 0; i < produtos.size(); i++)
        {
            total += quantidades.get(i) * preco.get(i);
        }
        
        return total;
    }
    
    
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{quantidades, preco, produtos});
    }
    
    public Cliente clone()
    {
        return new Cliente(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Cliente c = (Cliente) obj;
        if(c.getQuantidadesFil().equals(quantidades) == false) return false;
        if(c.getPrecoFil().equals(preco) == false) return false;
        if(c.getProdutosFil().equals(produtos) == false) return false;
        return true;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < produtos.size(); i++)
        {
            sb.append("Produto : " + produtos.get(i) + "\n");
            sb.append("Quantidade" + quantidades.get(i) + "\n");
            sb.append("Preço " + preco.get(i) + "\n");
        }
        return sb.toString();
    }
}
