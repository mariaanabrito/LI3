import java.util.List;
import java.util.ArrayList;

/**
 * Classe Paginacao é responsável por armazenar e navegar as informações que vão ser apresentadas ao utilizador na aplicação. 
 */
public class Paginacao
{
   /**
    * Variáveis de instância que representam uma página (informação que está a ser mostrada ao utilizador) e o conjunto todo de informação.
    */
   private List<String> pagina, todo;
   /**
    *  Tamanho da página, o índice, o tamanho da informação total e o número da página que está a ser mostrada.
    */
   private int pageTam, index, todoTam, npage;
   
   /**
    * Construtor Vazio
    */
   public Paginacao()
   {
       pagina = new ArrayList<String>(20);
       todo = new ArrayList<String>();
       pageTam = index = todoTam = npage = 0;
   }
   
   /**
    * Construtor por parâmentros
    */
   public Paginacao(ArrayList<String> p, ArrayList<String> t, int pTam, int ind, int tTam, int np)
   {
       pagina = p;
       todo = t;
       pTam = pageTam;
       index = ind;
       todoTam = tTam;
       npage = np;
   }
   
   /**
    * Construtor por cópia
    */
   public Paginacao(Paginacao p)
   {
       pagina = p.getPagina();
       todo = p.getTodo();
       pageTam = p.getTamanhoPagina();
       index = p.getIndice();
       todoTam = p.getTamanhoTodo();
       npage = p.getNumeroPagina();
   }
   
   /**
    * Devolve a página atual
    */
   public List<String> getPagina()
   {
       List<String> novo = new ArrayList<>();
       
       for(String s: pagina)
            novo.add(s);
            
       return novo;
   }
   
   /**
    * Devolve o conjunto total de informação
    */
   public List<String> getTodo()
   {
       List<String> novo = new ArrayList<>();
       
       for(String s: todo)
            novo.add(s);
            
       return novo;
   }
   
   /**
    * Devolve tamanho da página
    */
   public int getTamanhoPagina()
   {
       return pageTam;
   }
   
   /**
    * Devolve índice atual
    */
   public int getIndice()
   {
       return index;
   }
   
   /**
    * Devolve tamanho da informação total
    */
   public int getTamanhoTodo()
   {
       return todoTam;
   }
   
   /**
    * Devolve número da página atual
    */
   public int getNumeroPagina()
   {
       return npage;
   }
   
   /**
    * Devolve número de páginas
    */
   public int getNumeroPaginasTotais()
   {
       return roundup(todoTam);
   }
   
   /**
    * Define a página
    */
   public void setPagina(List<String> p)
   {
       pagina = new ArrayList<>();
       
       for(String s: p)
            pagina.add(s);
   }
   
   /**
    * Define a informação total
    */
   public void setTodo(List<String> t)
   {
       todo = new ArrayList<>();
       
       for(String s: t)
            todo.add(s);
   }
   
   /**
    * Define o tamanho da página
    */
   public void setTamanhoPagina(int pt)
   {
       pageTam = pt;
   }
   
   /**
    * Define o índice atual
    */
   public void setIndice(int i)
   {
       index = i;
   }
   
   /**
    * Define o tamanho total
    */
   public void setTamanhoTodo(int tt)
   {
       todoTam = tt;
   }
   
   /**
    * Define o número da página atual
    */
   public void setNumeroPagina(int np)
   {
       npage = np;
   }
   
   /**
    * Acrescenta um elemento à informação total
    */
   public void acrescentaElementoAoTodo(String s)
   {
       todo.add(s);
       todoTam++;
   }
   
   /**
    * Arredonda número de páginas total para permitir que a última página possa ter menos elementos que o tamanho da página definido
    */
   int roundup(int n)
   {
       int t = n / 20;
       
       if(n%20 != 0)
            t += 1;
       
       return t;
   }
   
   /**
    * Devolve a página seguinte, caso exista. Caso Contrário, lança uma exceção que indica que a página seguinte é inválida.
    */
   public List<String> getPaginaNext() throws PaginaInvalidaException
   {
       int paginasTotal, patual, tamrest, tamcopy, i;
       
       paginasTotal = roundup(todoTam);
       
       if(npage > paginasTotal || npage < 0)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
       
       patual = npage*20;
       
       if (patual < 0 || patual > todoTam)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
            
       npage++;
       
       tamrest = todoTam - patual;
       
       if(tamrest > 20)
            tamcopy = 20;
       else tamcopy = tamrest;
       
       pagina = new ArrayList<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   /**
    * Devolve a página anterior, caso exista. Caso contrário, lança uma exceção que indica que a página anterior é inválida.
    */
   public List<String> getPaginaAnt() throws PaginaInvalidaException
   {
       int paginasTotal, patual, tamrest, tamcopy, i;
       
       paginasTotal = roundup(todoTam);
       
       if(npage > paginasTotal || npage < 0)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
       
       patual = npage*20;
       
       if (patual < 0 || patual > todoTam)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
            
       npage--;
       
       tamrest = todoTam - patual;
       
       if (tamrest > 20)
            tamcopy = 20;
       else tamcopy = tamrest;
       
       pagina = new ArrayList<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   /**
    * Devolve página numerada pelo utilizador, caso exista. Caso contrário, lança uma exceção a indicar que a página numerada é inválida.
    */
   public List<String> getPaginaP(int p) throws PaginaInvalidaException
   {
       int paginasTotal, patual, tamrest, tamcopy, i;
       
       paginasTotal = roundup(todoTam);
       
       if(npage > paginasTotal || npage < 0)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
       
       patual = p*20 - 20;
       
       if (patual < 0 || patual > todoTam)
            throw new PaginaInvalidaException("A página a que pretende aceder é inválida!");
            
       npage = p;
       
       tamrest = todoTam - patual;

       if (tamrest > 20)
            tamcopy = 20;
       else tamcopy = tamrest;
       
       pagina = new ArrayList<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   /**
    * Devolver uma cópia da instância
    */
   public Paginacao clone()
   {
       return new Paginacao(this);
   }
   
   /**
    * Verifica a igualdade com outro objecto
    */
   public boolean equals(Object o)
   {
       if (o == this) return true;
       if (o == null || o.getClass() != this.getClass()) return false;
       
       Paginacao p = (Paginacao) o;
       
       if (pagina.equals(p.getPagina()) == false) return false;
       if (todo.equals(p.getTodo()) == false) return false;
       if (pageTam != p.getTamanhoPagina() || index != p.getIndice() || todoTam != p.getTamanhoTodo() ||
           npage != p.getNumeroPagina()) return false;
       return true;
   }
   
   /**
    * Devolve representação textual da página
    */
   public String toString()
   {
       StringBuilder sb = new StringBuilder();
       for(String s: pagina)
            sb.append(s + "\n");
       sb.append("Página " + (npage - 1) + " de " + roundup(todoTam) + "\n");
       
       return sb.toString();
   }
}
