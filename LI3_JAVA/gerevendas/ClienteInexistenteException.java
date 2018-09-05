/**
 * Classe que representa a exceção enviada quando o cliente não existe.
 */
public class ClienteInexistenteException extends Exception
{
    /**
     * Construtor por parâmetro.
     */
    public ClienteInexistenteException(String msg)
    {
        super(msg);
    }
}
