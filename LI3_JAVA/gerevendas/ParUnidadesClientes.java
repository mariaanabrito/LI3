import java.util.Set;
import java.util.TreeSet;
import java.io.*;
/**
 * Classe que representa um para contendo um conjunto de clientes e um valor para quantidades.
 */
public class ParUnidadesClientes implements Serializable
{
    private Set<String> clientes;
    private int quantos;
    
    /**
     * Construtor vazio 
     */
    public ParUnidadesClientes()
    {
        clientes  = new TreeSet<>();
        quantos = 0;
    }
    
    /**
     * Construtor por parâmetro
     */
    public ParUnidadesClientes(Set<String> cli, int qua)
    {
        setClientes(cli);
        quantos = qua;
    }
    
    /**
     * Construtor de cópia
     */
    public ParUnidadesClientes(ParUnidadesClientes p)
    {
        clientes = p.getClientes();
        quantos = p.getQuantos();
    }
    
    /**
     * Define o conjunto de clientes através do parâmetro recebido pelo método.
     */
    public void setClientes(Set<String> cli)
    {
        clientes = new TreeSet<>();
        for(String s : cli)
            clientes.add(s);
    }
    
    /**
     * Método que retorna o conjunto dos clientes.
     */
    public Set<String> getClientes()
    {
        Set<String> novo = new TreeSet<>();
        for(String s : clientes)
            novo.add(s);
        return novo;
    }
    
    /**
     * Método que retorna as quantidades.
     */
    public int getQuantos()
    { 
        return quantos;
    }
    
    /**
     * Método que adiciona um cliente ao conjunto de clientes.
     */
    public void addCliente(String s)
    {
        clientes.add(s);
    }
    
    /**
     * Método que adiciona uma quantidade às quantidades da classe.
     */
    public void addUnidade(int n)
    {
        quantos += n;
    }
    
    /**
     * Método que devolve o número de clientes.
     */
    public int getNumClientes()
    {
        return clientes.size();
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public ParUnidadesClientes clone()
    {
        return new ParUnidadesClientes(this);
    }
    
    /**
     * Método que testa a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParUnidadesClientes p = (ParUnidadesClientes) obj;
        if(quantos != p.getQuantos()) return false;
        if(clientes.equals(p.getClientes()) == false) return false;
        return true;
    }
    
    /**
     * Representação textual de ParUnidadesCliente.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(clientes.toString());
        sb.append(quantos);
        return sb.toString();
    }
}
