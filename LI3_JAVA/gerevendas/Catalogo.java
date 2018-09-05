import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.io.*;

/**
 * Classe Catalogo contém uma lista com o conjunto dos produtos/clientes que começam por uma dada letra.
 * Por exemplo, na posição 0 da lista, encontram-se os produtos/clientes que começam pela letra A.
 */
public class Catalogo implements Serializable
{
    /**
     * Variável de instância que guarda os códigos dos produtos/clientes.
     */
    List<TreeSet<String>> catalogo;

    /**
     * Construtor Vazio
     */
    public Catalogo()
    {
        catalogo = new ArrayList<>(26);
        
        for(int i = 0; i < 26; i++)
        {
            catalogo.add(i, new TreeSet<String>());
        }
    }
    
    /**
     * Construtor por parâmetro
     */
    public Catalogo(ArrayList<TreeSet<String>> cat)
    {
        setCatalogo(cat);
    }
    
    /**
     * Construtor por cópia
     */
    public Catalogo(Catalogo c)
    {
        catalogo = c.getCatalogo();
    }
    
    /**
     * Define a lista dos códigos.
     */
    public void setCatalogo(ArrayList<TreeSet<String>> cat)
    {
        this.catalogo = new ArrayList<>();
        
        for(int i = 0; i < 26; i++)
        {
            for(String s : cat.get(i))
            {
                this.catalogo.get(i).add(s);
            }
        }
    }
    
    /**
     * Devolve a lista completa dos códigos.
     */
    public List<TreeSet<String>> getCatalogo()
    {
        List<TreeSet<String>> novo = new ArrayList<>(26);
        
        for(int i = 0; i < 26; i++)
        {
            novo.add(i, new TreeSet<String>());
            
            for(String s : catalogo.get(i))
                novo.get(i).add(s);
        }
        
        return novo;
    }
    
    /**
     * Devolve o conjunto dos códigos que se encontram numa dada posição da lista.
     */
    public Set<String> getSubCatalogo(int indice)
    {
        Set<String> novo = new TreeSet<>();
        
        for(String s : catalogo.get(indice))
        {
            novo.add(s);
        }
            
        return novo;
    } 
    
    /**
     * Devolver uma cópia da instância
     */
    public Catalogo clone()
    {
        return new Catalogo(this);
    }
    
    /**
     * Adiciona um novo código ao conjunto que se encontra numa dada posição da lista.
     */
    public void adicionaCodigo(String codigo, int indice)
    {
        catalogo.get(indice).add(codigo);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object o)
    {
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Catalogo c = (Catalogo) o;
        if(c.getCatalogo().equals(catalogo) == false) return false;
        
        return true;
    }
    
    /**
     * Devolve representação textual dos códigos
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < 26; i++)
        {
            for(String s : catalogo.get(i))
            {
                sb.append(s + "\n");
            }
        }
        
        return sb.toString();
    }
}
