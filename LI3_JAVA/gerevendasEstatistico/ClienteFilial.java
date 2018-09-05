import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Arrays;
import java.io.*;
public class ClienteFilial implements Serializable
{
    private TreeMap<String,Cliente> clientes; /** Chave é o código de cliente */
    
    public ClienteFilial()
    {
        clientes = new TreeMap<>();
    }
    
    public ClienteFilial( TreeMap<String,Cliente> cli)
    {
        setClientesFilial(cli);
    }
    
    public ClienteFilial(ClienteFilial cf)
    {
        clientes = cf.getClientesFilial();
    }
    
    public void setClientesFilial(TreeMap<String,Cliente> cli)
    {
       clientes = new TreeMap<>();
       
       cli.forEach((k,v) -> clientes.put(k, v.clone()));
    }
    
    public TreeMap<String,Cliente> getClientesFilial()
    {
        TreeMap<String,Cliente> novo = new TreeMap<>();
       
        clientes.forEach((k,v) -> novo.put(k, v.clone()));
            
        return novo;
    }
    
    public Cliente getClientePorCodigo(String cod)
    {
        return clientes.get(cod);
    }
    
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
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{clientes});
    }
    
    public ClienteFilial clone()
    {
        return new ClienteFilial(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ClienteFilial c = (ClienteFilial) obj;
        if(clientes.equals(c.getClientesFilial()) == false) return false;
        return true;
    }
    
    public String toString()
    { 
        return clientes.toString();
    }
}
