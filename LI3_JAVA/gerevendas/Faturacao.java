import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.io.*;

/**
* A classe Faturação contém uma estrutura Map cujas chaves são os meses do ano e cujos valores pertencem à classe ProdutosFat, bem como os métodos de gestão da estrutura, 
* como, por exemplo, método para adicionar uma nova entra ou para atualizar uma entrada já existente.
*/
    
public class Faturacao implements Serializable
{
    /**
     * Variável de instância da classe Faturação 
     */
    private Map<Integer, ProdutosFat> faturacao;
    
    /**
     * Construtor Vazio
     */
    public Faturacao()
    {
        faturacao = new HashMap<Integer, ProdutosFat>(14);
    }
    
    /**
     * Construtor por cópia
     */
    public Faturacao(Faturacao fat)
    {
        faturacao = fat.getFaturacao();
    }
    
    /**
     * Construtor por parâmetro
     */
    public Faturacao(Map<Integer, ProdutosFat> f)
    {
        setFaturacao(f);
    }
    
    /**
     * Obter estrutura Map relativa à Faturação.
     */
    public Map<Integer, ProdutosFat> getFaturacao()
    {
        Map<Integer, ProdutosFat> novo = new HashMap<>(14);
       
        faturacao.forEach((k,v) -> novo.put(k,v.clone()));
       
        return novo;
    }
    
    /**
     * Método que limpa a estrutura da faturação
     */
    public void limpaFaturacao()
    {
        faturacao.clear();
    }
    
    /**
     * Obter valores (ProdutosFat) relativo a um mês.
     */
    public ProdutosFat getFaturacaoMes(int m)
    {
        ProdutosFat p = faturacao.get(m).clone();
        
        if(p == null)
            return null;
            
        return p;
    }
    
    /**
     * Definir a Faturação.
     */
    public void setFaturacao(Map<Integer, ProdutosFat> f)
    {
        faturacao = new HashMap<>(14);
       
        f.forEach((k,v)-> faturacao.put(k, v.clone()));
    }
    
    /**
     * Adicionar uma nova venda à estrutura da Faturação.
     * Existem dois casos possíveis: ainda não há nenhuma entrada com o mês, por isso, pode-se assumir que todos os restantes maps têm de ser criados de raiz 
     * ou já há uma entrada com o mês, por isso temos de adicionar o resto a esta entrada existente.
     */
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
            faturacao.put(mes, new ProdutosFat(filial, produto, preco, quantos));
        }
        else 
        {  
            faturacao.get(mes).adicionaProdutoFat(filial, produto, preco, quantos);
        }
    }
    
    /**
     * Redefinição do hashCode.
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{faturacao});
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public Faturacao clone()
    {
        return new Faturacao(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Faturacao f = (Faturacao) o;
        
        if (f.getFaturacao().equals(faturacao) == false) return false;
        
        return true;
    }
    
    /**
     * Devolve representação textual da Faturação
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(faturacao.toString());
        
        return sb.toString();
    }
}
