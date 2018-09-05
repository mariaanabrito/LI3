import java.util.Set;
import java.util.TreeSet;
import java.io.*;

/**
 * Classe que representa um par que contém um código de cliente e um conjunto com os produtos
 * que comprou.
 */
public class ParClienteQuantProds
{
    private String cliente;
    private Set<String> prods;
    
    /**
     * Construtor vazio.
     */
    public ParClienteQuantProds()
    {
        cliente = "";
        prods = new TreeSet<>();
    }
    
    /**
     * Construtor por parâmetro.
     */
    public ParClienteQuantProds(String c, Set<String> s)
    {
        cliente = c;
        setProds(s);
    }
    
    /**
     * Construtor de cópia.
     */
    public ParClienteQuantProds(ParClienteQuantProds p)
    {
        cliente = p.getCliente();
        prods = p.getProds();
    }
    
    /**
     * Método que retorna o código do cliente
     */
    public String getCliente()
    {
        return cliente;
    }
    
    /**
     * Método que define o conjunto dos produtos através do parâmetro recebido pelo método.
     */
    public void setProds(Set<String> s)
    {
        prods = new TreeSet<>();
        for(String f : s)
            prods.add(f);
    }
    
    /**
     * Método que retorna o conjuntos dos produtos.
     */
    public Set<String> getProds()
    {
        Set<String> novo = new TreeSet<>();
        for(String f : prods)
            novo.add(f);
        return novo;
    }
    
    /**
     * Método que adiciona um produto ao conjunto através do parâmetro recebido pelo método.
     */
    public void adicionaProduto(String s)
    {
        prods.add(s);
    }
    
    /**
     * Método que devolve o número de produtos distintos.
     */
    public int getProdsDistintos()
    {
        return prods.size();
    }
    
    /**
     * Método que define o o código do cliente através do parâmetro recebido pelo método.
     */
    public void setCliente(String s)
    {
        cliente = s;
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public ParClienteQuantProds clone()
    {
        return new ParClienteQuantProds(this);
    }
    
    /**
     * Testa a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParClienteQuantProds p = (ParClienteQuantProds) obj;
        if(cliente.equals(p.getCliente()) == false) return false;
        if(prods.equals(p.getProds()) == false) return false;
        return true;
    }
    
    /**
     * Representação textual de ParClienteQuantProds
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente " +cliente +" comprou " +prods.size() +" produtos distintos.\n");
        return sb.toString();
    }
}
