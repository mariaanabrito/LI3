import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.io.*;
public class Faturacao implements Serializable
{
    private Map<Integer, ProdutosFat> faturacao; /** Chave é o mês */
    
    public Faturacao()
    {
        faturacao = new TreeMap<Integer, ProdutosFat>();
    }
    
    public Faturacao(Faturacao fat)
    {
        faturacao = fat.getFaturacao();
    }
    
    public Faturacao(Map<Integer, ProdutosFat> f)
    {
        setFaturacao(f);
    }
    
    public Map<Integer, ProdutosFat> getFaturacao()
    {
        Map<Integer, ProdutosFat> novo = new TreeMap<>();
       
        faturacao.forEach((k,v) -> novo.put(k,v.clone()));
       
        return novo;
    }
    
    public ProdutosFat getFaturacaoMes(int m)
    {
        ProdutosFat p = faturacao.get(m).clone();
        
        if(p == null)
            return null;
            
        return p;
    }
    
    /**
     * Método que limpa a estrutura da faturação
     */
    public void limpaFaturacao()
    {
        faturacao.clear();
    }
    
    public void setFaturacao(Map<Integer, ProdutosFat> f)
    {
        faturacao = new TreeMap<>();
       
        f.forEach((k,v)-> faturacao.put(k, v.clone()));
    }
    
    public void adicionaVendaFaturacao(Venda v)
    {
        String produto;
        int filial, mes, quantos;
        double preco;
        
        produto = v.getProduto();
        filial = v.getFilial();
        mes = v.getMes();
        quantos = v.getQuantos();
        preco = v.getPreco();
        
        if (faturacao.containsKey(mes) == false)
        {
            /** Ainda não há nenhuma entrada com o mês, por isso, pode-se assumir que todos os restantes maps têm de ser criados de raiz */
            faturacao.put(mes, new ProdutosFat(filial, produto, preco, quantos));
        }
        else 
        {
            /** Já há uma entrada com o mês, por isso temos de adicionar o resto a esta entrada existente */
            faturacao.get(mes).adicionaProdutoFat(filial, produto, preco, quantos);
        }
    }
    
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{faturacao});
    }
    
    public Faturacao clone()
    {
        return new Faturacao(this);
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Faturacao f = (Faturacao) o;
        
        if (f.getFaturacao().equals(faturacao) == false) return false;
        
        return true;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(faturacao.toString());
        
        return sb.toString();
    }
}
