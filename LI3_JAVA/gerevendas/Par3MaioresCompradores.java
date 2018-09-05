import java.util.Set;
import java.util.TreeSet;
import java.io.*;

/**
 * Classe Par3MaioresCompradores contém códigos dos três clientes que mais compraram em cada uma das filiais
 */
public class Par3MaioresCompradores implements Serializable
{
    /**
     * Variáveis de instância que representam os códigos dos três clientes que mais compraram em cada uma das filiais
     */
    private TreeSet<String> compF1, compF2, compF3;
    
    /**
     * Construtor Vazio
     */
    public Par3MaioresCompradores()
    {
        compF1 = new TreeSet<>();
        compF2 = new TreeSet<>();
        compF3 = new TreeSet<>();
    }
    
    /**
     * Construtor por parâmetros
     */
    public Par3MaioresCompradores(TreeSet<String> c1, TreeSet<String> c2, TreeSet<String> c3)
    {
        setCompradoresF1(c1);
        setCompradoresF2(c2);
        setCompradoresF3(c3);
    }
    
    /**
     * Construtor por cópia
     */
    public Par3MaioresCompradores(Par3MaioresCompradores p)
    {
        compF1 = getCompradoresF1();
        compF2 = getCompradoresF2();
        compF3 = getCompradoresF3();
    }
    
    /**
     * Define os clientes que mais compraram na filial 1
     */
    public void setCompradoresF1(TreeSet<String> c1)
    {
        compF1 = new TreeSet<>();
        for(String s : c1)
            compF1.add(s);
    }
    
    /**
     * Define os clientes que mais compraram na filial 2
     */
    public void setCompradoresF2(TreeSet<String> c2)
    {
        compF2 = new TreeSet<>();
        for(String s : c2)
            compF2.add(s);
    }
    
    /**
     * Define os clientes que mais compraram na filial 3
     */
    public void setCompradoresF3(TreeSet<String> c3)
    {
        compF3 = new TreeSet<>();
        for(String s : c3)
            compF3.add(s);
    }
    
    /**
     * Devolve os clientes que mais compraram na filial 1
     */
    public TreeSet<String> getCompradoresF1()
    {
        TreeSet<String> novo = new TreeSet<>();
        for(String s : compF1)
            novo.add(s);
        return novo;
    }
    
    /**
     * Devolve os clientes que mais compraram na filial 2
     */
    public TreeSet<String> getCompradoresF2()
    {
        TreeSet<String> novo = new TreeSet<>();
        for(String s : compF2)
            novo.add(s);
        return novo;
    }
    
    /**
     * Devolve os clientes que mais compraram na filial 3
     */
    public TreeSet<String> getCompradoresF3()
    {
        TreeSet<String> novo = new TreeSet<>();
        for(String s : compF3)
            novo.add(s);
        return novo;
    }
    
    /**
     * Adiciona um novo comprador à lista dos compradores que mais compraram, numa dada filial
     */
    public void adicionaComprador(int fi, String s)
    {
        if(fi == 1)
            compF1.add(s);
        if(fi == 2)
            compF2.add(s);
        if(fi == 3)
            compF3.add(s);
    }
    
    /**
     * Devolver uma cópia da instância
     */
    public Par3MaioresCompradores clone()
    {
        return new Par3MaioresCompradores(this);
    }
    
    /**
     * Verifica a igualdade com outro objecto
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Par3MaioresCompradores p = (Par3MaioresCompradores) obj;
        if(compF1.equals(p.getCompradoresF1()) == false) return false;
        if(compF2.equals(p.getCompradoresF2()) == false) return false;
        if(compF3.equals(p.getCompradoresF3()) == false) return false;
        return true;
    }
    
    /**
     * Devolve representação textual dos compradores que mais compraram em cada uma das filiais
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        sb.append("Para a filial " + i + "\n");
        for(String s : compF1)
            sb.append(s + "\n");
        i++;
        sb.append("Para a filial " + i + "\n");
        for(String s : compF2)
            sb.append(s + "\n");
        i++;
        sb.append("Para a filial " + i + "\n");
        for(String s : compF3)
            sb.append(s + "\n");
        return sb.toString();
    }
}
