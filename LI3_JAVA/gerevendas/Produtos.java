import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.io.*;

/**
 * Classe Produtos contém uma estrutura Map cujas chaves são os códigos dos produtos e cujos valores são os ComponentesProdutos, bem 
 * como métodos que permitem adicionar um novo produto e seus atributos ou atualizar um já existente.
 */
public class Produtos implements Serializable
{
    /**
     * Variável de instância que associa o código dos produtos aos seus componentes.
     */
    private Map<String, ComponentesProduto> produtos;

    /**
     * Construtor Vazio.
     */
    public Produtos()
    {
        produtos = new HashMap<>(171009);
    }
    
    /**
     * Construtor por Cópia.
     */
    public Produtos(Produtos ps)
    {
        produtos = ps.getProdutos();
    }
    
    /**
     * Construtor por Parâmetro.
     */
    public Produtos(Map<String, ComponentesProduto> ps)
    {
        setProdutos(ps);
    }
    
    /** Método que adiciona um novo produto quando não existia nenhuma entrada com a filial no map anterior, 
     * por isso para essa nova entrada temos de criar um novo map de produtos 
     */
    public Produtos(String produto, double preco, int unid)
    {
        produtos = new HashMap<>(171009);

        produtos.put(produto, new ComponentesProduto(preco, unid));
    }
    
    /**
     * Devolve mapeamento dos produtos.
     */
    public Map<String, ComponentesProduto> getProdutos()
    {
        Map<String, ComponentesProduto> novo = new HashMap<>(171009);
        
        produtos.forEach((k,v) -> novo.put(k, v.clone()));
        
        return novo;
    }
   
    /**
     * Devolve os ComponentesProduto relativo a um código de produto.
     */
    public ComponentesProduto getComponentesCodigo(String cod)
    {
        for(Map.Entry<String, ComponentesProduto> e: produtos.entrySet())
        {
            if (e.getKey().equals(cod) == true)
            {
                return e.getValue().clone();
            }
        }
        
        return null;
    }
    
    /**
     * Define mapeamentro entre produtos e seus atributos.
     */
    public void setProdutos(Map<String, ComponentesProduto> ps)
    {
        produtos = new HashMap<>();
       
       ps.forEach((k,v) -> produtos.put(k, v.clone()));
    }
    
    /**
     * Método que adiciona ou atualiza um produto.
     * Existem dois casos possíveis: o produto não existe, por isso temos de 
     * adicioná-lo ou existe e temos de atualizar os valores.
     */
    public void adicionaProduto(String produto, double preco, int quantos)
    {
        if (produtos.containsKey(produto) == false)
        {
            produtos.put(produto, new ComponentesProduto(preco, quantos));
        }
        else
        {
            produtos.get(produto).adicionaComponentesProduto(preco, quantos);
        }
    }
    
    /**
     * Redefinição do hashCode.
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{produtos});
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public Produtos clone()
    {
        return new Produtos(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Produtos ps = (Produtos) o;
        
        if (ps.getProdutos().equals(produtos) == false) return false;
        
       return true;
    }
    
    /**
     * Devolve representação textual dos produtos.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(produtos.toString());
        
        return sb.toString();
    }
}
