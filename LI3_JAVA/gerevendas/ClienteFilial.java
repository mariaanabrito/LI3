import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.io.*;

/**
 * Classe que armazena uma correspondência entre o código de clientes e a classe Cliente.
 * A classe cliente armazena as compras de um determinado cliente.
 */
public class ClienteFilial implements Serializable
{
    private HashMap<String,Cliente> clientes; /** Chave é o código de cliente */
    
    /**
     * Construtor vazio.
     */
    public ClienteFilial()
    {
        clientes = new HashMap<>(171009);
    }
    
    /**
     * Construtor por parâmetro.
     */
    public ClienteFilial( HashMap<String,Cliente> cli)
    {
        setClientesFilial(cli);
    }
    
    /**
     * Construtor de cópia.
     */
    public ClienteFilial(ClienteFilial cf)
    {
        clientes = cf.getClientesFilial();
    }
    
    /**
     * Define a correspondência através do parâmetro recebido pelo método.
     */
    public void setClientesFilial(HashMap<String,Cliente> cli)
    {
       clientes = new HashMap<>(171009);
       
       cli.forEach((k,v) -> clientes.put(k, v.clone()));
    }
    
    /**
     * Retorna a correspondência da classe ClienteFilial.
     */
    public HashMap<String,Cliente> getClientesFilial()
    {
        HashMap<String,Cliente> novo = new HashMap<>(171009);
       
        clientes.forEach((k,v) -> novo.put(k, v.clone()));
            
        return novo;
    }
    
    /**
     * Método que retorna um Cliente através do código de cliente recebido no parâmetro.
     */
    public Cliente getClientePorCodigo(String cod)
    {
        return clientes.get(cod);
    }
    
    /**
     * Método que adiciona o código de cliente, um código de produto, um preço, um valor
     * de quantidades o adiciona à classe
     */
    public void adicionaClienteFilial(String cli, String prod, double prec, int quantos)
    {
        if(clientes.containsKey(cli) == false)
        {
            Cliente c = new Cliente();
            c.adicionaCliente(prec, quantos, prod);
            clientes.put(cli, c);
        }
        else
        {
            clientes.get(cli).adicionaCliente(prec, quantos, prod);
        }
    }
    
    /**
     * Método de hash.
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{clientes});
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public ClienteFilial clone()
    {
        return new ClienteFilial(this);
    }
    
    /**
     * Método que verifica a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ClienteFilial c = (ClienteFilial) obj;
        if(clientes.equals(c.getClientesFilial()) == false) return false;
        return true;
    }
    
    /**
     * Representação textual de ClienteFilial.
     */
    public String toString()
    { 
        return clientes.toString();
    }
}
