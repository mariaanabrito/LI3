import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.io.*;

/**
 * Classe que implementa uma filial.
 */
public class Filial implements Serializable
{
    private HashMap<Integer, ClienteFilial> filial; /** Chave é o mês  */
    /**
     * Construtor vazio.
     */
    public Filial()
    {
        filial = new HashMap<>(14);
    }
    
    /**
     * Construtor por parâmetros
     */
    public Filial(HashMap<Integer, ClienteFilial> fili)
    {
        setFilial(fili);
    }
    /**
     * Construtor de cópia.
     */
    public Filial(Filial fi)
    {
        filial = fi.getFilial();
    }
    
    /**
     * Definir uma filial através do parâmetro dado pelo método.
     */
    public void setFilial(HashMap<Integer, ClienteFilial> fili)
    {
        filial = new HashMap<>(14);
       
        fili.forEach((k,v) -> filial.put(k, v.clone()));
    }
    
    /**
     * Método que retorna a filial.
     */
    public HashMap<Integer, ClienteFilial> getFilial()
    {
        HashMap<Integer, ClienteFilial> novo = new HashMap<>(14);
       
        filial.forEach((k,v) -> novo.put(k, v.clone()));
            
        return novo;
    }
    
    /**
     * Método que limpa a estrutura da filial
     */
    public void limpaFilial()
    {
        filial.clear();
    }
    
    /**
     * Obter os dados de uma filial(ClienteFilial) num determinado mês.
     */
    public ClienteFilial getFilialMes(int m)
    {
        return filial.get(m);
    }
    
    /**
     * Adiciona, num determinado mês, um produto comprado por um determinado cliente.
     */
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
    
    /**
     * Método de hash para o HashMap da classe
     */
    public int hashCode()
    {
        return Arrays.hashCode(new Object[]{filial});
    }
    
    /**
     * Devolve uma cópia da instância.
     */
    public Filial clone()
    {
        return new Filial(this);
    }
    
    /**
     * Método que verifica a igualdade entre dois objetos.
     */
    public boolean equals(Object obj)
    {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Filial f = (Filial) obj;
        if(filial.equals(f.getFilial()) == false) return false;
        return true;
    }
    
    /**
     * Representação textual de uma filial.
     */
    public String toString()
    { 
        return filial.toString();
    }
}
