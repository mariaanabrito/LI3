import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.io.*;

/**
 * Classe ProdutosFat contém uma estrutura Map cujas chaves são as filiais e cujos valores são Produtos, bem como os métodos de gestão desta estrutura, como, por
 * exemplo, método de adicionar uma nova entrada ou atualizar uma entrada já existente.
 */
public class ProdutosFat implements Serializable
{
    /**
     * Variável de instância da classe ProdutosFat, que é o mapeamento entre as filiais e os Produtos.
     */
    private Map<Integer, Produtos> produtosfat;
    
    /**
     * Construtor Vazio
     */
    public ProdutosFat()
    {
        produtosfat = new HashMap<>(44);
    }
    
    /**
     * Construtor por Cópia
     */
    public ProdutosFat(ProdutosFat pf)
    {
        produtosfat = pf.getProdutosFat();
    }
    
    /**
     * Construtor por parâmetro
     */
    public ProdutosFat(Map<Integer, Produtos> ps)
    {
        setProdutosFat(ps);
    }
    
    /** 
     * Método que vai ser usado quando o map anterior não tiver uma entrada existente com o mês, logo para esse mês vamos ter de criar map novo com a filial recebida 
     * e restantes atributos.
     */
    public ProdutosFat(int filial, String produto, double preco, int quantos)
    {
        produtosfat = new HashMap<>(4);
        
        produtosfat.put(filial, new Produtos(produto, preco, quantos));
    }
    
    /**
     * Devolve a estrutura Map da classe ProdutosFat.
     */
    public Map<Integer, Produtos> getProdutosFat()
    {
        Map<Integer, Produtos> novo = new HashMap<>(4);
       
        produtosfat.forEach((k,v) -> novo.put(k, v.clone()));
        
        return novo;
    }
    
    /**
     * Devolve o valor (Produtos) relativo a uma filial.
     */
    public Produtos getProdutosFilial(int f)
    {
        Produtos p = produtosfat.get(f).clone();
        
        if(p == null)
            return null;
            
        return p;
    }
    
    /**
     * Definir a associação entre filiais e Produtos.
     */
    public void setProdutosFat(Map<Integer, Produtos> ps)
    {
        produtosfat = new HashMap<>();
       
        ps.forEach((k,v) -> produtosfat.put(k, v.clone()));
    }
    
    /**
     * Método que adiciona uma nova entrada na estrutura ou atualiza uma já existente.
     * Existem dois casos possíveis: não existe entrada com a filial, logo temos de criar um map de produtos de raiz para esta nova entrada ou 
     * existe já uma entrada com a filial, por isso temos de adicionar o produto ao map desta entrada
     */
    public void adicionaProdutoFat(int filial, String produto, double preco, int quantos)
    {
        if (produtosfat.containsKey(filial) == false)
        {
            produtosfat.put(filial, new Produtos(produto, preco, quantos));
        }
        else
        {
            produtosfat.get(filial).adicionaProduto(produto, preco, quantos);
        }
    }
    
    /**
     * Redefinição do hashCode.
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{produtosfat});
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public ProdutosFat clone()
    {
        return new ProdutosFat(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        ProdutosFat pf = (ProdutosFat) o;
        
        if (pf.getProdutosFat().equals(produtosfat) == false) return false;
        
        return true;
    }
    
    /**
     * Devolve representação textual da estrutura da classe ProdutosFat.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(produtosfat.toString());
        
        return sb.toString();
    }
}
