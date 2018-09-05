import java.util.Set;
import java.util.HashSet;
import java.io.*;
import java.util.Arrays;

public class ParClienteQuantProds
{
    private String cliente;
    private Set<String> prods;
    
    public ParClienteQuantProds()
    {
        cliente = "";
        prods = new HashSet<>();
    }
    
    public ParClienteQuantProds(String c, Set<String> s)
    {
        cliente = c;
        setProds(s);
    }
    
    public ParClienteQuantProds(ParClienteQuantProds p)
    {
        cliente = p.getCliente();
        prods = p.getProds();
    }
    
    public String getCliente()
    {
        return cliente;
    }
    
    public void setProds(Set<String> s)
    {
        prods = new HashSet<>();
        for(String f : s)
            prods.add(f);
    }
    
    
    public Set<String> getProds()
    {
        Set<String> novo = new HashSet<>();
        for(String f : prods)
            novo.add(f);
        return novo;
    }
    
    public void adicionaProduto(String s)
    {
        prods.add(s);
    }
    
    public int getProdsDistintos()
    {
        return prods.size();
    }
    
    public void setCliente(String s)
    {
        cliente = s;
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{cliente, prods});
    }
    
    public ParClienteQuantProds clone()
    {
        return new ParClienteQuantProds(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParClienteQuantProds p = (ParClienteQuantProds) obj;
        if(cliente.equals(p.getCliente()) == false) return false;
        if(prods.equals(p.getProds()) == false) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente " +cliente +" comprou " +prods.size() +" produtos distintos.\n");
        return sb.toString();
    }
}
