/**
 * Classe que representa a exceção enviada quando a página a que se 
 * quer aceder é inválida.
 */
public class PaginaInvalidaException extends Exception
{
    /**
     * Construtor por parâmetro
     */
    public PaginaInvalidaException(String msg)
    {
        super(msg);
    }
}
