import java.io.*;

/**
 * Classe que contém o código de cliente e o número de vezes que comprou um certo produto, bem como o dinheiro gasto nele
 */
public class ParClienteCompraFaturado implements Serializable
{
    /**
     * Código do cliente
     */
    private String cliente;
    /**
     * Número de vezes que comprou um certo produto
     */
    private int quantos;
    /**
     * Total de dinheiro que gastou num certo produto
     */
    private double faturado;
    
    /**
     * Construtor Vazio
     */
    public ParClienteCompraFaturado()
    {
        cliente = "";
        quantos = 0;
        faturado = 0.0;
    }
    
    /**
     * Construtor por parâmetros
     */
    public ParClienteCompraFaturado(String cli, int q, double fat)
    {
        cliente = cli;

        quantos = q;
        faturado = fat;
    }
    
    /**
     * Construtor por cópia
     */
    public ParClienteCompraFaturado(ParClienteCompraFaturado cf)
    {
        cliente = cf.getCliente();
        quantos = cf.getQuantidades();
        faturado = cf.getFaturado();
    }
    
    /**
     * Devolve código do cliente
     */
    public String getCliente()
    {
        return cliente;
    }
    
    /**
     * Devolve número de vezes que o comprou
     */
    public int getQuantidades()
    {
        return quantos;
    }
    
    /**
     * Devolve total de dinheiro que gastou no produto
     */
    public double getFaturado()
    {
        return faturado;
    }
    
    /**
     * Define o cliente
     */
    public void setCliente(String cli)
    {
        cliente = cli;
    }
    
    /**
     * Define número de vezes comprado
     */
    public void adicionaQuantidade(int n)
    {
        quantos += n;
    }
    
    /**
     * Define total de dinheiro gasto
     */
    public void adicionaFaturado(double fat)
    {
        faturado += fat;
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public ParClienteCompraFaturado clone()
    {
        return new ParClienteCompraFaturado(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
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
    
    /**
     * Devolve representação textual do cliente, do número de vezes que comprou um produto e o dinheiro nele gasto
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente " +cliente+ " comprou " +quantos+ " vezes o produto e gastou " +faturado +"\n");
        return sb.toString();
    }
}