import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Arrays;
import java.io.*;
public class Filial implements Serializable
{
    private TreeMap<Integer, ClienteFilial> filial; /** Chave é o mês  */
    
    public Filial()
    {
        filial = new TreeMap<>();
    }
    
    public Filial(TreeMap<Integer, ClienteFilial> fili)
    {
        setFilial(fili);
    }
    
    public Filial(Filial fi)
    {
        filial = fi.getFilial();
    }
    
    public void setFilial(TreeMap<Integer, ClienteFilial> fili)
    {
        filial = new TreeMap<>();
       
        fili.forEach((k,v) -> filial.put(k, v.clone()));
    }
    
    public TreeMap<Integer, ClienteFilial> getFilial()
    {
        TreeMap<Integer, ClienteFilial> novo = new TreeMap<>();
       
        filial.forEach((k,v) -> novo.put(k, v.clone()));
            
        return novo;
    }
    
    public ClienteFilial getFilialMes(int m)
    {
        return filial.get(m);
    }
    
    /**
     * Método que limpa a estrutura da filial
     */
    public void limpaFilial()
    {
        filial.clear();
    }
    
    public void adicionaFilial(int mes, String cli, String prod, double pre, int quantos)
    {
        if(filial.containsKey(mes) == false)
        {
            ClienteFilial cf = new ClienteFilial();
            cf.adicionaClienteFilial(cli, prod, pre, quantos);
            filial.put(mes, cf);
        }
        else
        {
            filial.get(mes).adicionaClienteFilial(cli,prod,pre,quantos);
        }
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{filial});
    }
    
    public Filial clone()
    {
        return new Filial(this);
    }
    
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Filial f = (Filial) obj;
        if(filial.equals(f.getFilial()) == false) return false;
        return true;
    }
    
    public String toString()
    { 
        return filial.toString();
    }
}
