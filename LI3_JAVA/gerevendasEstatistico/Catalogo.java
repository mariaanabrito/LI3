import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.io.*;

public class Catalogo implements Serializable
{
    List<TreeSet<String>> catalogo;

    public Catalogo()
    {
        catalogo = new ArrayList<>(26);
        
        for(int i = 0; i < 26; i++)
        {
            catalogo.add(i, new TreeSet<String>());
        }
    }
    
    public Catalogo(ArrayList<TreeSet<String>> cat)
    {
        setCatalogo(cat);
    }
    
    public Catalogo(Catalogo c)
    {
        catalogo = c.getCatalogo();
    }
    
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
    
    public Set<String> getSubCatalogo(int indice)
    {
        Set<String> novo = new TreeSet<>();
        
        for(String s : catalogo.get(indice))
        {
            novo.add(s);
        }
            
        return novo;
    } 
    
    public Catalogo clone()
    {
        return new Catalogo(this);
    }
    
    public void adicionaCodigo(String codigo, int indice)
    {
        catalogo.get(indice).add(codigo);
    }
    
    public boolean equals(Object o)
    {
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Catalogo c = (Catalogo) o;
        if(c.getCatalogo().equals(catalogo) == false) return false;
        
        return true;
    }
    
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
