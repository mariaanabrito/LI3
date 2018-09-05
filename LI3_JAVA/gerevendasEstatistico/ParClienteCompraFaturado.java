import java.io.*;
import java.util.Arrays;

public class ParClienteCompraFaturado implements Serializable
{
    private String cliente;
    private int quantos;
    private double faturado;
    
    public ParClienteCompraFaturado()
    {
        cliente = "";
        quantos = 0;
        faturado = 0.0;
    }
    
    public ParClienteCompraFaturado(String cli, int q, double fat)
    {
        cliente = cli;

        quantos = q;
        faturado = fat;
    }
    
    public ParClienteCompraFaturado(ParClienteCompraFaturado cf)
    {
        cliente = cf.getCliente();
        quantos = cf.getQuantidades();
        faturado = cf.getFaturado();
    }
    
    public String getCliente()
    {
        return cliente;
    }
    
    public int getQuantidades()
    {
        return quantos;
    }
    
    public double getFaturado()
    {
        return faturado;
    }
    
    public void setCliente(String cli)
    {
        cliente = cli;
    }
    
    public void adicionaQuantidade(int n)
    {
        quantos += n;
    }
    
    public void adicionaFaturado(double fat)
    {
        faturado += fat;
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{quantos, cliente, faturado});
    }
    
    public ParClienteCompraFaturado clone()
    {
        return new ParClienteCompraFaturado(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        ParClienteCompraFaturado p = (ParClienteCompraFaturado) obj;
        if(p.getCliente().equals(cliente) == false) return false;
        if(quantos != p.getQuantidades()) return false;
        if(faturado != p.getFaturado()) return false;
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente " +cliente+ " comprou " +quantos+ " vezes o produto e gastou " +faturado +"\n");
        return sb.toString();
    }
}