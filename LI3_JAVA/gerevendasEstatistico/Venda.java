import java.io.*;
import java.util.HashSet;
import java.util.Arrays;

public class Venda implements Serializable
{
    private int quantos, mes, filial;
    private String produto, cliente, modo;
    private double preco;
        
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
    
    public int getQuantos()
    {
        return quantos;
    }
    
    public int getMes()
    {
        return mes;
    }
    
    public int getFilial()
    {
        return filial;
    }
    
    public String getProduto()
    {
        return produto;
    }
    
    public String getCliente()
    {
        return cliente;
    }
    
    public double getPreco()
    {
        return preco;
    }
    
    public String getModo()
    {
        return modo;
    }
    
    public boolean vendaValida(HashSet<String> catclientes, HashSet<String> catprods)
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
        
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{quantos, mes, filial, produto, cliente, modo, preco});
    }
    
    public Venda clone()
    {
        return new Venda(this);
    }
    
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
    //produto-preço-quantidade-modo-cliente-mes-filial
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
