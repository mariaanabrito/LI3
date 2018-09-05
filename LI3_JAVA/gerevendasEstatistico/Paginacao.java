import java.util.Vector;
import java.util.Vector;

public class Paginacao
{
   private Vector<String> pagina, todo;
   private int pageTam, index, todoTam, npage;
   
   public Paginacao()
   {
       pagina = new Vector<String>(20);
       todo = new Vector<String>();
       pageTam = index = todoTam = npage = 0;
   }
   
   public Paginacao(Vector<String> p, Vector<String> t, int pTam, int ind, int tTam, int np)
   {
       pagina = p;
       todo = t;
       pTam = pageTam;
       index = ind;
       todoTam = tTam;
       npage = np;
   }
   
   public Paginacao(Paginacao p)
   {
       pagina = p.getPagina();
       todo = p.getTodo();
       pageTam = p.getTamanhoPagina();
       index = p.getIndice();
       todoTam = p.getTamanhoTodo();
       npage = p.getNumeroPagina();
   }
   
   public Vector<String> getPagina()
   {
       Vector<String> novo = new Vector<>();
       
       for(String s: pagina)
            novo.add(s);
            
       return novo;
   }
   
   public Vector<String> getTodo()
   {
       Vector<String> novo = new Vector<>();
       
       for(String s: todo)
            novo.add(s);
            
       return novo;
   }
   
   public int getTamanhoPagina()
   {
       return pageTam;
   }
   
   public int getIndice()
   {
       return index;
   }
   
   public int getTamanhoTodo()
   {
       return todoTam;
   }
   
   public int getNumeroPagina()
   {
       return npage;
   }
   
   public int getNumeroPaginasTotais()
   {
       return roundup(todoTam);
   }
   
   public void setPagina(Vector<String> p)
   {
       pagina = new Vector<>();
       
       for(String s: p)
            pagina.add(s);
   }
   
   public void setTodo(Vector<String> t)
   {
       todo = new Vector<>();
       
       for(String s: t)
            todo.add(s);
   }
   
   public void setTamanhoPagina(int pt)
   {
       pageTam = pt;
   }
   
   public void setIndice(int i)
   {
       index = i;
   }
   
   public void setTamanhoTodo(int tt)
   {
       todoTam = tt;
   }
   
   public void setNumeroPagina(int np)
   {
       npage = np;
   }
   
   public void acrescentaElementoAoTodo(String s)
   {
       todo.add(s);
       todoTam++;
   }
   
   int roundup(int n)
   {
       int t = n / 20;
       
       if(n%20 != 0)
            t += 1;
       
       return t;
   }
   
   public Vector<String> getPaginaNext() throws PaginaInvalidaException
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
       
       pagina = new Vector<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   public Vector<String> getPaginaAnt() throws PaginaInvalidaException
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
       
       pagina = new Vector<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   public Vector<String> getPaginaP(int p) throws PaginaInvalidaException
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
       
       pagina = new Vector<>();
       
       for(i = 0; i < tamcopy; i++, patual++)
       {
           pagina.add(i, todo.get(patual));
       }
       
       return pagina;
   }
   
   
   
   
   
   
   
   public Paginacao clone()
   {
       return new Paginacao(this);
   }
   
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
   
   public String toString()
   {
       StringBuilder sb = new StringBuilder();
       for(String s: pagina)
            sb.append(s + "\n");
       sb.append("Página " + (npage - 1) + " de " + roundup(todoTam) + "\n");
       
       return sb.toString();
   }
}
