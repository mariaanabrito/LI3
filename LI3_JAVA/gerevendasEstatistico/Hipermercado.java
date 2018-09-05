import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.Comparator;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class Hipermercado implements Serializable
{
    private Catalogo catClientes;
    private Catalogo catProdutos;
    private Vector<Filial> f;
    private Faturacao faturacao;
    
    public Hipermercado()
    {
        int i;
        
        catClientes = new Catalogo();
        catProdutos = new Catalogo();
        f = new Vector<>();
        
        for(i = 0; i < 3; i++)
        {
            f.add(new Filial());
        }
        
        faturacao = new Faturacao();
    }
    
    public Hipermercado(Hipermercado hiper)
    {
        catClientes = hiper.getCatClientes();
        catProdutos = hiper.getCatProdutos();
        f = hiper.getFiliaisHiper();
        faturacao = hiper.getFaturacaoHiper();
    }
    
    public Catalogo getCatClientes()
    {
        return catClientes.clone();
    }
    
    public Catalogo getCatProdutos()
    {
        return catProdutos.clone();
    }
    
    public Vector<Filial> getFiliaisHiper()
    {
        int i;
        Vector<Filial> novo = new Vector<>();
        
        for(i = 0; i < 3; i++)
        {
            novo.add(i, f.get(i).clone());
        }
        
        return novo;
    }
    
    public Filial getFilialHiper(int i)
    {
        return f.get(i-1);
    }
    
    public Faturacao getFaturacaoHiper()
    {
        return faturacao.clone();
    }
    
    /**
     * Método que limpa esvazia as estruturas.
     */
    public void limpaEstruturas()
    {
        int i;
        
        faturacao.limpaFaturacao();
        for(i = 0; i < 3; i++)
            f.get(i).limpaFilial();
    }
    
    public void adicionaCliente(String cliente)
    {
        catClientes.adicionaCodigo(cliente, ascii(cliente.charAt(0)));
    }
    
    public void adicionaProduto(String produto)
    {
       catProdutos.adicionaCodigo(produto, ascii(produto.charAt(0)));
    }
    
    private int ascii(char c)
    {
        int acii = (int) c;
        
        return (acii - 65);
    }
    
    public void updateFilial(Venda v)
    {
        if(v.getFilial() == 1)
            getFilialHiper(1).adicionaFilial(v.getMes(), v.getCliente(), v.getProduto(), v.getPreco(), v.getQuantos());
        else if(v.getFilial() == 2)
            getFilialHiper(2).adicionaFilial(v.getMes(), v.getCliente(), v.getProduto(), v.getPreco(), v.getQuantos());
        else if(v.getFilial() == 3)
            getFilialHiper(3).adicionaFilial(v.getMes(), v.getCliente(), v.getProduto(), v.getPreco(), v.getQuantos());
    }
    
    public void updateFaturacao(Venda v)
    {
        faturacao.adicionaVendaFaturacao(v);
    }
   
   public void gravaObj(String fich) throws IOException
   { 
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich)); 
        oos.writeObject(this); 
        oos.flush();
        oos.close();
   } 
   
   public static Hipermercado leObjeto(String ficheiro) throws IOException, ClassNotFoundException
   {
       ObjectInputStream ins = new ObjectInputStream(new FileInputStream(ficheiro));
       
       Hipermercado hiper = (Hipermercado) ins.readObject();
       
       ins.close();
       return hiper;
   }
   
   public static Venda parseLinhaVenda(String linha) throws NumberFormatException, NullPointerException
   {
       String[] componentes;
       String modo, cliente, produto;
       int i, quantos, mes, filial;
       double preco;
       Venda v = null;
       
       componentes = linha.split(" ");
       
       produto = componentes[0].trim();
       modo = componentes[3].trim();
       cliente = componentes[4].trim();
       try
       {
           preco = Double.parseDouble(componentes[1]);
           quantos = Integer.parseInt(componentes[2]);
           mes = Integer.parseInt(componentes[5]);
           filial = Integer.parseInt(componentes[6]);
       }
       catch(NumberFormatException | NullPointerException e)
       {
           return null;
       }
      
       v = new Venda(produto, preco, quantos, modo, cliente, mes, filial);
       return v;
   }
   
   public int comprasNumMes(int mes)
   {
       int total = 0;
       ProdutosFat pf = faturacao.getFaturacaoMes(mes).clone();
       
       if(pf != null)
       {
           for(Map.Entry<Integer, Produtos> e : pf.getProdutosFat().entrySet())
           {
               if(e != null)
               {
                   for(Map.Entry<String, ComponentesProduto> i : e.getValue().getProdutos().entrySet())
                   {
                       total += i.getValue().getQuantosProd();
                   }
               }
           }
       }
       
       return total;
   }
   
   public double faturacaoNumMes(int mes, int fili)
   {
       double total = 0.0;
       Filial filial = getFilialHiper(fili);
       for(Map.Entry<String,Cliente> e : filial.getFilialMes(mes).getClientesFilial().entrySet())
       {
           total += e.getValue().getFaturacao();
       }
       BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
       double aux = bd.doubleValue();
       return aux;
   }
   
   public int clientesDistintosNumMes(int mes)
   {
       Set<String> cli = new HashSet<>();
       
       for(Map.Entry<String,Cliente> e : getFilialHiper(1).clone().getFilialMes(mes).getClientesFilial().entrySet())
            cli.add(e.getKey());
            
       for(Map.Entry<String,Cliente> e : getFilialHiper(2).clone().getFilialMes(mes).getClientesFilial().entrySet())
            cli.add(e.getKey());
            
       for(Map.Entry<String,Cliente> e : getFilialHiper(3).clone().getFilialMes(mes).getClientesFilial().entrySet())
            cli.add(e.getKey());
            
       return cli.size();
   }
   
   public ParVendasClientes vendasNumMes(int mes) throws MesInvalidoException
   {
       if(mes < 1 || mes > 12)
            throw new MesInvalidoException("O mês que inseriu não é válido!");
            
       ParVendasClientes par = new ParVendasClientes();
       HashSet<String> cli = new HashSet<>();
       
       vendasFilial(getFilialHiper(1).clone(), mes, par, cli);
       vendasFilial(getFilialHiper(2).clone(), mes, par, cli);
       vendasFilial(getFilialHiper(3).clone(), mes, par, cli);
       
       par.adicionaClientes(cli.size());
       
       return par;
   }
   
   private void vendasFilial(Filial fi, int mes, ParVendasClientes par, HashSet<String> cli)
   {
       for(Map.Entry<String,Cliente> e : fi.getFilialMes(mes).getClientesFilial().entrySet())
       {
           par.adicionaVendas(e.getValue().getNumVendas());
           cli.add(e.getKey());
       }
   }
   
   public Par3MaioresCompradores top3ClientesPorFilial()
   {
       Par3MaioresCompradores par = new Par3MaioresCompradores();
       top3(par, getFilialHiper(1).clone(), 1);
       top3(par, getFilialHiper(2).clone(), 2);
       top3(par, getFilialHiper(3).clone(), 3);
       return par;
   }
   
   private void top3(Par3MaioresCompradores par, Filial fi, int n)
   {
       HashMap<String, Double> cliFat = new HashMap<>();
       for(int i = 1; i <= 12; i++)
       {
           Iterator <Map.Entry<String, Cliente>> it = fi.getFilialMes(i).getClientesFilial().entrySet().iterator();
           while(it.hasNext())
           {
               Map.Entry<String,Cliente> e = it.next();
               if(cliFat.containsKey(e.getKey()) == false)
                    cliFat.put(e.getKey(), e.getValue().getFaturacao());
               else
               {
                    String s = e.getKey();
                    double f = cliFat.get(s);
                    double g = e.getValue().getFaturacao();
                    cliFat.remove(s);
                    cliFat.put(s, f+g);
                }
           }
       }
       
       for(int k = 0; k < 3; k++)
       {
           double maior = 0.0;
           String mai = "";
           for(Map.Entry<String, Double> t: cliFat.entrySet())
           {
               if(t.getValue() > maior) 
               {
                   maior = t.getValue();
                   mai = t.getKey();
               }
           }
           par.adicionaComprador(n, mai);
           cliFat.remove(mai);
       }
   }
    
   public TuploProdutosFat infoDeCliente(String codigo, int mes) throws ClienteInexistenteException
   {
       TuploProdutosFat tpf = new TuploProdutosFat();
       Set<String> setprods = new HashSet<>();
       Cliente c;
       int i, j, nvendas = 0, car;
       double fat = 0.0;
       
       car = ascii(codigo.charAt(0));
       if(catClientes.getSubCatalogo(car).contains(codigo) == false)
       {
            throw new ClienteInexistenteException("O cliente que inseriu não existe!");
       }
       
       for(j = 0; j < 3; j++)
       {
           c = f.get(j).getFilialMes(mes).getClientePorCodigo(codigo);
           
           if(c != null)
           {
               for(i = 0; i < c.getPrecoFil().size(); i++)
               {
                   nvendas++;
                   setprods.add(c.getProdutoIndice(i));
                   fat += c.getPrecoIndice(i)*c.getQuantidadesIndice(i);
                }
            }
       }
       
       tpf.adicionaComponentesTPF(nvendas, setprods.size(), fat);
       
       return tpf;
   }
   
   public TuploProdutosFat infoDeProduto(String produto, int mes) throws ProdutoInexistenteException
   {
       TuploProdutosFat tpf = new TuploProdutosFat();
       Set<String> setclientes = new HashSet<>();
       Map<String, Cliente> clientesfilial = new HashMap<>();
       Iterator<Map.Entry<String, Cliente>> it;
       int i, j, nvendas = 0, car;
       double fat = 0.0;
       
       car = ascii(produto.charAt(0));
       if(catProdutos.getSubCatalogo(car).contains(produto) == false)
            throw new ProdutoInexistenteException("O produto que inseriu não existe!");
      
       
       for(j = 0; j < 3; j++)
       {
           clientesfilial = f.get(j).getFilialMes(mes).getClientesFilial();
           
            if(clientesfilial != null)
            {
                it = clientesfilial.entrySet().iterator();
                while(it.hasNext())
                {
                    Map.Entry<String, Cliente> e = it.next();
                    Cliente c = e.getValue();

                    if (c != null)
                    {
                        for(i = 0; i < c.getPrecoFil().size(); i++)
                        {
                            if (c.getProdutoIndice(i).equals(produto) == true)
                            {
                                setclientes.add(e.getKey());
                                nvendas++;
                                fat += c.getQuantidadesIndice(i)*c.getPrecoIndice(i);
                            }
                        }
                    }
                }
            }
       }
       
       
       tpf.adicionaComponentesTPF(nvendas, setclientes.size(), fat);
       
       return tpf;
   }
    
   public Set<String> produtosNaoComprados()
   {
       int j, k;
       Set<String> p = new HashSet<>();
       Set<String> prodsComprados = new HashSet<>();
      
       for(j = 1; j <= 12; j++)
       {
           ProdutosFat pf = faturacao.getFaturacaoMes(j);
           
           if(pf != null)
           {
               for(k = 1; k <= 3; k++)
               {
                   Produtos pr = pf.getProdutosFilial(k);
                   
                   if(pr != null)
                   {
                       Map<String, ComponentesProduto> ps = pr.getProdutos();
                       
                       for(Map.Entry<String, ComponentesProduto> e: ps.entrySet())
                       {
                           prodsComprados.add(e.getKey());
                       }
                   }
               }
           }
       }
       
       for(j = 0; j < 26; j++)
       {
           for(String s: catProdutos.getSubCatalogo(j))
           {
               if(prodsComprados.contains(s) == false)
                     p.add(s);
           }
       }
       
       return p;
   }
   
    public Set<String> produtosMaisVendidos(int numero) throws NumeroInvalidoException
   {
       if(numero < 0)
            throw new NumeroInvalidoException("O número que inseriu não é válido!");
            
       ParProdutoClientes par;
       HashMap<String, ParUnidadesClientes> todos = new HashMap<>();
       
       produtoMaisVendidoFilial(todos, getFilialHiper(1).clone());
       produtoMaisVendidoFilial(todos, getFilialHiper(2).clone());
       produtoMaisVendidoFilial(todos, getFilialHiper(3).clone());
       
       Set<String> finale = new HashSet<>();
       String s;
       int maior = 0;
       Iterator<Map.Entry<String, ParUnidadesClientes>> it;
       int tamanho = todos.size();
       
       for(int k = 0; k < tamanho && k < numero; k++)
       {   
           par  = new ParProdutoClientes();
           maior = 0;
           s = "";
           it = todos.entrySet().iterator();
           while(it.hasNext())
           {
               Map.Entry<String,ParUnidadesClientes> e = it.next();
               if(e.getValue().getQuantos() > maior) 
               {
                   par  = new ParProdutoClientes();
                   par.addProduto(e.getKey());
                   par.addClientes(e.getValue().getNumClientes());
                   maior = e.getValue().getQuantos();
                   s = e.getKey();
               }
           }
           finale.add(par.toString());
           todos.remove(s);
       }
       return finale;
   }
   
   private void produtoMaisVendidoFilial(HashMap<String, ParUnidadesClientes> todos, Filial fi)
   {
       Iterator <Map.Entry<String,Cliente>> it;
       ParUnidadesClientes aux;
       
       for(int mes = 1; mes < 13; mes++)
       {
           it = fi.getFilialMes(mes).getClientesFilial().entrySet().iterator();
           while(it.hasNext())
           {
                Map.Entry<String,Cliente> e = it.next();
                Cliente cli = e.getValue().clone();
                for(int j = 0; j < cli.getNumVendas(); j++)
                {
                    if(todos.containsKey(cli.getProdutoIndice(j)) == false)
                    {
                        aux = new ParUnidadesClientes();
                        aux.addCliente(e.getKey());
                        aux.addUnidade(cli.getQuantidadesIndice(j));
                        todos.put(cli.getProdutoIndice(j), aux);
                    }
                    else
                    {
                        todos.get(cli.getProdutoIndice(j)).addCliente(e.getKey());
                        todos.get(cli.getProdutoIndice(j)).addUnidade(cli.getQuantidadesIndice(j));
                    }
                }
           }
       }
   }
   
   public Set<ParProdutoUnidades> produtosMaisCompradosCliente(String cli) throws ClienteInexistenteException
   {
       int car = ascii(cli.charAt(0));
       if(catClientes.getSubCatalogo(car).contains(cli) == false)
       {
            throw new ClienteInexistenteException("O cliente que inseriu não existe!");
       }
       
       Comparator<ParProdutoUnidades> compProd =
            (p1,p2)->{
                        if(p1.getQuantos() > p2.getQuantos()) return -1;
                        if(p1.getQuantos() < p2.getQuantos()) return 1;
                        else
                            return p1.getProduto().compareTo(p2.getProduto());
                     };
       Set<ParProdutoUnidades> finale = new HashSet<>();
       Map<String, ParProdutoUnidades> produtos = new HashMap<>();//String é o produto
       produtosDoCliente(cli, getFilialHiper(1).clone(), produtos);
       produtosDoCliente(cli, getFilialHiper(2).clone(), produtos);
       produtosDoCliente(cli, getFilialHiper(3).clone(), produtos);
       
       for(Map.Entry<String, ParProdutoUnidades> e : produtos.entrySet())
            finale.add(e.getValue().clone());
       
       return finale;
   }
   
   private void produtosDoCliente(String cli, Filial fi, Map<String, ParProdutoUnidades> pares)
   {
       ParProdutoUnidades par = new ParProdutoUnidades();
       for(int i = 1; i < 13; i++)
       {
           if(fi.getFilialMes(i).getClientesFilial().containsKey(cli) == true)
           {
               Cliente c = fi.getFilialMes(i).getClientePorCodigo(cli);
               for(int j = 0; j < c.getNumVendas(); j++)
               {
                   if(pares.containsKey(c.getProdutoIndice(j)) == false)
                   {
                       par = new ParProdutoUnidades(c.getProdutoIndice(j), c.getQuantidadesIndice(j));
                       pares.put(c.getProdutoIndice(j), par);
                   }
                   else
                       pares.get(c.getProdutoIndice(j)).adicionaQuantidade(c.getQuantidadesIndice(j));
               }
            }
       }
   }
   
   public Vector<ParClienteQuantProds> clientesProdutosDiferentes(int numero) throws NumeroInvalidoException
   {
       if(numero < 0)
            throw new NumeroInvalidoException("O número que inseriu não é válido!");
       
       ParClienteQuantProds par;
       HashMap<String, ParClienteQuantProds> todos = new HashMap<>();
       clientesCompraProdutos(todos, getFilialHiper(1).clone());
       clientesCompraProdutos(todos, getFilialHiper(2).clone());
       clientesCompraProdutos(todos, getFilialHiper(3).clone());
       
       Comparator<ParClienteQuantProds> compQuant =
            (p1,p2)->{
                        if(p1.getProdsDistintos() > p2.getProdsDistintos()) return -1;
                        if(p1.getProdsDistintos() < p2.getProdsDistintos()) return 1;
                        return 0;
                     };

       Vector<ParClienteQuantProds> finale = new Vector<>();
       String s;
       int maior = 0;
       Iterator<Map.Entry<String, ParClienteQuantProds>> it;
       int tam = todos.size();
       for(int k = 0; k < tam && k < numero; k++)
       {   
           par  = new ParClienteQuantProds();
           maior = 0;
           s = "";
           it = todos.entrySet().iterator();
           while(it.hasNext())
           {
               Map.Entry<String,ParClienteQuantProds> e = it.next();
               if(e.getValue().getProdsDistintos() > maior) 
               {
                   par  = new ParClienteQuantProds(e.getKey(), e.getValue().getProds());
                   maior = e.getValue().getProdsDistintos();
                   s = e.getKey();
               }
           }
           finale.add(par.clone());
           todos.remove(s);
       }
       return finale;
   }
   
   private void clientesCompraProdutos(Map<String,ParClienteQuantProds> total, Filial fi)
   {
       ParClienteQuantProds aux = new ParClienteQuantProds();
       Iterator<Map.Entry<String,Cliente>> it;
       int mes, j;
       
       for(mes = 1; mes < 13; mes++)
       {
           it = fi.getFilialMes(mes).getClientesFilial().entrySet().iterator();
           while(it.hasNext())
           {
                Map.Entry<String,Cliente> e = it.next();
                Cliente cli = e.getValue().clone();
                
                for(j = 0; j < cli.getNumVendas(); j++)
                {
                    if(total.containsKey(e.getKey()) == false)
                    {
                        aux = new ParClienteQuantProds();
                        aux.setCliente(e.getKey());
                        aux.adicionaProduto(cli.getProdutoIndice(j));
                        total.put(e.getKey(), aux);
                    }
                    else
                        total.get(e.getKey()).adicionaProduto(cli.getProdutoIndice(j));                
                }
           }
       }
   }
   
   public Set<ParClienteCompraFaturado> produtoNClientesFaturado(String produto, int numero) throws ProdutoInexistenteException,
                                                                                                    NumeroInvalidoException
   {
       if(catProdutos.getSubCatalogo(ascii(produto.charAt(0))).contains(produto) == false)
            throw new ProdutoInexistenteException("O produto que inseriu não existe!");
            
       if(numero < 0)
            throw new NumeroInvalidoException("O número que inseriu não é válido!");
            
       Comparator<ParClienteCompraFaturado> compCli =
        (p1,p2)->{
                   if(p1.getQuantidades() > p2.getQuantidades()) return -1;
                   if(p1.getQuantidades() < p2.getQuantidades()) return 1;
                   return p1.getCliente().compareTo(p2.getCliente());
                };
       
       Map<String, ParClienteCompraFaturado> compras = new HashMap<>();//string é o cliente
       
       atualizaComprasDoProduto(produto, compras, getFilialHiper(1).clone());
       atualizaComprasDoProduto(produto, compras, getFilialHiper(2).clone());
       atualizaComprasDoProduto(produto, compras, getFilialHiper(3).clone());
       
       Set<ParClienteCompraFaturado> finale = new HashSet<>();
       String s;
       int maior = 0;
       ParClienteCompraFaturado par;
       Iterator<Map.Entry<String, ParClienteCompraFaturado>> it;
       int tam = compras.size();
       for(int k = 0; k < tam && k < numero; k++)
       {   
           par  = new ParClienteCompraFaturado();
           maior = 0;
           s = "";
           it = compras.entrySet().iterator();
           while(it.hasNext())
           {
               Map.Entry<String,ParClienteCompraFaturado> e = it.next();
               if(e.getValue().getQuantidades() > maior) 
               {
                   par  = new ParClienteCompraFaturado(e.getKey(),e.getValue().getQuantidades(), e.getValue().getFaturado());
                   maior = e.getValue().getQuantidades();
                   s = e.getKey();
               }
           }
           finale.add(par.clone());
           compras.remove(s);
       }
       return finale;
   }
   
   private void atualizaComprasDoProduto(String prod, Map<String, ParClienteCompraFaturado> compras, Filial fi)
   {
       ParClienteCompraFaturado aux = new ParClienteCompraFaturado();
       Iterator<Map.Entry<String,Cliente>> it;
       
       for(int mes = 1; mes < 13; mes++)
       {
           it = fi.getFilialMes(mes).getClientesFilial().entrySet().iterator();
           while(it.hasNext())
           {
                Map.Entry<String,Cliente> e = it.next();
                Cliente cli = e.getValue().clone();
                for(int j = 0; j < cli.getNumVendas(); j++)
                {        
                    if(prod.equals(cli.getProdutoIndice(j)) == true)
                    {
                        if(compras.containsKey(e.getKey()) == false)
                        {
                            aux = new ParClienteCompraFaturado();
                            aux.setCliente(e.getKey());
                            aux.adicionaQuantidade(cli.getQuantidadesIndice(j));
                            aux.adicionaFaturado(cli.getPrecoIndice(j) * cli.getQuantidadesIndice(j));
                            compras.put(e.getKey(), aux);
                        }
                        else
                        {
                            compras.get(e.getKey()).adicionaQuantidade(cli.getQuantidadesIndice(j));
                            compras.get(e.getKey()).adicionaFaturado(cli.getPrecoIndice(j) * cli.getQuantidadesIndice(j));
                        }
                    }
                }            
           }
       }
   }
   
  
   public Hipermercado clone()
   {
       return new Hipermercado(this);
   }
   
   public boolean equals(Object o)
   {
       if (o == this) return true;
       if (o == null || o.getClass() != this.getClass()) return false;
       
       Hipermercado h = (Hipermercado) o;
       
       if (h.getCatProdutos().equals(this.catProdutos) == false) return false;
       if (h.getCatClientes().equals(this.catClientes) == false) return false;
       if(getFilialHiper(1).clone().equals(h.getFilialHiper(1).clone()) == false) return false;
       if(getFilialHiper(2).clone().equals(h.getFilialHiper(2).clone()) == false) return false;
       if(getFilialHiper(3).clone().equals(h.getFilialHiper(3).clone()) == false) return false;
       if(faturacao.equals(h.getFaturacaoHiper()) == false) return false;
       
       return true;
   }
   
   public String toString()
   {
       StringBuilder sb = new StringBuilder();
       sb.append(catProdutos.toString()); sb.append("\n");
       sb.append(catClientes.toString()); sb.append("\n");
       sb.append(faturacao.toString()); sb.append("\n");
       for(int i = 0; i < 3; i++)
       {
           sb.append(f.get(i).toString()); sb.append("\n");
       }
       
       return sb.toString();
   }
}
