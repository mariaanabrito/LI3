/**
 * Classe que representa a exceção enviada quando o produto não existe.
 */
public class ProdutoInexistenteException extends Exception
{
    /**
     * Construtor por parâmetro
     */
   public ProdutoInexistenteException(String msg)
   {
       super(msg);
   }
}
