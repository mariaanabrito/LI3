import java.util.Scanner;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;

public class Leitura
{
   public static ArrayList<String> readLinesArrayWithScanner(String ficheiro) {
        ArrayList<String> linhas = new ArrayList<>();
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new FileReader(ficheiro));
            scanFile.useDelimiter("\n\r");
            while(scanFile.hasNext()) 
                linhas.add(scanFile.nextLine());
        }
        catch(IOException ioExc)
             { System.out.println(ioExc.getMessage()); return null; }
        finally { if(scanFile != null) scanFile.close(); }
        return linhas;
   }
     
   public static ArrayList<String> readLinesWithBuff(String fich) {
      ArrayList<String> linhas = new ArrayList<>();
      BufferedReader inStream = null; 
      String linha = null;
      try {
            inStream = new BufferedReader(new FileReader(fich));
            while( (linha = inStream.readLine()) != null )
                              linhas.add(linha);
      }
      catch(IOException e) 
          { System.out.println(e.getMessage()); return null; };
      return linhas;  
   }
   
   /** Falta adicionar as exceções */
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
   
   public static ArrayList<Venda> parseAllLinhas(ArrayList<String> linhas)
   {
       ArrayList<Venda> vendas = new ArrayList<Venda>();
       
       for(String s: linhas)
            vendas.add(parseLinhaVenda(s));
            
       return vendas;
   }
       
   
   public static void main(String [] args)
    {
        ArrayList<Venda> vendasList = new ArrayList<Venda>();
        ArrayList<String> linhas;
        
        System.out.println("Vendas_1M.txt lido sem parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_1M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_3M.txt lido sem parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_3M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_5M.txt lido sem parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_5M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        
        
        System.out.println("Vendas_1M.txt lido sem parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_1M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_3M.txt lido sem parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_3M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_5M.txt lido sem parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_5M.txt");
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        
        
        System.out.println("Vendas_1M.txt lido com parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_1M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_3M.txt lido com parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_3M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_5M.txt lido com parse de linhas usando Scanner()");
        Crono.start();
        linhas = readLinesArrayWithScanner("Vendas_5M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        
        
        System.out.println("Vendas_1M.txt lido com parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_1M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_3M.txt lido com parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_3M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());
        
        System.out.println("Vendas_5M.txt lido com parse de linhas usando BufferedReader()");
        Crono.start();
        linhas = readLinesWithBuff("Vendas_5M.txt");
        vendasList = parseAllLinhas(linhas);
        Crono.stop();
        System.out.println("Tempo:" + Crono.print());       
    }
}
