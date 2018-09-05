import java.io.*;

/**
 * Classe TuploProdutosFat contém o número de vendas, o número de produtos diferentes comprados e 
 * a faturação total
 */
public class TuploProdutosFat implements Serializable
{
    /**
     * Número de vendas e número de produtos diferentes comprados
     */
    private int nvendas, nprods;
    /**
     * Faturação total
     */
    private double fat;
    
    /**
     * Construtor Vazio
     */
    public TuploProdutosFat()
    {
        nvendas = 0;
        nprods = 0;
        fat = 0.0;
    }
    
    /**
     * Construtor por parâmetros
     */
    public TuploProdutosFat(int nv, int np, double f)
    {
        nvendas = nv;
        nprods = np;
        fat = f;
    }
    
    /**
     * Construtor por cópia
     */
    public TuploProdutosFat(TuploProdutosFat tpf)
    {
        nvendas = tpf.getVendasTPF();
        nprods = tpf.getProdutosTPF();
        fat = tpf.getFatTPF();
    }
    
    /**
     * Devolve número de vendas
     */
    public int getVendasTPF()
    {
        return nvendas;
    }
    
    /**
     * Devolve número de produtos diferentes comprados
     */
    public int getProdutosTPF()
    {
        return nprods;
    }
    
    /**
     * Devolve faturação total
     */
    public double getFatTPF()
    {
        return fat;
    }
    
    /**
     * Define número de vendas
     */
    public void setVendasTPF(int v)
    {
        nvendas = v;
    }
    
    /**
     * Define número de produtos diferentes comprados
     */
    public void setProdutosTPF(int p)
    {
        nprods = p;
    }
    
    /**
     * Define faturação total
     */
    public void setFatTPF(double f)
    {
        fat = f;
    }
    
    /**
     * Adiciona novos valores, aos já existentes, relativos ao número de vendas, 
     * número total de produtos diferentes comprados e faturação total
     */
    public void adicionaComponentesTPF(int nv, int np, double f)
    {
        nvendas += nv;
        nprods += np;
        fat += f;
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public TuploProdutosFat clone()
    {
        return new TuploProdutosFat(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if (o == this) return false;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        TuploProdutosFat tpf = (TuploProdutosFat) o;
        
        if (tpf.getVendasTPF() != nvendas) return false;
        if (tpf.getProdutosTPF() != nprods) return false;
        if (tpf.getFatTPF() != fat) return false;
        
        return true;
    }
    
    /**
     * Devolve representação textual do número de vendas, número de produtos diferentes comprados 
     * e faturação total
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de compras: " + nvendas + "\n");
        sb.append("Total de produtos diferentes comprados: " + nprods + "\n");
        sb.append("Total gasto: " + fat + "\n");
        
        return sb.toString();
    }
    

}
