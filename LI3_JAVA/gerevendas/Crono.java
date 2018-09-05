import static java.lang.System.nanoTime;

/**
 * Classe necessária para medir os tempos de execução do programa.
 */
public class Crono{

  private static long inicio = 0L;
  private static long fim = 0L;
  
  /**
   * Método que inicia o cronómetro.
   */
  public static void start() { 
      fim = 0L; inicio = nanoTime();  
  }
  
  /**
   * Método que para o cronómetro.
   */
  public static double stop() { 
      fim = nanoTime();
      long elapsedTime = fim - inicio;
      // segundos
      return elapsedTime / 1.0E09;
  }
  
  /**
   * Método que devolve uma representação textual do intervalo
   * decorrido.
   */
  public static String print() {
      return "" + stop();
  }

}
