import java.util.Set;
import java.util.HashSet;
import java.io.*;
import java.util.Arrays;
/**
 * Write a description of class ParUnidadesClientes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParUnidadesClientes implements Serializable
{
    private Set<String> clientes;
    private int quantos;
    
    public ParUnidadesClientes()
    {
        clientes  = new HashSet<>();
        quantos = 0;
    }
    
    public ParUnidadesClientes(Set<String> cli, int qua)
    {
        setClientes(cli);
        quantos = qua;
    }
    
    public ParUnidadesClientes(ParUnidadesClientes p)
    {
        clientes = p.getClientes();
        quantos = p.getQuantos();
    }
    
    public void setClientes(Set<String> cli)
    {
        clientes = new HashSet<>();
        for(String s : cli)
            clientes.add(s);
    }
    
    public Set<String> getClientes()
    {
        Set<String> novo = new HashSet<>();
        for(String s : clientes)
            novo.add(s);
        return novo;
    }
    
    public int getQuantos()
    { 
        return quantos;
    }
    
    public void addCliente(String s)
    {
        clientes.add(s);
    }
    
    public void addUnidade(int n)
    {
        quantos += n;
    }
    
    public int getNumClientes()
    {
        return clientes.size();
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{clientes, quantos});
    }
    
    public ParUnidadesClientes clone()
    {
        return new ParUnidadesClientes(this);
    }
    
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParUnidadesClientes p = (ParUnidadesClientes) obj;
        if(quantos != p.getQuantos()) return false;
        if(clientes.equals(p.getClientes()) == false) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(clientes.toString());
        sb.append(quantos);
        
        return sb.toString();
    }
}
