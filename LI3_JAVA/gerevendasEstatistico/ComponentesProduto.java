import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class ComponentesProduto implements Serializable
{
    private double preco;
    private int unidades, quantos;
    
    public ComponentesProduto()
    {
        preco = 0.0;
        unidades = 0;
        quantos = 0;
    }
    
    public ComponentesProduto(ComponentesProduto p)
    {
        preco = p.getPrecoProd();
        unidades =  p.getUnidadesProd();
        quantos = p.getQuantosProd();
    }
    
    public ComponentesProduto(double pc, int unid)
    {
        BigDecimal bd = new BigDecimal(pc*unid).setScale(2, RoundingMode.HALF_EVEN);
        double aux = bd.doubleValue();
        preco = aux;
        unidades = unid;
        quantos = 1;
    }

    public int getQuantosProd()
    {
        return quantos;
    }
    
    public double getPrecoProd()
    {
        return preco;
    }
    
    public int getUnidadesProd()
    {
        return unidades;
    }
    
    public void setPrecoProd(double pc)
    {
        preco = pc;
    }
    
    public void setUnidadesProd(int unid)
    {
        unidades = unid;
    }
    
    public void setQuantosProd(int q)
    {
        quantos = q;
    }
    
    public void adicionaComponentesProduto(double pc, int unid)
    {
        BigDecimal bd = new BigDecimal(pc*unid).setScale(2, RoundingMode.HALF_EVEN);
        double aux = bd.doubleValue();
        preco += aux;
        unidades += unid;
        quantos++;
    }
   
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{preco, unidades, quantos});
    }
    
    public ComponentesProduto clone()
    {
        return new ComponentesProduto(this);
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        ComponentesProduto p = (ComponentesProduto) o;
        
        if (p.getPrecoProd() != preco) return false;
        if (p.getUnidadesProd() != unidades) return false;
        if (p.getQuantosProd() != quantos) return false;
        
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Preço: " + preco + "\n");
        sb.append("Unidades: " + unidades + "\n");
        
        
        return sb.toString();
    }
}