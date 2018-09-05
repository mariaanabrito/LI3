import java.io.*;
public class ParVendasClientes implements Serializable
{
    private int vendas, clientes;
    
    public ParVendasClientes()
    {
        vendas = 0;
        clientes = 0;
    }
    
    public ParVendasClientes(int ven, int cli)
    {
        vendas = ven;
        clientes = cli;
    }
    
    public ParVendasClientes(ParVendasClientes p)
    {
        vendas = p.getVendasTotal();
        clientes = p.getClientesTotais();
    }
    
    public int getVendasTotal()
    {
        return vendas;
    }
    
    public int getClientesTotais()
    {
        return clientes;
    }
    
    public void adicionaVendas(int n)
    {
        vendas += n;
    }
    
    public void adicionaClientes(int n)
    {
        clientes += n;
    }
    
    public ParVendasClientes clone()
    {
        return new ParVendasClientes(this);
    }
    

    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParVendasClientes p = (ParVendasClientes) obj;
        if(vendas != p.getVendasTotal() || clientes != p.getClientesTotais()) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" Número global de vendas realizadas : " + vendas + "\n");
        sb.append(" Número total de clientes diferentes : " + clientes + "\n");
        return sb.toString();
    }
}
