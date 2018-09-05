import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.math.BigDecimal;

/**
 * Classe ComponentesProduto contém a faturação total de um produto, o total de unidades compradas e o número de vezes que foi comprado.
 */
public class ComponentesProduto implements Serializable
{
    /**
     * Faturação do produto
     */
    private double fat;
    /**
     * Total de unidades compradas e o número de vezes que foi comprado.
     */
    private int unidades, quantos;
    
    /**
     * Construtor Vazio
     */
    public ComponentesProduto()
    {
        fat = 0.0;
        unidades = 0;
        quantos = 0;
    }
    
    /**
     * Construtor por cópia
     */
    public ComponentesProduto(ComponentesProduto p)
    {
        fat = p.getFatProd();
        unidades =  p.getUnidadesProd();
        quantos = p.getQuantosProd();
    }
    
    /**
     * Construtor por parâmetros
     */
    public ComponentesProduto(double pc, int unid)
    {
        BigDecimal bd = new BigDecimal(pc*unid).setScale(2, RoundingMode.HALF_EVEN);
        double aux = bd.doubleValue();
        fat = aux;
        unidades = unid;
        quantos = 1;
    }

    /**
     * Devolve número de vezes que foi comprado.
     */
    public int getQuantosProd()
    {
        return quantos;
    }
    
    /**
     * Devolve faturação total do produto.
     */
    public double getFatProd()
    {
        return fat;
    }
    
    /**
     * Devolve número total de unidades compradas.
     */
    public int getUnidadesProd()
    {
        return unidades;
    }
    
    /**
     * Define faturação total.
     */
    public void setFatProd(double pc)
    {
        fat = pc;
    }
    
    /**
     * Define número de unidades compradas.
     */
    public void setUnidadesProd(int unid)
    {
        unidades = unid;
    }
    
    /**
     * Define número de vezes que foi comprado.
     */
    public void setQuantosProd(int q)
    {
        quantos = q;
    }
    
    /**
     * Método que atualiza os componentes de um dado produto. Soma os novos valores da faturação e unidades aos já existentes e incrementa o número 
     * de vezes que foi comprado.
     */
    public void adicionaComponentesProduto(double pc, int unid)
    {
        BigDecimal bd = new BigDecimal(pc*unid).setScale(2, RoundingMode.HALF_EVEN);
        double aux = bd.doubleValue();
        fat += aux;
        unidades += unid;
        quantos++;
    }
   
    /**
     * Redefinição do hashCode.
     */ 
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{fat, unidades, quantos});
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public ComponentesProduto clone()
    {
        return new ComponentesProduto(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        ComponentesProduto p = (ComponentesProduto) o;
        
        if (p.getFatProd() != fat) return false;
        if (p.getUnidadesProd() != unidades) return false;
        if (p.getQuantosProd() != quantos) return false;
        
        return true;
    }
    
    /**
     * Devolve representação textual dos componentes do produto
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Faturação: " + fat + "\n");
        sb.append("Unidades: " + unidades + "\n");
        
        
        return sb.toString();
    }
}