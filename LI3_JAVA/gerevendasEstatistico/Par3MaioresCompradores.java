import java.util.Set;
import java.util.HashSet;
import java.io.*;
import java.util.Arrays;
/**
 * Write a description of class Par3MaioresCompradores here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Par3MaioresCompradores implements Serializable
{
    private HashSet<String> compF1, compF2, compF3;
    
    public Par3MaioresCompradores()
    {
        compF1 = new HashSet<>();
        compF2 = new HashSet<>();
        compF3 = new HashSet<>();
    }
    
    public Par3MaioresCompradores(HashSet<String> c1, HashSet<String> c2, HashSet<String> c3)
    {
        setCompradoresF1(c1);
        setCompradoresF2(c2);
        setCompradoresF3(c3);
    }
    
    public Par3MaioresCompradores(Par3MaioresCompradores p)
    {
        compF1 = getCompradoresF1();
        compF2 = getCompradoresF2();
        compF3 = getCompradoresF3();
    }
    
    public void setCompradoresF1(HashSet<String> c1)
    {
        compF1 = new HashSet<>();
        for(String s : c1)
            compF1.add(s);
    }
    
    public void setCompradoresF2(HashSet<String> c2)
    {
        compF2 = new HashSet<>();
        for(String s : c2)
            compF2.add(s);
    }
    
    public void setCompradoresF3(HashSet<String> c3)
    {
        compF3 = new HashSet<>();
        for(String s : c3)
            compF3.add(s);
    }
    
    public HashSet<String> getCompradoresF1()
    {
        HashSet<String> novo = new HashSet<>();
        for(String s : compF1)
            novo.add(s);
        return novo;
    }
    
    public HashSet<String> getCompradoresF2()
    {
        HashSet<String> novo = new HashSet<>();
        for(String s : compF2)
            novo.add(s);
        return novo;
    }
    
    public HashSet<String> getCompradoresF3()
    {
        HashSet<String> novo = new HashSet<>();
        for(String s : compF3)
            novo.add(s);
        return novo;
    }
    
    public void adicionaComprador(int fi, String s)
    {
        if(fi == 1)
            compF1.add(s);
        if(fi == 2)
            compF2.add(s);
        if(fi == 3)
            compF3.add(s);
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{compF1, compF2, compF3});
    }
    
    public Par3MaioresCompradores clone()
    {
        return new Par3MaioresCompradores(this);
    }
    
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
