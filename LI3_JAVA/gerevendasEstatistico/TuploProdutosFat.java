import java.io.*;
public class TuploProdutosFat implements Serializable
{
    private int nvendas, nprods;
    private double fat;
    
    public TuploProdutosFat()
    {
        nvendas = 0;
        nprods = 0;
        fat = 0.0;
    }
    
    public TuploProdutosFat(int nv, int np, double f)
    {
        nvendas = nv;
        nprods = np;
        fat = f;
    }
    
    public TuploProdutosFat(TuploProdutosFat tpf)
    {
        nvendas = tpf.getVendasTPF();
        nprods = tpf.getProdutosTPF();
        fat = tpf.getFatTPF();
    }
    
    public int getVendasTPF()
    {
        return nvendas;
    }
    
    public int getProdutosTPF()
    {
        return nprods;
    }
    
    public double getFatTPF()
    {
        return fat;
    }
    
    public void setVendasTPF(int v)
    {
        nvendas = v;
    }
    
    public void setProdutosTPF(int p)
    {
        nprods = p;
    }
    
    public void setFatTPF(double f)
    {
        fat = f;
    }
    
    public void adicionaComponentesTPF(int nv, int np, double f)
    {
        nvendas += nv;
        nprods += np;
        fat += f;
    }
    
    
    
    public TuploProdutosFat clone()
    {
        return new TuploProdutosFat(this);
    }
    
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
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de compras: " + nvendas + "\n");
        sb.append("Total de produtos diferentes comprados: " + nprods + "\n");
        sb.append("Total gasto: " + fat + "\n");
        
        return sb.toString();
    }
    

}
