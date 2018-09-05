import java.io.*;
import java.util.TreeSet;

/**
 * Classe que representa uma venda contendo um produto, um cliente, um preço, uma quantidade,
 * um modo de venda, e uma filial.
 */

public class Venda implements Serializable
{
    private int quantos, mes, filial;
    private String produto, cliente, modo;
    private double preco;
    
    /**
     * Construtor por parâmetro.
     */
    public Venda(String p, double pc, int q, String md, String c, int m, int f)
    {
        quantos = q;
        mes = m;
        filial = f;
        produto = p;
        cliente = c;
        preco = pc;
        modo = md;
    }
    
    /**
     * Construtor por cópia
     */
    public Venda(Venda v)
    {
        quantos = v.getQuantos();
        mes = v.getMes();
        filial = v.getFilial();
        produto = v.getProduto();
        cliente = v.getCliente();
        preco = v.getPreco();
        modo = v.getModo();
    }
    
    /**
     * Método que devolve a quantidade da venda.
     */
    public int getQuantos()
    {
        return quantos;
    }
    
    /**
     * Método que devolve o mês da venda.
     */
    public int getMes()
    {
        return mes;
    }
    
    /**
     * Método que devolve a filial da venda.
     */
    public int getFilial()
    {
        return filial;
    }
    
    /**
     * Método que devolve o produto da venda.
     */
    public String getProduto()
    {
        return produto;
    }
    
    /**
     * Método que devolve o cliente da venda.
     */
    public String getCliente()
    {
        return cliente;
    }
    
    /**
     * Método que devolve o preço da venda.
     */
    public double getPreco()
    {
        return preco;
    }
    
    /**
     * Método que devolve o modo da venda.
     */
    public String getModo()
    {
        return modo;
    }
    
    /**
     * Método que testa se uma venda é válida.
     */
    public boolean vendaValida(TreeSet<String> catclientes, TreeSet<String> catprods)
    {
        boolean r = true;
        if(catprods.contains(produto) == false || catclientes.contains(cliente) == false)
        {
            r = false;
        }
        if(modo.equals("N") == false && modo.equals("P") == false)
        {
            r = false;
        }
        if(preco < 0 || preco > 1000)
        {
            r = false;
        }
        if(quantos < 1 || quantos > 200)
        {
            r = false;
        }
        if(mes < 1 || mes > 12)
        {
            r = false;
        }
        if(filial < 1 || filial > 3)
        {
            r = false;
        }
        
        return r;
    }
    
    /**
     * Devolve uma instância da classe.
     */
    public Venda clone()
    {
        return new Venda(this);
    }
    
    /**
     * Método que testa a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;
        Venda vd = (Venda) obj;
        if(vd.getQuantos() != quantos) return false;
        if(vd.getMes() != mes) return false;
        if(vd.getFilial() != filial) return false;
        if(vd.getProduto().equals(produto) == false) return false;
        if(vd.getCliente().equals(cliente) == false) return false;
        if(vd.getPreco() != preco) return false;
        if(vd.getModo().equals(modo) == false) return false;
        return true;
    }
    /**
     * Representação textual de Venda.
     * Observação : produto-preço-quantidade-modo-cliente-mes-filial
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder(produto);
        sb.append("-");
        sb.append(preco);
        sb.append("-");
        sb.append(quantos);
        sb.append("-");
        sb.append(modo);
        sb.append("-");
        sb.append(cliente);
        sb.append("-");
        sb.append(mes);
        sb.append("-");
        sb.append(filial);
        return sb.toString();
        
        
    }
}
