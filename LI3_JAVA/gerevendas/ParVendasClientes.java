import java.io.*;

/**
 * Classe que contém número de vendas e número de clientes diferentes que as realizaram.
 */
public class ParVendasClientes implements Serializable
{
    /**
     * Variáveis de instância que representam o número de vendas e o número de clientes diferentes.
     */
    private int vendas, clientes;
    
    /**
     * Construtor Vazio
     */
    public ParVendasClientes()
    {
        vendas = 0;
        clientes = 0;
    }
    
    /**
     * Construtor por parâmetros
     */
    public ParVendasClientes(int ven, int cli)
    {
        vendas = ven;
        clientes = cli;
    }
    
    /**
     * Construtor por cópia
     */
    public ParVendasClientes(ParVendasClientes p)
    {
        vendas = p.getVendasTotal();
        clientes = p.getClientesTotais();
    }
    
    /**
     * Devolve número de vendas
     */
    public int getVendasTotal()
    {
        return vendas;
    }
    
    /**
     * Devolve número de clientes diferentes
     */
    public int getClientesTotais()
    {
        return clientes;
    }
    
    /**
     * Adiciona um novo número de vendas
     */
    public void adicionaVendas(int n)
    {
        vendas += n;
    }
    
    /**
     * Adiciona um novo número de clientes diferentes
     */
    public void adicionaClientes(int n)
    {
        clientes += n;
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public ParVendasClientes clone()
    {
        return new ParVendasClientes(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParVendasClientes p = (ParVendasClientes) obj;
        if(vendas != p.getVendasTotal() || clientes != p.getClientesTotais()) return false;
        return true;
    }
    
    /**
     * Devolve representação textual do número de vendas e número de clientes diferentes que as realizaram
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" Número global de vendas realizadas : " + vendas + "\n");
        sb.append(" Número total de clientes diferentes : " + clientes + "\n");
        return sb.toString();
    }
}
