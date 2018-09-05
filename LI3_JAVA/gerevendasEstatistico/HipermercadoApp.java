import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;
import java.util.Set;
import java.util.NavigableSet;
import java.util.Iterator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;
import java.util.Vector;

public class HipermercadoApp
{
    private HipermercadoApp(){}
    
    private static Hipermercado hiper;
    
    private static Menu menuprincipal, menu2, menu3, menu4, menu5, menu6, menu7;
    
    public static void main(String[] args)
    {
        carregarMenus();
        hiper = initApp();

        lerFicheiros();
        do
        {
            menuprincipal.executarMenu();
         
            switch(menuprincipal.getOpcao())
            {
                case 1: produtosNaoCompradosOrdenados();
                        break;
                case 2: vendasEClientesNumMes();
                        break;
                case 3: informacaoSobreUmCliente();
                        break;
                case 4: informacaoSobreUmProduto();
                        break;
                case 5: produtosMaisCompradosPorCliente();
                        break;
                case 6: produtosMaisVendidos();
                        break;
                case 7: tresClientesporFilial();
                        break;
                case 8: clientesComProdutosDistintos();
                        break;
                case 9: clientesQueMaisCompraramProduto();
                        break;
                case 10: totalDeComprasMes();
                         break;
                case 11: faturacaoPorMes();
                         break;
                case 12: clientesDistintosMes();
                         break;
                case 13: lerFicheiros();
                         break;
                case 14: initApp();
                         break;
                case 15: gravarObjetos();
                         break;
            }
        }while(menuprincipal.getOpcao() != 0);
    }
    
    private static void carregarMenus()
    {
        String[] opsprincipais = {"Lista de Produtos Não Comprados",
                                  "Número total global de vendas realizadas e o número de clientes distintos que as realizaram",
                                  "Número de compras realizadas, número de produtos distintos e total gasto por um cliente",
                                  "Número de vezes que um produto foi comprado, quantos clientes diferentes o compraram e total faturado",
                                  "Produtos mais vendidos para um determinado cliente",
                                  "Conjunto dos n produtos mais vendidos",
                                  "Top 3 de maiores compradores, em termos de faturação, para cada filial",
                                  "Conjunto dos n clientes que compraram mais produtos distintos",
                                  "Conjunto dos n clientes que mais compraram certo produto e o seu total faturado",
                                  "Número total de compras por mês",
                                  "Faturação total por mês para cada filial ou o valor total global",
                                  "Número de clientes distintos que compraram em cada mês",
                                  "Ler Ficheiros",
                                  "Carregar Objetos",
                                  "Gravar Objetos"};
                                  
        String[] ops2 = {"Ler Ficheiro de Vendas_1M",
                         "Ler Ficheiro de Vendas_3M",
                         "Ler Ficheiro de Vendas_5M",
                         "Não Quero Ler Nenhum Ficheiro de Vendas"};
                       
        String[] ops3 = {"Filial 1",
                         "Filial 2",
                         "Filial 3",
                         "Valores Globais"};
                         
        String[] ops4 = {"Página Seguinte",
                         "Página Anterior",
                         "Introduzir o Número da Página",
                         "Número Total de Produtos Não Comprados"};
                         
        String[] ops5 = {"Página Seguinte",
                         "Página Anterior",
                         "Introduzir o Número da Página"};
                    
        String[] ops6 = {"Página Seguinte",
                         "Página Anterior",
                         "Introduzir o Número da Página",
                         "Número Total de Produtos Mais Comprados"};
                         
        String[] ops7 = {"Inserir Nome de um Ficheiro"};
                                  
        menuprincipal = new Menu(opsprincipais);
        menu2 = new Menu(ops2);
        menu3 = new Menu(ops3);
        menu4 = new Menu(ops4);
        menu5 = new Menu(ops5);
        menu6 = new Menu(ops6);
        menu7 = new Menu(ops7);
    }

    private static Hipermercado initApp()
    {
       String fich;
       try
       {
           System.out.println("\nLeitura de Ficheiros de Objetos:");
           menu7.executarMenu();
           switch(menu7.getOpcao())
           {
               case 0: hiper.leObjeto("naoexiste.dat");
                       break;
               case 1: System.out.println("Insira o nome do ficheiro :");
                       fich = Input.lerString();
                       hiper = hiper.leObjeto(fich);
                       break;
           }
       }
       catch(IOException e)
       {
           hiper = new Hipermercado();
           System.out.println("ERRO DE LEITURA: Os dados não foram lidos!");
       }
       catch(ClassNotFoundException e)
       {
           hiper = new Hipermercado();
           System.out.println("ERRO DE FORMATO: Ficheiro com formato incorreto!");
       }
       catch(ClassCastException e)
       {
           hiper = new Hipermercado();
           System.out.println("ERRO DE FORMATO: Erro de formato!");
       }
        
       return hiper;
    }
    
    /**
     * Grava objetos num ficheiro válido cujo nome o utilizador introduz.
     */
    private static void gravarObjetos()
    {
        String fich;
        try
        {
            System.out.println("Insira o nome do ficheiro :");
            fich = Input.lerString();
            hiper.gravaObj(fich);
        }
        catch(IOException e)
        {
            System.out.println("Os dados não foram gravados!");
        }
    }
      
    private static void lerFicheiros()
    {
      BufferedReader inStream = null; 
      String linha = null, fich = null;
      Venda v;
      int vendasInvalidas, comprasNulas;
      double fatTotal;
      HashSet<String> codclientes = new HashSet<>();
      HashSet<String> codprods = new HashSet<>();
      HashSet<String> prodscomprados = new HashSet<>();
      HashSet<String> clientescompraram = new HashSet<>();
      Crono c = null;
      
      vendasInvalidas = comprasNulas = 0;
      fatTotal = 0.0;
      try
      {
          c.start();
          inStream = new BufferedReader(new FileReader("Clientes.txt"));
          while( (linha = inStream.readLine()) != null )
          {
              hiper.adicionaCliente(linha);
              codclientes.add(linha);
          }             
          c.stop();
          System.out.println("\nFoi lido o ficheiro Clientes.txt\n");
          System.out.println("Ficheiro Clientes.txt foi lido em " + c.print() + " segundos.\n");
      }
      catch(IOException e) 
      {
          System.out.println(e.getMessage());
      }
      
      try
      {
         c.start();
         inStream = new BufferedReader(new FileReader("Produtos.txt"));
         while( (linha = inStream.readLine()) != null)
         { 
             hiper.adicionaProduto(linha);
             codprods.add(linha);
         }
         c.stop();
         System.out.println("Foi lido o ficheiro Produtos.txt\n");
         System.out.println("Ficheiro Produtos.txt foi lido em " + c.print() + " segundos.");
      }
      catch(IOException e) 
      {
          System.out.println(e.getMessage());
      }
      menu2.executarMenu();

      if(menu2.getOpcao() != 4)
      {
          switch (menu2.getOpcao())
          {
              case 0: System.out.println("Programa a encerrar...");
                      System.exit(0);
              case 1: fich = "Vendas_1M.txt";
                      break;
              case 2: fich = "Vendas_3M.txt";
                      break;
              case 3: fich = "Vendas_5M.txt";
                      break;
          }
          try
          {
              hiper.getFaturacaoHiper().getFaturacao().clear();
              for(int i = 1; i <= 3; i++)
                    hiper.getFilialHiper(i).getFilial().clear();
              c.start();
              inStream = new BufferedReader(new FileReader(fich));
              while( (linha = inStream.readLine()) != null )
              {
                  v = hiper.parseLinhaVenda(linha);
                  if(v.vendaValida(codclientes, codprods) == false)
                  {
                      vendasInvalidas++;
                  }
                  else
                  {
                      prodscomprados.add(v.getProduto());
                      clientescompraram.add(v.getCliente());
                      if (v.getPreco() == 0)
                            comprasNulas++;
                      BigDecimal bd = new BigDecimal(v.getPreco()*v.getQuantos()).setScale(2, RoundingMode.HALF_EVEN);
                      double aux = bd.doubleValue();
                      fatTotal += v.getPreco()*v.getQuantos();
                      hiper.updateFilial(v);
                      hiper.updateFaturacao(v);
                  }
              }
              c.stop();
              System.out.println("Foi lido o ficheiro: " + fich + " em " + c.print() + " segundos.");
              System.out.println("O número de vendas inválidas é: " + vendasInvalidas);
              System.out.println("O número total de produtos é: " + codprods.size());
              System.out.println("O número total de produtos diferentes comprados é: " + prodscomprados.size());
              System.out.println("O número total de produtos não comprados é: " + (codprods.size() - prodscomprados.size()));
              System.out.println("O número total de clientes é: " + codclientes.size());
              System.out.println("O número total de clientes que realizaram compras é: " + clientescompraram.size());
              System.out.println("O número total de clientes que nada compraram é: " + (codclientes.size() - clientescompraram.size()));
              System.out.println("O número total de compras de valor total igual a 0.0 é: " + comprasNulas);
              BigDecimal bd1 = new BigDecimal(fatTotal).setScale(2, RoundingMode.HALF_EVEN);
              double f = bd1.doubleValue();
              System.out.println("A faturação total é: " + f);
          }
          catch(IOException e) 
          {
              System.out.println(e.getMessage());
          }
      }
    }

    private static void vendasEClientesNumMes() /** Query 2 */
    {
        Crono c = null;
        int mes;
        
        System.out.println("\nInsira um mês: ");
        mes = Input.lerInt();
        
        ParVendasClientes par = new ParVendasClientes();
        
        c.start();
        try
        {
            par = hiper.vendasNumMes(mes).clone();
        }
        catch(MesInvalidoException e)
        {
            System.out.println(e.getMessage());
        }
        c.stop();
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        
        System.out.println(par.toString());
    }
    
    private static void tresClientesporFilial() /**Query 7*/
    {
        Crono c = null;
        
        c.start();
        Par3MaioresCompradores par = hiper.top3ClientesPorFilial();
        c.stop();
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        
        System.out.println(par.toString());
    }
    
    private static void informacaoSobreUmCliente() /** Query 3 */
    {
        Vector<TuploProdutosFat> tpf = new Vector<>(14);
        String codigoCliente;
        Crono c = null;
        int i;
        
        System.out.println("\nInsira o código de cliente: ");
        codigoCliente = Input.lerString();
       
        try
        {
            c.start();
            for(i = 1; i <= 12; i++)
            {
                tpf.add(i-1 ,hiper.infoDeCliente(codigoCliente, i).clone());
            }
            c.stop();
            System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        }
        catch(ClienteInexistenteException e)
        {
            System.out.println(e.getMessage());
        }
        for(i = 1; i <= 12; i++)
        {
            System.out.println("Mês " + i + ":\n");
            System.out.println(tpf.get(i-1).toString());
        }
    }
    
    private static void informacaoSobreUmProduto() /** Query 4 */
    {
        Vector<TuploProdutosFat> tpf = new Vector<>(14);
        String codigoProduto;
        Crono c = null;
        int i;
        
        System.out.println("\nInsira o código de produto: ");
        codigoProduto = Input.lerString();
        
        try
        {
            c.start();
            for(i = 1; i <= 12; i++)
            {
                tpf.add(i-1, hiper.infoDeProduto(codigoProduto, i).clone());
            }
            c.stop();
            System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        }
        catch(ProdutoInexistenteException e)
        {
            System.out.println(e.getMessage());
        }
        
        for(i = 1; i <= 12; i++)
        {
            System.out.println("Mês " + i + ":\n");
            System.out.println(tpf.get(i-1).toString());
        }
    }
    
    private static void totalDeComprasMes() /** Estatística */
    {
        Vector<Integer> array = new Vector<>(14);
        Crono c = null;
        int n;
        
        c.start();
        for(int i = 1; i < 13;i++)
        {
            array.add(i-1, hiper.comprasNumMes(i));
        }
        c.stop();
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
            
        for(int i = 1; i < 13; i++)
            System.out.println("Mês " + i + " teve número de vendas igual a " + array.get(i-1));
    }
    
    private static void faturacaoPorMes() /** Estatística */
    {
        Vector<Double> array = new Vector<>(14);
        Crono c = null;
        menu3.executarMenu();
        double total = 0.0;
        
        switch(menu3.getOpcao())
        {
            case 1: c.start();
                    for(int i = 1; i < 13;i++)
                    {
                         array.add(i-1, hiper.faturacaoNumMes(i, 1));
                    }
                    c.stop();
                    System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
                    for(int i = 1; i < 13; i++)
                        System.out.println("Mês " + i + " faturou " + array.get(i-1));
                    break;
            case 2: c.start();
                    for(int i = 1; i < 13;i++)
                    {
                         array.add(i-1, hiper.faturacaoNumMes(i, 2));
                    }
                    c.stop();
                    System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
                    for(int i = 1; i < 13; i++)
                        System.out.println("Mês " + i + " faturou " + array.get(i-1));
                    break;
            case 3: c.start();
                    for(int i = 1; i < 13;i++)
                    {
                         array.add(i-1, hiper.faturacaoNumMes(i, 3));
                    }
                    c.stop();
                    System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
                    for(int i = 1; i < 13; i++)
                        System.out.println("Mês " + i + " faturou " + array.get(i-1));
                    break;
            case 4: c.start();
                    for(int i = 1; i < 13;i++)
                    {
                        total += hiper.faturacaoNumMes(i, 1);
                    }
                    for(int i = 1; i < 13;i++)
                    {
                        total += hiper.faturacaoNumMes(i, 2);
                    }
                    for(int i = 1; i < 13;i++)
                    {
                         total += hiper.faturacaoNumMes(i, 3);
                    }
                    c.stop();
                    System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
                    System.out.println("Valor global " + total);
                    break;
        }
    }
    
    private static void clientesDistintosMes() /** Estatística */
    {
        Crono c = null;
        Vector<Integer> array = new Vector<>(14);
        int i;
        
        c.start();
        for(i = 1; i < 13; i++)
        {
            array.add(i-1, hiper.clientesDistintosNumMes(i));
        }
        c.stop();
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        
        for(i = 1; i < 13; i++)
            System.out.println("Mês " + i + " teve número de clientes distintos igual a " + array.get(i-1));
    }
    
    private static void produtosNaoCompradosOrdenados() /** Query 1 */
    {
        Set<String> p;
        Paginacao paginacao;
        Vector<String> page = null;
        int num;
        NavigableSet<String> navigableSet = new TreeSet<>();
        Iterator<String> it;
        Crono c = null;
        
        c.start();
        p = hiper.produtosNaoComprados();
        c.stop();
        
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        
        for(String pr: p)
        {
            navigableSet.add(pr);
        }
        it = navigableSet.iterator();
        
        paginacao = new Paginacao();
        
        while(it.hasNext())
        {
            String st = it.next();
            paginacao.acrescentaElementoAoTodo(st);
        }
        
        try
        {
            page = paginacao.getPaginaNext();
            System.out.println("Produtos Não Comprados:\n");
            for(String e: page)
                  System.out.println(e);
            System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
        }
        catch(PaginaInvalidaException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nInsira uma das opções seguintes:\n");
        do
        {
            menu4.executarMenu();
        
            switch (menu4.getOpcao())
            {
                case 1: try
                        {
                            page = paginacao.getPaginaNext();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Não Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 2: try
                        {
                            page = paginacao.getPaginaAnt();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Não Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 3: System.out.println("Insira o número da página a que deseja aceder:");
                        num = Input.lerInt();
                        try
                        {
                            page = paginacao.getPaginaP(num);
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Não Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 4: System.out.println("\nNão foram comprados " + p.size() + " produtos.\n");
            }
        }while(menu4.getOpcao() != 0);
        
    }
    
    private static void produtosMaisVendidos() /** Query 6 */
    {
        Set<String> p = null;
        Paginacao paginacao;
        Vector<String> page = null;
        int num;
        NavigableSet<String> navigableSet = new TreeSet<>();
        Iterator<String> it;
        Crono c = null;
        
        int numero; 
        System.out.println("Insira o número de produtos mais vendidos que deseja ver:");
        numero = Input.lerInt();
        
        try
        {
            c.start();
            p = hiper.produtosMaisVendidos(numero);
            c.stop();
        }
        catch(NumeroInvalidoException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Tempo de execução: " + c.print() + " segundos.");
  
        
        for(String pr: p)
        {
            navigableSet.add(pr);
        }
        it = navigableSet.iterator();
        
        paginacao = new Paginacao();
        
        while(it.hasNext())
        {
            String st = it.next();
            paginacao.acrescentaElementoAoTodo(st);
        }
        
        try
        {
            page = paginacao.getPaginaNext();
            System.out.println("Produtos Mais Vendidos:\n");
            for(String e: page)
                  System.out.println(e);
            System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
        }
        catch(PaginaInvalidaException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nInsira uma das opções seguintes:\n");
        do
        {
            menu5.executarMenu();
        
            switch (menu5.getOpcao())
            {
                case 1: try
                        {
                            page = paginacao.getPaginaNext();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Vendidos:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 2: try
                        {
                            page = paginacao.getPaginaAnt();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Vendidos:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 3: System.out.println("Insira o número da página a que deseja aceder:");
                        num = Input.lerInt();
                        try
                        {
                            page = paginacao.getPaginaP(num);
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Vendidos:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
            }
        }while(menu5.getOpcao() != 0);
    }
    
    private static void produtosMaisCompradosPorCliente() /** Query 5 */
    {
        String cliente;
        System.out.println("Insira o cliente");
        cliente = Input.lerString();
        Set<ParProdutoUnidades> p = new HashSet<>();
        Crono c = null;
        
        try
        {
            c.start();
            p = hiper.produtosMaisCompradosCliente(cliente);
            c.stop();
        }
        catch(ClienteInexistenteException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
        
        Paginacao paginacao = new Paginacao();
        Vector<String> page = null;
        int num;
        Iterator<ParProdutoUnidades> it;
        
        it = p.iterator();
        
        while(it.hasNext())
        {
            ParProdutoUnidades par = it.next();
            paginacao.acrescentaElementoAoTodo(par.toString());
        }

        try
        {
            page = paginacao.getPaginaNext();
            System.out.println("Produtos Mais Comprados:\n");
            for(String e: page)
                  System.out.println(e);
            System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
        }
        catch(PaginaInvalidaException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nInsira uma das opções seguintes:\n");
        do
        {
            menu6.executarMenu();
        
            switch (menu6.getOpcao())
            {
                case 1: try
                        {
                            page = paginacao.getPaginaNext();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 2: try
                        {
                            page = paginacao.getPaginaAnt();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 3: System.out.println("Insira o número da página a que deseja aceder:");
                        num = Input.lerInt();
                        try
                        {
                            page = paginacao.getPaginaP(num);
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Produtos Mais Comprados:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 4: System.out.println("\nNúmero de Produtos Comprados: " + p.size() + "\n");
            }
        }while(menu6.getOpcao() != 0);
    }
    
    private static void clientesComProdutosDistintos() /** Query 8 */
    {
        int numero;
        System.out.println("Insira o número de clientes que deseja ver:");
        numero = Input.lerInt();
        Crono c = null;
        Vector<ParClienteQuantProds> p = new Vector<>();
        
        try
        {
            c.start();
            p = hiper.clientesProdutosDiferentes(numero);
            c.stop();
        }
        catch(NumeroInvalidoException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Tempo de execução: " + c.print() + " segundos.");
        
        Paginacao paginacao = new Paginacao();
        Vector<String> page = null;
        int num;
        Iterator<ParClienteQuantProds> it;
        
        it = p.iterator();
        
        while(it.hasNext())
        {
            ParClienteQuantProds par = it.next();
            paginacao.acrescentaElementoAoTodo(par.toString());
        }
        
        try
        {
            page = paginacao.getPaginaNext();
            System.out.println("Clientes Que Compraram Mais Produtos Diferentes:\n");
            for(String e: page)
                  System.out.println(e);
            System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
        }
        catch(PaginaInvalidaException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nInsira uma das opções seguintes:\n");
        do
        {
            menu5.executarMenu();
        
            switch (menu5.getOpcao())
            {
                case 1: try
                        {
                            page = paginacao.getPaginaNext();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Clientes Que Compraram Mais Produtos Diferentes:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 2: try
                        {
                            page = paginacao.getPaginaAnt();
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Clientes Que Compraram Mais Produtos Diferentes:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 3: System.out.println("Insira o número da página a que deseja aceder:");
                        num = Input.lerInt();
                        try
                        {
                            page = paginacao.getPaginaP(num);
                        }
                        catch(PaginaInvalidaException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Clientes Que Compraram Mais Produtos Diferentes:\n");
                        for(String e: page)
                            System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
            }
        }while(menu5.getOpcao() != 0);
    }
    
    private static void clientesQueMaisCompraramProduto() /** Query 9 */
    {
        String produto;
        int numero;
        Set<ParClienteCompraFaturado> p = new HashSet<>();
        Crono c = null;
        
        System.out.println("Insira o produto");
        produto = Input.lerString();
        System.out.println("Insira o número de clientes que deseja ver:");
        numero = Input.lerInt();
        
        try
        {
            c.start();
            p = hiper.produtoNClientesFaturado(produto, numero);
            c.stop();
        }
        catch(ProdutoInexistenteException e)
        {
            System.out.println(e.getMessage());
        }
        catch(NumeroInvalidoException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\n---Tempo de execução: " + c.print() + " segundos.---\n");
            
        Paginacao paginacao = new Paginacao();
        Vector<String> page = null;
        int num; 
        Iterator<ParClienteCompraFaturado> it;
            
        it = p.iterator();
            
        while(it.hasNext())
        {
            ParClienteCompraFaturado par = it.next();
            paginacao.acrescentaElementoAoTodo(par.toString());
        }
            
        try
        {
            page = paginacao.getPaginaNext();
            System.out.println("Clientes que mais compraram o produto: "+ produto +"\n");
            for(String e: page)
                   System.out.println(e);
            System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
        }
        catch(PaginaInvalidaException e)
        {
             System.out.println(e.getMessage());
        }
            
        System.out.println("\nInsira uma das opções seguintes:\n");
        do
        {
            menu5.executarMenu();
            
            switch (menu5.getOpcao())
            {
                case 1: try
                        {
                             page = paginacao.getPaginaNext();
                        }
                        catch(PaginaInvalidaException e)
                        {
                             System.out.println(e.getMessage());
                        }
                        System.out.println("Clientes que mais compraram o produto: "+ produto +"\n");
                        for(String e: page)
                             System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
                case 2: try
                        {
                             page = paginacao.getPaginaAnt();
                        }
                        catch(PaginaInvalidaException e)
                        {
                             System.out.println(e.getMessage());
                        }
                        System.out.println("Clientes que mais compraram o produto: "+ produto +"\n");
                        for(String e: page)
                             System.out.println(e);
                        System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                        break;
               case 3: System.out.println("Insira o número da página a que deseja aceder:");
                       num = Input.lerInt();
                       try
                       {
                           page = paginacao.getPaginaP(num);
                       }
                       catch(PaginaInvalidaException e)
                       {
                            System.out.println(e.getMessage());
                       }
                       System.out.println("Clientes que mais compraram o produto: "+ produto +"\n");
                       for(String e: page)
                             System.out.println(e);
                       System.out.println("Página " + paginacao.getNumeroPagina() + " de " + paginacao.getNumeroPaginasTotais() + ".\n");
                       break;
            }
        }while(menu5.getOpcao() != 0);
    }
    
    
}
