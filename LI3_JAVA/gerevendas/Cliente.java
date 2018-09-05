import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.math.BigDecimal;

/**
 *  Classe que representa as compras de um determinado cliente, apresentando o produto, o preço dele, e a 
 *  quantidade de vezes que foi comprado.
 */
public class Cliente implements Serializable
{
    private ArrayList<Double> preco;
    private ArrayList<Integer> quantidades;
    private ArrayList<String> produtos;
    
    /**
     * Construtor vazio.
     */
    public Cliente()
    {
        preco = new ArrayList<>();
        quantidades = new ArrayList<>();
        produtos = new ArrayList<>();
    }
    
    /**
     * Construtor por parâmetro
     */
    public Cliente(ArrayList<Double> prec, ArrayList<Integer> quantos, ArrayList<String> prods)
    {
        setPrecoFil(preco);
        setQuantidadesFil(quantos);
        setProdutosFil(prods);
    }
    
    /**
     * Construtor de cópia
     */
    public Cliente(Cliente c)
    {
        preco = c.getPrecoFil();
        quantidades = c.getQuantidadesFil();
        produtos = c.getProdutosFil();
    }
    
    /**
     * Definir todos os preços dos produtos comprados através do parâmetro recebido no método.
     */
    public void setPrecoFil(ArrayList<Double> prec)
    {
        preco = new ArrayList<>();
        
        for(Double p: prec)
            preco.add(p);
    }
    
    /**
     * Definir todas as quantidades dos produtos comprados através do parâmetro recebido no método.
     */
    public void setQuantidadesFil(ArrayList<Integer> quantos)
    {
        quantidades = new ArrayList<>();
        
        for(Integer q: quantos)
            quantidades.add(q);
    }
    
    /**
     * Definir todos os produtos comprados através do parâmetro recebido no método.
     */
    public void setProdutosFil(ArrayList<String> prods)
    {
        produtos = new ArrayList<>();
        
        for(String s: prods)
            produtos.add(s);
    }
    
    /**
     * Método que retorna um determinado preço de um produto.
     */
    public double getPrecoIndice(int i)
    {
        return preco.get(i);
    }
    
    /**
     * Método que retorna umas determinadas quantidades  de um produto.
     */
    public int getQuantidadesIndice(int i)
    {
        return quantidades.get(i);
    }
    
    /**
     * Método que retorna um determinado produto.
     */
    public String getProdutoIndice(int i)
    {
        return produtos.get(i);
    }
    
    /**
     * Método que retorna todos os preços dos produtos.
     */
    public ArrayList<Double> getPrecoFil()
    {
        return preco;
    }
    
    /**
     * Método que retorna todas as quantidades dos produtos.
     */
    public ArrayList<Integer> getQuantidadesFil()
    {
        return quantidades;
    }
    
    /**
     * Método que retorna todos os produtos.
     */
    public ArrayList<String> getProdutosFil()
    {      
        return produtos;
    } 
    
    /**
     * Método que adiciona um produto comprado pelo cliente, as quantidades, e o preço dele.
     */
    public void adicionaCliente(double prec, int quantos, String prod)
    {
        produtos.add(prod);
        quantidades.add(quantos);
        preco.add(prec);
    }
    
    /**
     * Método que retorna o número de compras efetuadas pelo cliente.
     */
    public int getNumVendas()
    {
        return produtos.size();
    }
    
    /**
     * Método que retorna o total gasto por um cliente.
     */
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
    
    /**
     * Método de hash.
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{quantidades, preco, produtos});
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public Cliente clone()
    {
        return new Cliente(this);
    }
    
    /**
     * Método que verifica a igualdade entre dois objetos.
     */
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
    
    /**
     * Representação textual de um cliente.
     */
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
